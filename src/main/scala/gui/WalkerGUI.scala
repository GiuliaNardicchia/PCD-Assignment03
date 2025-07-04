package gui

import actors.DirectoryScanner.*
import actors.{AggregateActor, DirectoryScanner}
import akka.actor.typed.scaladsl.AskPattern.*
import akka.actor.typed.{ActorRef, ActorSystem}

import java.awt.*
import java.nio.file.Paths
import javax.swing.*
import scala.swing.Swing
import scala.util.Try

class WalkerGUI(scannerRef: ActorRef[DirectoryScanner.Command], aggregateRef: ActorRef[AggregateActor.Command], system: ActorSystem[?]):

  private val directoryField = new JTextField()
  private val maxFilesField = new JTextField("10")
  private val numIntervalsField = new JTextField("10")
  private val maxLengthField = new JTextField("100")
  private val startButton = new JButton("Start")
  private val stopButton = new JButton("Stop")
  private val maxFilesArea = new JTextArea()
  private val distributionArea = new JTextArea()
  @volatile private var isStopped = false

  def initGUI(): Unit =
    val frame = new JFrame("Scala Walker")
    frame.setPreferredSize(new Dimension(800, 600))

    val inputPanel = new JPanel(new GridBagLayout())
    inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10))
    val gbc = new GridBagConstraints()
    gbc.insets = Insets(5, 5, 5, 5)
    gbc.anchor = GridBagConstraints.LINE_START

    def addRow(label: String, field: JComponent, y: Int, extraButton: Option[JButton] = None): Unit =
      gbc.gridx = 0
      gbc.gridy = y
      inputPanel.add(new JLabel(label), gbc)
      gbc.gridx = 1
      inputPanel.add(field, gbc)
      extraButton.foreach { btn =>
        gbc.gridx = 2
        inputPanel.add(btn, gbc)
      }

    val browseButton = new JButton("Browse")
    browseButton.addActionListener(_ => chooseDirectory())
    directoryField.setPreferredSize(Dimension(300, 25))

    addRow("Directory:", directoryField, 0, Some(browseButton))
    addRow("Max Files:", maxFilesField, 1)
    addRow("Num Intervals:", numIntervalsField, 2)
    addRow("Max Length:", maxLengthField, 3)

    startButton.addActionListener(_ => startWalker())
    stopButton.addActionListener(_ => stopWalker())
    stopButton.setEnabled(false)

    val buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT))
    buttonPanel.add(startButton)
    buttonPanel.add(stopButton)

    val centerPanel = new JPanel(new GridLayout(1, 2))
    centerPanel.add(createTextAreaPanel("Max Files", maxFilesArea))
    centerPanel.add(createTextAreaPanel("Distribution", distributionArea))

    frame.add(inputPanel, BorderLayout.NORTH)
    frame.add(centerPanel, BorderLayout.CENTER)
    frame.add(buttonPanel, BorderLayout.SOUTH)

    frame.pack()
    frame.setLocationRelativeTo(null)
    frame.setVisible(true)

  private def createTextAreaPanel(title: String, textArea: JTextArea): JPanel =
    val panel = new JPanel(new BorderLayout())
    panel.setBorder(BorderFactory.createTitledBorder(title))
    val scrollPane = new JScrollPane(textArea)
    panel.add(scrollPane, BorderLayout.CENTER)
    panel

  private def chooseDirectory(): Unit =
    val chooser = new JFileChooser()
    chooser.setDialogTitle("Select a directory")
    chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY)
    if chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION then
      directoryField.setText(chooser.getSelectedFile.getAbsolutePath)

  private def startWalker(): Unit =
    val dir = directoryField.getText.trim
    if dir.isEmpty then
      showErrorDialog("Please specify a directory.")
      return

    val validated = for
      maxLength <- parseField(maxLengthField, "max length")
      maxFiles  <- parseField(maxFilesField, "max files")
      numIntv   <- parseField(numIntervalsField, "num intervals")
      _         <- validateLimits(maxFiles, maxLength, "Max Files")
      _         <- validateLimits(numIntv, maxLength, "Num Intervals")
    yield (maxLength, maxFiles, numIntv)

    validated match
      case Some((maxLength, maxFiles, numIntv)) =>
        val dirPath = Paths.get(dir)
        aggregateRef ! AggregateActor.ResetStats
        aggregateRef ! AggregateActor.Start
        scannerRef ! DirectoryScanner.Start
        scannerRef ! DirectoryScanner.Scan(os.Path(dirPath))

        isStopped = false
        startButton.setEnabled(false)
        stopButton.setEnabled(true)

        val printerThread = Thread(() =>
          while !isStopped do
            printResults()
            Thread.sleep(75)
        )
        printerThread.start()

      case None => // Errors already shown

  private def stopWalker(): Unit =
    isStopped = true
    startButton.setEnabled(true)
    stopButton.setEnabled(false)
    scannerRef ! DirectoryScanner.Stop
    aggregateRef ! AggregateActor.Stop
//    maxFilesArea.setText("")
//    distributionArea.setText("")

  private def printResults(): Unit = {
    import akka.actor.typed.Scheduler
    import akka.util.Timeout
    import scala.concurrent.ExecutionContext
    import scala.concurrent.duration.*

    given Timeout = Timeout(3.seconds)
    given Scheduler = system.scheduler
    given ExecutionContext = ExecutionContext.global

    val futureStats = aggregateRef.ask(replyTo =>
      AggregateActor.GetStats(
        maxFilesField.getText.trim.toInt,
        numIntervalsField.getText.trim.toInt,
        maxLengthField.getText.trim.toInt,
        replyTo
      )
    )

    futureStats.foreach { anyResult =>
      Swing.onEDT {
        val stats = anyResult.asInstanceOf[AggregateActor.Stats]
        maxFilesArea.setText(stats.topFiles.map(_.toString).mkString("\n"))
        distributionArea.setText(stats.distribution)
      }
    }
  }

  private def showErrorDialog(message: String): Unit =
    Swing.onEDT {
      JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE)
    }

  private def parseField(field: JTextField, name: String): Option[Int] =
    Try(field.getText.trim.toInt).toOption.orElse {
      showErrorDialog(s"Invalid value for $name: '${field.getText.trim}'")
      None
    }

  private def validateLimits(value: Int, limit: Int, name: String): Option[Unit] =
    if value < limit then Some(())
    else
      showErrorDialog(s"$name value must be less than max length ($limit).")
      None
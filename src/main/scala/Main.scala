import java.nio.file.Paths

object Main:
  import akka.actor.typed.ActorSystem
  import DirectoryScanner.*

  @main def runMain(): Unit =
    val path = Paths.get(os.root.toString, "Users", "HP", "Desktop", "UNIBO", "stress_test")
    val maxFiles = 10
    val numIntervals = 10
    val maxLength = 100

    val system = ActorSystem[Command](
      guardianBehavior = DirectoryScanner(path.toString),
      name = "DirectoryScanner"
    )
    system ! Scan(os.Path(path.toString))
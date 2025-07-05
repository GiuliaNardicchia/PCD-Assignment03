import akka.actor.typed.scaladsl.Behaviors

import java.nio.file.Paths

object Main:
  import akka.actor.typed.ActorSystem
  import DirectoryScanner.*

  @main def runMain(): Unit =
    val path = Paths.get(os.pwd.toString, "benchmarks", "stress_test")
    val maxFiles = 10
    val numIntervals = 10
    val maxLength = 100

    val system = ActorSystem(Behaviors.setup[DirectoryScanner.Command] { context =>
      val aggregateActor = context.spawn(AggregateActor(), "AggregateActor")
      val fileReader = context.spawn(FileReader(aggregateActor), "FileReader")
      val directoryScanner = context.spawn(DirectoryScanner(fileReader), "DirectoryScanner")
      WalkerGUI(directoryScanner, aggregateActor, context.system).initGUI()
      Behaviors.empty
    }, "ActorSystem")

//    system ! Scan(os.Path(path))

//    C:\Users\HP\Desktop\UNIBO\LaureaMagistrale\1 Anno\Programmazione Concorrente e Distribuita (PCD)\Assignment\PCD-Assignment03\benchmarks

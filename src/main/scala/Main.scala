import akka.actor.typed.ActorRef
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
      DirectoryScanner(fileReader)
    }, "ActorSystem")

    system ! Scan(os.Path(path))

//  @main def checkLOC(): Unit =
//    val path = Paths.get(os.root.toString, "Users", "HP", "Desktop", "UNIBO", "stress_test", "AccountActivity.java")
//    val loc = os.read.lines(os.Path(path), charSet = java.nio.charset.StandardCharsets.ISO_8859_1).size
//    println(loc)
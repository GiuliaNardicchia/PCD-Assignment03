import akka.actor.typed.ActorRef
import akka.actor.typed.scaladsl.Behaviors

import java.nio.file.Paths

object Main:
  import akka.actor.typed.ActorSystem
  import DirectoryScanner.*

  @main def main(): Unit =
    val path = Paths.get(os.root.toString, "Users", "HP", "Desktop", "UNIBO", "stress_test")
    val maxFiles = 10
    val numIntervals = 10
    val maxLength = 100

    val system = ActorSystem(Behaviors.setup[DirectoryScanner.Command] { context =>
      val fileReader = context.spawn(FileReader(path.toString), "FileReader")
      DirectoryScanner(path.toString, fileReader)
    }, "ActorSystem")

    system ! Scan(os.Path(path.toString))

  @main def checkLOC(): Unit =
    val path = Paths.get(os.root.toString, "Users", "HP", "Desktop", "UNIBO", "stress_test", "AccountActivity.java")
    val loc = os.read.lines(os.Path(path), charSet = java.nio.charset.StandardCharsets.ISO_8859_1).size
    println(loc)
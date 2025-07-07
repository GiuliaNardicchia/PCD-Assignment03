import actors.{AggregateActor, DirectoryScanner, FileReader}
import akka.actor.typed.scaladsl.Behaviors
import gui.WalkerGUI

import java.nio.file.Paths

object Main:
  import akka.actor.typed.ActorSystem
  import actors.DirectoryScanner.*

  @main def runMain(): Unit =
    val path = Paths.get(os.pwd.toString, "benchmarks", "stress_test")
    val maxFiles = 10
    val numIntervals = 10
    val maxLength = 100

    val system = ActorSystem(Behaviors.setup[DirectoryScanner.Command] { context =>
      val aggregateActor = context.spawn(AggregateActor(), "AggregateActor")
      val directoryScanner = context.spawn(DirectoryScanner(aggregateActor), "DirectoryScanner")
      WalkerGUI(directoryScanner, aggregateActor, context.system).initGUI()
      Behaviors.empty
    }, "ActorSystem")
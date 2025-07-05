package actors

import actors.FileReader.*
import akka.actor.typed.{ActorRef, Behavior}
import akka.actor.typed.scaladsl.Behaviors

/**
 * Actor DirectoryScanner that recursively scans directories and files that sends as content of messages to FileReader
 * if it finds a file with .java extension.
 */
object DirectoryScanner:
  enum Command:
    case Start
    case Scan(path: os.Path)
    case Stop
  export Command.*

  private var i = 0

  def apply(aggregateActor: ActorRef[AggregateActor.Command]): Behavior[Command] = idle(aggregateActor)

  private def idle(aggregateActor: ActorRef[AggregateActor.Command]): Behavior[Command] =
    Behaviors.receive { (context, msg) =>
      msg match
        case Start =>
          val fileReader = context.spawn(FileReader(aggregateActor), s"FileReader$i")
          i+=1
          active(aggregateActor, fileReader)
        case _ => Behaviors.same
    }

  private def active(aggregateActor: ActorRef[AggregateActor.Command], fileReader: ActorRef[FileReader.Command]): Behavior[Command] =
    Behaviors.receive { (context, msg) =>
      msg match
        case Scan(path) =>
          context.log.info(s"Scanning directory: $path")
          for (p <- os.list(path)) {
            if os.isDir(p) then
              context.log.info(s"Found directory: $p")
              context.self ! Scan(p)
            else if os.isFile(p) && p.toString.endsWith(".java") then {
              context.log.info(s"Found file java: $p")
              fileReader ! FileReader.ReadFile(p)
            } else
              context.log.info(s"Unknown type: $p")
          }
          Behaviors.same
        case Stop =>
          context.stop(fileReader)
          idle(aggregateActor)

        case _ => Behaviors.same
    }
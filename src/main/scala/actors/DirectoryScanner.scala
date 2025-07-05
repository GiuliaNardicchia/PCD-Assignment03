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

  def apply(fileReader: ActorRef[FileReader.Command]): Behavior[Command] = idle(fileReader)

  private def idle(fileReader: ActorRef[FileReader.Command]): Behavior[Command] =
    Behaviors.receive { (context, msg) =>
      msg match
        case Start => active(fileReader)
        case _ => Behaviors.same
    }

  private def active(fileReader: ActorRef[FileReader.Command]): Behavior[Command] =
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
          idle(fileReader)
        case _ => Behaviors.same
    }
import akka.actor.typed.ActorRef
import akka.actor.typed.Behavior
import akka.actor.typed.scaladsl.Behaviors

import FileReader.*

/**
 * Actor directory scanner that recursively scans directories and logs the files found.
 */
object DirectoryScanner:
  enum Command:
    case Scan(path: os.Path)
  export Command.*

  def apply(fileReader: ActorRef[FileReader.Command]): Behavior[Command] =
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
    }
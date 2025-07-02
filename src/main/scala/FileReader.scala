import akka.actor.typed.{Behavior, DispatcherSelector}
import akka.actor.typed.scaladsl.Behaviors

import scala.concurrent.{ExecutionContext, Future}

/**
 * Actor FileReader that reads a file.
 */
object FileReader:
  enum Command:
    case ReadFile(path: os.Path)
  export Command.*

  def apply(path: String): Behavior[Command] =
    Behaviors.receive { (context, msg) =>
      msg match
        case ReadFile(filePath) =>
          given ExecutionContext =
            context.system.dispatchers.lookup(DispatcherSelector.fromConfig("my-blocking-dispatcher"))
          context.log.info(s"Reading file: ${filePath.toString}")
          val result = Future:
            // ctx.log.info(s"Truly starting ${i}") // NB: THIS WOULD BLOCK (ctx.log is not thread-safe: see docs)
            print(s"Starting op: ${filePath.toString}.\n")
            val loc = os.read.lines(os.Path(path), charSet = java.nio.charset.StandardCharsets.ISO_8859_1).size
            print(s"Blocking op finished: ${filePath.toString}, line of code: $loc.\n")
            Seq(filePath, loc)
          context.log.info(s"Done handling ${filePath.toString}")
          Behaviors.same
    }
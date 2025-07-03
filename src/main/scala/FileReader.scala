import akka.actor.typed.{ActorRef, Behavior, DispatcherSelector}
import akka.actor.typed.scaladsl.Behaviors

import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Failure, Success, Try}

/**
 * Actor FileReader that reads a file.
 */
object FileReader:
  enum Command:
    case ReadFile(path: os.Path)
  export Command.*

  def apply(aggregateActor: ActorRef[AggregateActor.Command]): Behavior[Command] =
    Behaviors.receive { (context, msg) =>
      msg match
        case ReadFile(path) =>
          given ExecutionContext =
            context.system.dispatchers.lookup(DispatcherSelector.fromConfig("my-blocking-dispatcher"))
//          context.log.info(s"Reading file: ${path.toString}")

          Future:
            //ctx.log.info(s"Truly starting ${i}") // NB: THIS WOULD BLOCK (ctx.log is not thread-safe: see docs)
//            print(s"Starting op: ${path.toString}.\n")
            val loc = countLOC(path)
            aggregateActor ! AggregateActor.AggregateResult(path, loc)
//            print(s"Blocking op finished: ${path.toString}, line of code: $loc.\n")
//          context.log.info(s"Done handling ${path.toString}")
          Behaviors.same
    }

  private def countLOC(filePath: os.Path): Int =
    val tryLoc = Try { os.read.lines(filePath).size }
    //val tryLoc = Try { os.read.lines(filePath, charSet = java.nio.charset.StandardCharsets.ISO_8859_1).size }
    tryLoc match
      case Success(v) => println(s"Numero di righe: $v"); v
      case Failure(e) => println(s"Errore nel leggere il file: ${e.getMessage}"); 0


//          val result = Future:
//            // ctx.log.info(s"Truly starting ${i}") // NB: THIS WOULD BLOCK (ctx.log is not thread-safe: see docs)
//            print(s"Starting op: ${filePath.toString}.\n")
//            val loc = countLOC(filePath)
//            print(s"Blocking op finished: ${filePath.toString}, line of code: $loc.\n")
//            (filePath, loc)
//          aggregateActor ! AggregateActor.AggregateResult(result)
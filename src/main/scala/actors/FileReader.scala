package actors

import akka.actor.typed.scaladsl.Behaviors
import akka.actor.typed.{ActorRef, Behavior, DispatcherSelector}

import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Failure, Success, Try}

/**
 * Actor FileReader that read a .java file and count the lines of code.
 * It sends the result to AggregateActor.
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
    tryLoc match
      case Success(v) => println(s"Numero di righe: $v"); v
      case Failure(e) => println(s"Errore nel leggere il file: ${e.getMessage}"); 0
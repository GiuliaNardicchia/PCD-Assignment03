import akka.actor.typed.Behavior
import akka.actor.typed.scaladsl.Behaviors

object AggregateActor:
  enum Command:
    case AggregateResult(path: os.Path, loc: Int)
    case GetStats(maxFiles: Int, numIntervals: Int, maxLength: Int)
  export Command.*

  private var results: Seq[(os.Path, Int)] = Seq.empty

  def apply(): Behavior[Command] =
    Behaviors.receive { (context, msg) =>
      msg match
        case AggregateResult(path, loc) =>
          results = results :+ (path, loc)
          context.log.info(results.size.toString)
          Behaviors.same
        case GetStats(_, _, _) => ???
    }
import akka.actor.typed.Behavior
import akka.actor.typed.scaladsl.Behaviors

object AggregateActor:
  enum Command:
    case AggregateResult(path: os.Path, loc: Int)
  export Command.*

  def apply(): Behavior[Command] =
    Behaviors.receive { (context, msg) =>
      msg match
        case AggregateResult(path, loc) =>
          context.log.info(s"Aggregated result for file: ${path.toString}, lines of code: $loc")
          Behaviors.same
    }
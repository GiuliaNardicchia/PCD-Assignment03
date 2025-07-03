import akka.actor.typed.Behavior

import scala.concurrent.Future

object AggregateActor:
  enum Command:
    case AggregateResult(result: (os.Path, Int))
  export Command.*

  def apply(): Behavior[Command] = ???
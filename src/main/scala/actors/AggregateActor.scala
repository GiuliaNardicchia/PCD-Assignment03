package actors

import akka.actor.typed.scaladsl.Behaviors
import akka.actor.typed.{ActorRef, Behavior}

/**
 * Actor AggregateActor that aggregates results from FileReader actors and provides statistics about the files processed.
 * It can return the top files and a distribution of file lengths.
 */
object AggregateActor:
  enum Command:
    case Start
    case AggregateResult(path: os.Path, loc: Int)
    case GetStats(maxFiles: Int, numIntervals: Int, maxLength: Int, replyTo: ActorRef[Stats])
    case ResetStats
    case Stop
  export Command.*
  case class Stats(topFiles: Seq[os.Path], distribution: String)

  private var results: Seq[(os.Path, Int)] = Seq.empty

  def apply(): Behavior[Command] = idle
  
  private def idle: Behavior[Command] =
    Behaviors.receive { (context, msg) =>
      msg match
        case Start => active
        case ResetStats =>
          results = Seq.empty
          context.log.info("Statistics reset.")
          Behaviors.same
        case _ => Behaviors.same
    }
  
  private def active: Behavior[Command] =
    Behaviors.receive { (context, msg) =>
      msg match
        case AggregateResult(path, loc) =>
          results = results :+ (path, loc)
          Behaviors.same

        case GetStats(maxFiles, numIntervals, maxLength, replyTo) =>
          val top = results.sortBy(-_._2).take(maxFiles).map((path, _) => path)
          val ranges = calculateIntervalRanges(numIntervals, maxLength / numIntervals, maxLength)
          val dist = Array.fill(ranges.size)(0)
          for ((_, loc) <- results) {
            val idx = ranges.indexWhere { case (start, end) => loc >= start && (if (end == maxLength) true else loc <= end) }
            if (idx >= 0) dist(idx) += 1
          }
          replyTo ! Stats(top, getDistributionString(ranges, dist, maxLength))
          Behaviors.same

        case ResetStats =>
          results = Seq.empty
          context.log.info("Statistics reset.")
          Behaviors.same

        case Stop => idle
        
        case _ =>
          Behaviors.same
    }

  private def calculateIntervalRanges(numIntervals: Int, intervalSize: Int, maxLength: Int): Seq[(Int, Int)] = {
    (0 until numIntervals + 1).map { i =>
      val start = i * intervalSize
      val end = if (i == numIntervals) maxLength else start + intervalSize - 1
      (start, end)
    }
  }

  private def getDistributionString(
                             intervalRanges: Seq[(Int, Int)],
                             distribution: Seq[Int],
                             maxLines: Int
                           ): String = {
    val sb = new StringBuilder
    for (i <- intervalRanges.indices) {
      val (start, end) = intervalRanges(i)
      if (end == maxLines) {
        sb.append(s"[$start,+inf]: ${distribution(i)}\n")
      } else {
        sb.append(s"[$start,$end]: ${distribution(i)}\n")
      }
    }
    sb.toString()
  }
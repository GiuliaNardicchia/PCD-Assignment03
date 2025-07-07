import actors.{AggregateActor, DirectoryScanner, FileReader}
import akka.actor.testkit.typed.scaladsl.BehaviorTestKit
import akka.actor.testkit.typed.Effect
import akka.actor.testkit.typed.scaladsl.TestInbox
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import actors.DirectoryScanner.*

import java.nio.file.Paths

class DirectoryScannerTest extends AnyFlatSpec with Matchers:

  "DirectoryScanner in idle" should "spawn FileReader when Start is received" in:
    val inboxAgg = TestInbox[AggregateActor.Command]()
    val kit = BehaviorTestKit(DirectoryScanner(inboxAgg.ref))

    kit.run(Start)

    val effect = kit.expectEffectPF:
      case Effect.Spawned(_, name, _) =>
        name should startWith("FileReader")
        true
    effect shouldBe true

  "DirectoryScanner in active" should "send ReadFile for each .java file and Scan for directories" in:
    val inboxAgg = TestInbox[AggregateActor.Command]()
    val kit = BehaviorTestKit(DirectoryScanner(inboxAgg.ref))
    val fileReaderInbox = TestInbox[FileReader.Command]()
    val activeBehavior: Unit = kit.run(Start)

    val activeKit = BehaviorTestKit(DirectoryScanner.active(inboxAgg.ref, fileReaderInbox.ref))
    val path = Paths.get(os.pwd.toString, "benchmarks", "stress_test")
    activeKit.run(Scan(os.Path(path)))
    fileReaderInbox.hasMessages shouldBe true
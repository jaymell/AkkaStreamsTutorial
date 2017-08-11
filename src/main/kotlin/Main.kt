import akka.stream.*;
import akka.stream.javadsl.*;
import akka.NotUsed;
import akka.actor.ActorSystem;
import java.util.concurrent.CompletionStage;
import akka.stream.Supervision


fun decider(exc: Throwable): Supervision.Directive {
  if (exc is ArithmeticException) {
      println("Exception encountered")
      return Supervision.resume()
  }
  else {
      return Supervision.stop()
  }
}


fun main(args: Array<String>) {

    val system: ActorSystem = ActorSystem.create("QuickStart")

    val mat: Materializer = ActorMaterializer.create(ActorMaterializerSettings.create(system)
            .withSupervisionStrategy(::decider), system)

    val flow: Flow<Int, Int, NotUsed> =
        Flow.of(Int::class.java)
            .filter{ 100 / it < 50 }
            .map{ 100 / (5-it) }

    val source: Source<Int, NotUsed> = Source.from(listOf(0, 1, 2, 3, 4, 5))
            .via(flow)

    val sink = Sink.fold<Int, Int>(0) { acc, elem ->
        val ret = acc + elem
        println("acc: ${ret}")
        ret
    }

    val result: CompletionStage<Int> = source.runWith(sink, mat)
}


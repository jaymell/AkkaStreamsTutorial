import akka.stream.*;
import akka.stream.javadsl.*;
import akka.Done;
import akka.NotUsed;
import akka.actor.ActorSystem;
import akka.util.ByteString;

import java.nio.file.Paths;
import java.math.BigInteger;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import scala.concurrent.duration.Duration;

fun main(args: Array<String>) {
    val system: ActorSystem = ActorSystem.create("QuickStart")
    val materializer: Materializer = ActorMaterializer.create(system)
    val source: Source<Int, NotUsed> = Source.range(1, 100)
    val factorials = source
        .scan(BigInteger.ONE) { acc, next ->
            acc.multiply(BigInteger.valueOf(next.toLong()))
        }
    val result: CompletionStage<IOResult> = factorials
        .map{ ByteString.fromString(it.toString() + "\n") }
        .runWith(FileIO.toPath(Paths.get("factorials.txt")), materializer)
}
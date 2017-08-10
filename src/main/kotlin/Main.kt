import akka.stream.*;
import akka.stream.javadsl.*;
import akka.Done;
import akka.NotUsed;
import akka.actor.ActorSystem;
import akka.stream.impl.PublisherSink
import akka.util.ByteString;

import java.nio.file.Paths;
import java.math.BigInteger;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import scala.concurrent.duration.Duration;

import twitter4j.*
import twitter4j.conf.ConfigurationBuilder
import java.lang.Exception

class MyStatusListener: StatusListener {
   override fun onStatus(status: Status) {
       println(status.getText())
   }

    override fun onTrackLimitationNotice(numberOfLimitedStatuses: Int) {
        println(numberOfLimitedStatuses)
    }

    override fun onDeletionNotice(statusDeletionNotice: StatusDeletionNotice?) {
        println(statusDeletionNotice)
    }

    override fun onStallWarning(warning: StallWarning?) {
        println(warning)
    }

    override fun onException(ex: Exception?) {
        println(ex)
    }

    override fun onScrubGeo(userId: Long, upToStatusId: Long) {
        println(userId)
    }
}

class MyRawListener: RawStreamListener {
	override fun onMessage(msg: String) {
		return msg
	}
    override fun onException(ex: Exception?) {
        return ex
    }
}

fun main(args: Array<String>) {
    val system: ActorSystem = ActorSystem.create("QuickStart")
    val materializer: Materializer = ActorMaterializer.create(system)

    val configBuilder: ConfigurationBuilder = ConfigurationBuilder()
            .setOAuthConsumerKey(appKey)
            .setOAuthConsumerSecret(appSecret)
            .setOAuthAccessToken(accessToken)
            .setOAuthAccessTokenSecret(accessTokenSecret)

    fun source(): Source<String, NotUsed> {
        val twitterStream: TwitterStream = TwitterStreamFactory(configBuilder.build()).getInstance()
        val sink: Sink = Sink<String, NotUsed>()
        Twitter4JHelper.addStatusListner(twitterStream, MyStatusListener())
        twitterStream.sample()
        val src = Source.actorRef<String>(1000, OverflowStrategy.fail())
    }

	// raw stream
    //Twitter4JHelper.addRawListner(twitterStream, MyRawListener())
	
    // apply arbitrary filter
    //Twitter4JHelper.addStatusListner(twitterStream, MyStatusListener())
    // val tweetFilterQuery: FilterQuery = FilterQuery()
    // tweetFilterQuery.track(arrayOf("trump"))
    // twitterStream.filter(tweetFilterQuery)

//    val source: Source<Int, NotUsed> = Source.range(1, 100)
//    val factorials = source
//        .scan(BigInteger.ONE) { acc, next ->
//            acc.multiply(BigInteger.valueOf(next.toLong()))
//        }
    // instead of this:
//    val result: CompletionStage<IOResult> = factorials
//        .map{ ByteString.fromString(it.toString() + "\n") }
//        .runWith(FileIO.toPath(Paths.get("factorials.txt")), materializer)

    // make a reusable sink:
//    fun lineSink(filename: String): Sink<String, CompletionStage<IOResult>> {
//        return Flow.of(String::class.java)
//            .map{ ByteString.fromString(it.toString() + "\n") }
//            .toMat(FileIO.toPath(Paths.get(filename)), Keep.right())
//    }
//
//    factorials.map(BigInteger::toString).runWith(lineSink("factorial2.txt"), materializer)


}

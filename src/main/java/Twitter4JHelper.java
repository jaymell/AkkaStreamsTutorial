import twitter4j.StatusListener;
import twitter4j.RawStreamListener;
import twitter4j.TwitterStream;

public class Twitter4JHelper {
    public static void addStatusListner(TwitterStream stream, StatusListener listner) {
        stream.addListener(listner);
    }
	public static void addRawListner(TwitterStream stream, RawStreamListener listner) {
		stream.addListener(listner);
	}
}


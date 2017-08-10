package TwitterStuff

import twitter4j.*
import twitter4j.conf.ConfigurationBuilder
import twitter4j.auth.AccessToken

object TwitterStreamClient {
    val appKey = ""
    val appSecret = ""
    val accessToken = ""
    val accessTokenSecret = ""

    val configBuilder: ConfigurationBuilder = ConfigurationBuilder()
            .setOAuthConsumerKey(appKey)
            .setOAuthConsumerSecret(appSecret)
            .setOAuthAccessToken(accessToken)
            .setOAuthAccessTokenSecret(accessTokenSecret)

    val twitterStream: TwitterStream = TwitterStreamFactory(configBuilder.build()).getInstance()
}

object TwitterClient {
    val appKey = ""
    val appSecret = ""
    val accessToken = ""
    val accessTokenSecret = ""

    fun apply(): Twitter {
        val factory = TwitterFactory(ConfigurationBuilder().build())
        val t = factory.getInstance()
        t.setOAuthConsumer(appKey, appSecret)
        t.setOAuthAccessToken(AccessToken(accessToken, accessTokenSecret))
        return t
    }
}

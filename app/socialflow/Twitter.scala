package socialflow

import play.api.Play
import twitter4j._
import twitter4j.auth._

class TwitterConnector() {
    val access_token = Play.current.configuration.getString("twitter.access_token").getOrElse("")
    val access_token_secret = Play.current.configuration.getString("twitter.access_token_secret").getOrElse("")

    val consumer_key = Play.current.configuration.getString("twitter.consumer_key").getOrElse("")
    val consumer_secret = Play.current.configuration.getString("twitter.consumer_secret").getOrElse("")

    val client = new TwitterFactory().getInstance()

    // Authorising with your Twitter Application credentials
    client.setOAuthConsumer(
        consumer_key,
        consumer_secret
    )

    client.setOAuthAccessToken(
        new AccessToken(
            access_token,
            access_token_secret
        )
    )


}

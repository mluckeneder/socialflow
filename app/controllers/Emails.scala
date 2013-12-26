package controllers

import play.api._
import play.api.mvc._
import play.api.mvc.BodyParsers.parse.json
import play.api.libs.json._
import play.api.libs.functional.syntax._
import twitter4j._
import twitter4j.auth._
import java.io.ByteArrayInputStream

import socialflow._

trait SocialRequestHandler {
    def apply(request: SocialRequest)
}

object PhotoPostHandler extends SocialRequestHandler {
    def apply(request: SocialRequest) = {
        val twitter = new TwitterConnector
        println(request.subject)
        request.attachment.map { attachment =>
            val fileStream = new ByteArrayInputStream(attachment.ByteContent)

            val statusUpdate = new StatusUpdate(request.subject)
            statusUpdate.setMedia(attachment.Name, fileStream)
            println("Post Photo!")
            twitter.client.updateStatus(statusUpdate)
        }

    }
}

object TextPostHandler extends SocialRequestHandler {
    def apply(request: SocialRequest) = {
        val twitter = new TwitterConnector
        println("Post Status!")
        twitter.client.updateStatus(request.subject)
    }
}

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


object Emails extends Controller {

    def insert = Action(json) { request =>
        val social_request = SocialRequestParser.parse(request.body)

        social_request match {
            case SocialRequest(subject: String, atts: Some[AttachedPhoto]) => PhotoPostHandler(social_request)
            case SocialRequest(subject: String, None) => TextPostHandler(social_request)
            case _ => None
        }

        Ok("Handled")

    }

}

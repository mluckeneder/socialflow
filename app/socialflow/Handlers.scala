package socialflow

import java.io.ByteArrayInputStream
import twitter4j._

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
            // twitter.client.updateStatus(statusUpdate)
        }

    }
}

object TextPostHandler extends SocialRequestHandler {
    def apply(request: SocialRequest) = {
        val twitter = new TwitterConnector
        println("Post Status!")
        // twitter.client.updateStatus(request.subject)
    }
}

// object Handlers {
//     val handlerMap = Map(
//         "test" -> PhotoPostHandler
//     )
// }

package controllers

import play.api._
import play.api.mvc._
import play.api.mvc.BodyParsers.parse.json
import play.api.libs.json._
import play.api.libs.functional.syntax._
import org.apache.commons.codec.binary.Base64


case class SocialRequest(subject: String, attachment: Seq[AttachedPhoto]){}
case class AttachedPhoto(Name: String, Content: String, ContentType: String, ContentID: String, ContentLength: Int){

    val ByteContent = Base64.decodeBase64(Content.getBytes("utf-8"))
}

trait SocialRequestHandler {
    def apply(request: SocialRequest)
}

object PhotoPostHandler extends SocialRequestHandler {
    def apply(request: SocialRequest) = {
        println("Photo Post")
    }
}

object TextPostHandler extends SocialRequestHandler {
    def apply(request: SocialRequest) = {
        println("Text Post")
    }
}

object SocialRequestParser {
    def parse(json_body: JsValue): SocialRequest = {

       implicit val attachmentReads: Reads[AttachedPhoto] = (
          (__ \\ "Name").read[String] ~
          (__ \\ "Content").read[String] ~
          (__ \\ "ContentType").read[String] ~
          (__ \\ "ContentID").read[String] ~
          (__ \\ "ContentLength").read[Int]
        )(AttachedPhoto)

        // Extracting fields
        val maybeSubject = (json_body \ "Subject").validate[String]
        val maybeAttachments = (json_body \ "Attachments").validate[List[AttachedPhoto]]

        val subject = maybeSubject match {
            case JsSuccess(subject: String, p: JsPath) => subject
            case _ => throw new Exception
        }

        val attachments = maybeAttachments match {
            case JsSuccess(atts: List[AttachedPhoto], p: JsPath) =>
                atts
            case _ => throw new Exception
        }

        val social_request = new SocialRequest(
            subject,
            attachments
        )

        return social_request
    }

}

object Emails extends Controller {

    def insert = Action(json) { request =>
        val social_request = SocialRequestParser.parse(request.body)

        social_request match {
            case SocialRequest(subject: String, atts: Seq[AttachedPhoto]) if atts.length > 0 => PhotoPostHandler(social_request)
            case SocialRequest(subject: String, Nil) => TextPostHandler(social_request)
            case _ => None
        }

        Ok("Handled")

    }

}

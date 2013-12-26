package controllers

import play.api._
import play.api.mvc._
import play.api.mvc.BodyParsers.parse.json
import play.api.libs.json._
import play.api.libs.functional.syntax._


case class SocialRequest(subject: String, attachment: Option[Seq[AttachedPhoto]]){}
case class AttachedPhoto(Name: String, Content: String, ContentType: String, ContentID: String, ContentLength: Int)

object SocialRequestParser {
    def parse(json_body: JsValue): SocialRequest = {
       implicit val attachmentReads: Reads[AttachedPhoto] = (
          (__ \\ "Name").read[String] and
          (__ \\ "Content").read[String] and
          (__ \\ "ContentType").read[String] and
          (__ \\ "ContentID").read[String] and
          (__ \\ "ContentLength").read[Int]
        )(AttachedPhoto)

        // Extracting fields
        // TODO remove as[T]
        val maybeSubject = (json_body \ "Subject").validate[String]
        val attachments = (json_body \ "Attachments").asOpt[List[AttachedPhoto]]
        println(attachments)
        println(maybeSubject)
        maybeSubject match {
            case JsSuccess(subject: String, p: JsPath) => println(subject)
            case _ => throw new Exception
        }

        val social_request = new SocialRequest(
            "Hello",
            attachments
        )

        return social_request
    }

}

object Emails extends Controller {

    def insert = Action(json) { request =>
        val social_request = SocialRequestParser.parse(request.body)

        social_request match {
            case SocialRequest(subject: String, Some(atts: Seq[AttachedPhoto])) if atts.length > 0 => Ok("upload attachment")
            case SocialRequest(subject: String, Some(Nil)) => Ok("post only")
            case _ => Ok("Damn")
        }
        // Ok("done")
    }

}

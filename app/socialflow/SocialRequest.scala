package socialflow

import org.apache.commons.codec.binary.Base64
import play.api.libs.json._
import play.api.libs.functional.syntax._
import play.api.mvc.BodyParsers.parse.json

case class SocialRequest(subject: String, attachment: Option[AttachedPhoto]){}
case class AttachedPhoto(Name: String, Content: String, ContentType: String, ContentID: String, ContentLength: Int){

    val ByteContent = Base64.decodeBase64(Content.getBytes("utf-8"))
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
            case JsSuccess(atts: List[AttachedPhoto], p: JsPath) if atts.length == 1 =>
                Some(atts(0))
            case JsSuccess(atts: List[AttachedPhoto], p: JsPath) if atts.length == 0 =>
                None
            case _ => throw new Exception
        }

        val social_request = new SocialRequest(
            subject,
            attachments
        )
        return social_request
    }

}

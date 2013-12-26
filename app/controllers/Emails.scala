package controllers

import play.api._
import play.api.mvc._
import play.api.mvc.BodyParsers.parse.json
import play.api.libs.json._
import play.api.libs.functional.syntax._

import socialflow._

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

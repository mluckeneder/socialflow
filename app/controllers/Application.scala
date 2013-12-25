package controllers

import play.api._
import play.api.mvc._

import com.restfb._
import com.restfb.types._

object Application extends Controller {

  def index = Action {
    Ok(views.html.index("Your new application is ready."))
  }

  def testFacebook = Action {
    val publicClient = new DefaultFacebookClient
    val returnit = publicClient.fetchObject("cocacola", classOf[Page])
    Ok(returnit.getAbout)
  }

}

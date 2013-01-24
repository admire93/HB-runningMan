package controllers

import play.api._
import play.api.mvc._
import models._

object Application extends Controller {
  
  def index = Action { implicit request =>
    Ok(views.html.index())
  }

  def logout = Action { implicit request =>
    Redirect(routes.Application.index).withNewSession
  }
}
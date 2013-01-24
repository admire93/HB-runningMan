package controllers

import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._

case class AdminLogin(id: String, pw: String)

object Admins extends Controller with Secured {

  val loginForm = Form(
    mapping(
      "id" -> nonEmptyText,
      "pw" -> nonEmptyText
    )(AdminLogin.apply)(AdminLogin.unapply)
  )
  def index = WithAdmin { implicit request =>
    Ok(views.html.admin.index())
  }

  def logout = Action { implicit request =>
    Redirect(routes.Admins.login).withNewSession
  }

  def addTeam = TODO

  def login = Action { implicit request =>
    Ok(views.html.admin.login(loginForm))
  }   

  def auth = Action { implicit request =>
    loginForm.bindFromRequest.fold(
      error => Ok(views.html.admin.login(error)),
      d => {
        if(d.id == "hb.admin" && d.pw == "victorySpirit") {
          Redirect(routes.Admins.index).withSession(
            "admin" -> "ok"
          )
        } else {
          Redirect(routes.Admins.login).flashing(
            "fail" -> "아이디나 비밀번호가 올바르지 못합니다."
          )
        }
      }
    )
  }

}



trait Secured {
  def WithAdmin(f: Request[AnyContent] => Result) = Action { implicit request =>
    request.session.get("admin").map { _ =>
      f(request)
    }.getOrElse {
      Results.Ok(views.html.notfound())
    }
  }
}
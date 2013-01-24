package controllers

import java.util.Date

import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._

import models._

case class AdminLogin(id: String, pw: String)
case class TeamLogin(id: String, pw: String, name: String)

object Admins extends Controller with Secured {

  val loginForm = Form(
    mapping(
      "id" -> nonEmptyText,
      "pw" -> nonEmptyText
    )(AdminLogin.apply)(AdminLogin.unapply)
  )
  val addTeamForm = Form(
    mapping(
      "id" -> nonEmptyText,
      "pw" -> nonEmptyText,
      "name" -> nonEmptyText
    )(TeamLogin.apply)(TeamLogin.unapply)
  )
  def index = WithAdmin { implicit request =>
    Ok(views.html.admin.index())
  }

  def logout = Action { implicit request =>
    Redirect(routes.Admins.login).withNewSession
  }

  def addTeam = WithAdmin { implicit request =>
    Ok(views.html.admin.addTeam())
  }

  def saveTeam = WithAdmin { implicit request =>
    addTeamForm.bindFromRequest.fold(
      error => Ok(views.html.admin.addTeam()),
      d => {
        Team.add(Team(anorm.NotAssigned, d.id, d.pw, d.name, new Date)).map { _ =>
          Redirect(routes.Admins.addTeam).flashing(
            "success" -> "%s 팀 등록에 성공했습니다. 학생들은 로그인하고 게임을 시작해주세요!".format(d.name)
          ) 
        }.getOrElse {
          Redirect(routes.Admins.addTeam).flashing(
            "fail" -> "%s 팀 등록에 실패했습니다. 다시 한번 가입해주세요.".format(d.name)
          ) 
        }
      }
    )
  }

  def viewMissions = WithAdmin { implicit request =>
    Ok(views.html.admin.viewMissions())
  }

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
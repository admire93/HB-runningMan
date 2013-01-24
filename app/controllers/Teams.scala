package controllers

import java.util.Date

import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._

import models._

case class TeamLogin(id: String, pw: String)

object Teams extends Controller with TeamsSecured {
  val loginForm = Form(
    mapping(
      "id" -> nonEmptyText,
      "pw" -> nonEmptyText
    )(TeamLogin.apply)(TeamLogin.unapply)
  )

  def index(id: Long) = WithTeam(id) { team => implicit request => 
    Ok(views.html.intro(team))
  }

  def login = Action { implicit request =>
    request.session.get("teamId").map { id =>
      Redirect(routes.Teams.index(id.toLong)).flashing(
        "fail" -> "이미 로그인 되어있는걸.. :("
      )
    }.getOrElse {
      Ok(views.html.login(loginForm))
    }
  }

  def auth = Action { implicit request =>
    loginForm.bindFromRequest.fold(
      error => {
        Ok(views.html.login(loginForm))
      },
      d => {
        Team.auth(d.id, d.pw).map { team =>
          Redirect(routes.Teams.index(team.index.toString.toLong)).withSession(
            "teamId" -> team.index.toString
          ).flashing(
            "success" -> "로그인 성공! %s 팀 런닝맨에 온걸 환영합니다. ".format(team.name)
          )
        }.getOrElse {
          Redirect(routes.Teams.login).flashing(
            "fail" -> "로그인 아이디나 비밀번호가 잘못되었습니다."
          )
        }
      }
    )
  }

  def startMission(teamId: Long, missionId: Long) = WithTeam(teamId) { team => implicit request =>
    Mission.findCurrentByTeamIdAndMissionId(teamId, missionId).map { mission =>
      Mission.start(missionId, teamId)
      Redirect(routes.Teams.currentMission(teamId)).flashing(
        "success" -> "미션 시작!!!"
      )
    }.getOrElse {
      Ok(views.html.error()) 
    }
  }

  def currentMission(id: Long) = WithTeam(id) { team => implicit request => 
    Mission.findCurrentByTeamId(id).map { mission =>
      Ok(views.html.missionCard(team, mission))
    }.getOrElse {
      Ok(views.html.allcomplete())
    }
  }

  def nextMission(id: Long) = WithTeam(id) { team => implicit request => 
    Mission.findNextByTeamId(id).map { mission =>
      Ok(views.html.missionCard(team, mission))
    }.getOrElse {
      Ok("All mission accomplished")
    }
  }
}

trait TeamsSecured {
  def WithTeam(id: Long)(f: Team => Request[AnyContent] => Result) = Action { request =>
    request.session.get("teamId").map { index =>
      if(id == index.toLong) {
        Team.findOneByIndex(index.toLong).map { team =>
          f(team)(request)
        }.getOrElse {
          Results.Ok(views.html.error())
        }
      } else {
        Results.Redirect(routes.Teams.index(index.toLong)).flashing(
          "fail" -> "이놈!! 다른팀에 들오면안되요. :)"
        )
      }
    }.getOrElse {
      Results.Redirect(routes.Teams.login).flashing(
        "fail" -> "로그인이 필요해요 :("
      )
    }
  }
}
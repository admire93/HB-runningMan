import java.util.Date

import play.api._
import play.api.mvc._
import play.api.mvc.Results._

import anorm._

import models._

object Global extends GlobalSettings {

  override def onStart(app: Application) {
    val now = new Date
    if(Mission.findAll.isEmpty) {
      List[Mission](
        Mission(
          NotAssigned,
          "강도사님을 이겨라",
          "강도사님을 탁구로 이기세요! 1명이 3점내기로 강도사님을 이기면 미션이 종료됩니다.",
          "교육관 무대 앞",
          now
        ),
        Mission(
          NotAssigned,
          "볼링",
          "볼링을 쳐서 성공시키세요! 단 단순한 볼링은 아닙니다 :-)",
          "교육관 출구 앞",
          now
        ),
        Mission(
          NotAssigned,
          "성경 퀴즈!!!",
          "성경 퀴즈를 각자 한문제씩 30초안에 맞춰주세요. :~|",
          "본관 유아실",
          now
        ),
        Mission(
          NotAssigned,
          "여러번 말해요 :)",
          "주어진 문장을 20초안에 3번씩 읽어주세요.",
          "본관 성가대",
          now
        ),
        Mission(
          NotAssigned,
          "몸으로 말해요.",
          "30초안에 주어진 단어를 몸으로 설명해주세요.",
          "교육관",
          now
        )
      ).map { mission =>
        Mission.add(mission)
      }
    }
  }  

}
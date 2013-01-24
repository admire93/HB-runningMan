package models

import java.util.Date

import play.api.db._
import play.api.Play.current
import play.api.Play

import anorm._
import anorm.SqlParser._

case class Mission(id: Pk[Long], name: String, description: String, place: String, createdAt: Date)

object Mission {

  val missionParser = {
    get[Pk[Long]]("id")~
    get[String]("name")~
    get[String]("description")~
    get[String]("place")~
    get[Date]("created_at") map {
      case id~n~d~p~ca => Mission(id, n, d, p, ca)
    }
  }

  def findAll: List[Mission] = {
    DB.withConnection { implicit con =>
      SQL(
        """
          SELECT *
          FROM hb_mission
        """
      ).as(
        missionParser.*
      )
    }
  }

  def add(m: Mission): Option[Long] = {
    DB.withConnection { implicit con =>
      SQL(
        """
          INSERT INTO hb_mission
          (name, description, place, created_at)
          VALUES
          ({name}, {description}, {place}, {now})
        """
      ).on(
        'name -> m.name,
        'description -> m.description, 
        'place -> m.place,
        'now -> m.createdAt
      ).executeInsert()
    } 
  } 

}
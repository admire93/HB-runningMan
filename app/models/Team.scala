package models

import java.util.Date

import play.api.db._
import play.api.Play.current
import play.api.Play

import anorm._
import anorm.SqlParser._

case class Team(index: Pk[Long], id: String, password: String, name: String, createdAt: Date)
object Team {

  val teamParser =  {
    get[Pk[Long]]("id")~
    get[String]("identity")~
    get[String]("password")~
    get[String]("name")~
    get[Date]("created_at") map {
      case id~iden~pw~n~ca => Team(id, iden, pw, n, ca)
    }
  }

  def findAll: List[Team] = {
    DB.withConnection { implicit con =>
      SQL(
        """
          SELECT * 
          FROM hb_team
        """
      ).as(
        teamParser.*
      )
    }
  }

  def findOneByIndex(id: Long): Option[Team] = {
    DB.withConnection { implicit con =>
      SQL(
        """
          SELECT * 
          FROM hb_team
          WHERE id = {index}
        """
      ).on(
        'index -> id
      ).as(teamParser.singleOpt)
    }
  }

  def auth(id: String, pw: String): Option[Team] = {
    DB.withConnection { implicit con =>
      SQL(
        """
          SELECT * 
          FROM hb_team
          WHERE identity = {identity}
                AND password = {pw}
        """
      ).on(
        'identity -> id,
        'pw -> pw
      ).as(teamParser.singleOpt)
    }
  }

  def add(t: Team): Option[Long] = {
    DB.withConnection { implicit con =>
      SQL(
        """
          INSERT INTO hb_team
          (identity, password, name, created_at)
          VALUES
          ({identity}, {password}, {name}, {now})
        """
      ).on(
        'identity -> t.id,
        'password -> t.password, 
        'name -> t.name,
        'now -> t.createdAt
      ).executeInsert()
    } 
  } 
}
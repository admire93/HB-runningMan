package models

import java.util.Date

import play.api.db._
import play.api.Play.current
import play.api.Play

import anorm._
import anorm.SqlParser._

case class Mission(id: Pk[Long], name: String, description: String, place: String, createdAt: Date)
case class MissionWithStatus(id: Pk[Long], name: String, description: String, place: String, createdAt: Date, status: String, modifiedAt: Date)

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
  val missionStatusParser = {
    get[Pk[Long]]("hb_mission.id")~
    get[String]("hb_mission.name")~
    get[String]("hb_mission.description")~
    get[String]("hb_mission.place")~
    get[Date]("hb_mission.created_at")~
    get[String]("hb_progress.prog")~
    get[Date]("hb_progress.modified_at") map {
      case id~n~d~p~ca~st~ma => MissionWithStatus(id, n, d, p, ca, st, ma)
    }
  }

  def findAll: List[Mission] = {
    DB.withConnection { implicit con =>
      SQL(
        """
          SELECT *
          FROM hb_mission
          ORDER BY id ASC
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

  def findAllWithStatusByTeamId(teamId: Long): List[MissionWithStatus] = {
    DB.withConnection { implicit con =>
      SQL(
        """
          SELECT m.*, p.prog, p.modified_at
          FROM hb_mission m, hb_progress p
          WHERE m.id = p.mission_id
                AND p.team_id = {teamId}
          ORDER BY id ASC
        """
      ).on(
        'teamId -> teamId
      ).as(
        missionStatusParser.*
      )
    } 
  }

  def findCurrentByTeamIdAndMissionId(teamId: Long, missionId: Long): Option[MissionWithStatus] = {
    DB.withConnection { implicit con =>
      SQL(
        """
          SELECT m.*, p.prog, p.modified_at
          FROM hb_mission m, hb_progress p
          WHERE m.id = p.mission_id
                AND p.team_id = {teamId}
                AND p.mission_id = {missionId}
                AND p.prog = 'current'
          LIMIT 1
        """
      ).on(
        'teamId -> teamId,
        'missionId -> missionId
      ).as(
        missionStatusParser.singleOpt
      )
    } 
  }

  def findCurrentByTeamId(teamId: Long): Option[MissionWithStatus] = {
    DB.withConnection { implicit con =>
      SQL(
        """
          SELECT m.*, p.prog, p.modified_at
          FROM hb_mission m, hb_progress p
          WHERE m.id = p.mission_id
                AND p.team_id = {teamId}
                AND (
                  p.prog = 'current'
                  OR
                  p.prog = 'progress'
                )
          LIMIT 1
        """
      ).on(
        'teamId -> teamId
      ).as(
        missionStatusParser.singleOpt
      )
    } 
  }

  def findNextByTeamId(teamId: Long): Option[MissionWithStatus] = {
    DB.withConnection { implicit con =>
      SQL(
        """
          SELECT m.*, p.prog, p.modified_at
          FROM hb_mission m, hb_progress p
          WHERE m.id = p.mission_id
                AND p.team_id = {teamId}
                AND p.prog = 'next'
          LIMIT 1
        """
      ).on(
        'teamId -> teamId
      ).as(
        missionStatusParser.singleOpt
      )
    } 
  }

  def complete(teamId: Long, missionId: Long) = {
    val now = new Date
    DB.withConnection { implicit con =>
      SQL(
        """
          UPDATE hb_progress
          SET modified_at = {now}, prog = 'complete'
          WHERE mission_id = {missionId}
                AND team_id = {teamId}
        """
      ).on(
        'now -> now,
        'missionId -> missionId,
        'teamId -> teamId
      ).executeUpdate()

      SQL(
        """
          UPDATE hb_progress
          SET prog = 'current'
          WHERE mission_id = {nextMissionId}
                AND team_id = {teamId}
        """
      ).on(
        'now -> now,
        'nextMissionId -> (missionId + 1),
        'teamId -> teamId
      ).executeUpdate()

      SQL(
        """
          UPDATE hb_progress
          SET prog = 'next'
          WHERE mission_id = {missionId}
                AND team_id = {teamId}
        """
      ).on(
        'now -> now,
        'missionId -> (missionId + 2),
        'teamId -> teamId
      ).executeUpdate()
    }
  }

  def start(missionId: Long, teamId: Long) = {
    DB.withConnection { implicit con =>
      val now = new Date
      SQL(
        """
          UPDATE hb_progress
          SET created_at = {now}, prog = 'progress'
          WHERE mission_id = {missionId}
                AND team_id = {teamId}
        """
      ).on(
        'now -> now,
        'missionId -> missionId,
        'teamId -> teamId
      ).executeUpdate()
    }
  }

  def initMissionProgress(teamId: Long) = {
    DB.withConnection { implicit con =>
      var now =   new Date
      for((item, i) <- findAll.view.zipWithIndex) {
        var sql: SimpleSql[Row] = null
        now = new Date
        if(i == 0) { 
          sql = SQL(
            """
              INSERT INTO hb_progress
              (mission_id,team_id,prog,created_at,modified_at)
              VALUES
              ({missionId}, {teamId}, {prog}, {now}, {now})
            """
          ).on(
            'missionId -> item.id,
            'teamId -> teamId,
            'prog -> "current",
            'now -> now
          )
        } else if(i == 1) {
          sql = SQL(
            """
              INSERT INTO hb_progress
              (mission_id, team_id, prog, created_at, modified_at)
              VALUES
              ({missionId}, {teamId}, {prog}, {now}, {now})
            """
          ).on(
            'missionId -> item.id,
            'teamId -> teamId,
            'prog -> "next",
            'now -> now
          )
        } else {
          sql = SQL(
            """
              INSERT INTO hb_progress
              (mission_id, team_id, prog, created_at, modified_at)
              VALUES
              ({missionId}, {teamId}, {prog}, {now}, {now})
            """
          ).on(
            'missionId -> item.id,
            'teamId -> teamId,
            'prog -> "stay",
            'now -> now
          )
        }
        sql.executeInsert()
      }
      true
    }
  }

}
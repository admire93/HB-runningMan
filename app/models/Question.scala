package models

import java.util.Date

import play.api.db._
import play.api.Play.current
import play.api.Play

import anorm._
import anorm.SqlParser._

case class Question(id: Pk[Long], kind: String, question: String, answer: String)

object Question {

  val questionParser = {
    get[Pk[Long]]("id")~
    get[String]("kind")~
    get[String]("question")~
    get[String]("answer") map {
      case i~k~q~a => Question(i, k, q, a)
    }
  }

  def findAll: List[Question] = {
    DB.withConnection { implicit conn =>
      SQL(
        """
          SELECT *
          FROM hb_question
        """
      ).as(questionParser.*)
    }
  }

  def findRandom(kind: String, limit: Long): List[Question] = {
    DB.withConnection { implicit con =>
      SQL(
        """
          SELECT *
          FROM hb_question
          WHERE kind = {kind}
          ORDER BY RAND()
          LIMIT {limit}
        """
      ).on(
        'kind -> kind,
        'limit -> limit
      ).as(questionParser.*)
    }
  }
  def add(q: Question): Option[Long] = {
    DB.withConnection { implicit conn =>
      SQL(
        """
          INSERT INTO hb_question
          (kind, question, answer)
          VALUES
          ({kind}, {question}, {answer})
        """
      ).on(
        'kind -> q.kind,
        'question -> q.question,
        'answer -> q.answer
      ).executeInsert()
    }
  }
}

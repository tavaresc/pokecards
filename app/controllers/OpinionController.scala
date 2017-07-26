package controllers

import play.api._
import play.api.mvc._
import play.api.i18n._
import play.api.data.Form
import play.api.data.Forms._
import play.api.data.validation.Constraints._
import play.api.libs.json.Json
import scala.concurrent.{ ExecutionContext, Future }
import javax.inject._
import models._ // for pokemon models
import dal._ // for database

/**  This controller access a database to obtain `Like/Dislike` entries for pokemons. It also updates it.
  *  @param repo the database
  *  @param messagesApi an acessor to the database
  *  @param exec We need an `ExecutionContext` to execute our asynchronous code.
  */
class OpinionController @Inject()(repo: LikeStatRepository, val messagesApi: MessagesApi)
                                 (implicit exec: ExecutionContext) extends Controller with I18nSupport{

  /** Add a `Like` to a Pokemon
    * @param pkstat the current like stats
    * @return the stats + 1
    */
  def likes(pkstat:PokemonLikeStats) = {
    pkstat.copy(likes = pkstat.likes + 1)
  }

  /** Add a `Dislike` to a Pokemon
    * @param pkstat the current dislike stats
    * @return the stats + 1
    */
  def dislike(pkstat:PokemonLikeStats) = {
    pkstat.copy(dislikes = pkstat.dislikes + 1)
  }

  /** Find and update `Like/Dislike` stats in the database
    * @param name the pokemon name
    * @param pks the list of stats from the database
    * @param f a function to be applied on the selected stat
    * @return a Future updated stat
    */
  def findAndModify(name:String, pks:Seq[PokemonLikeStats], f: PokemonLikeStats => PokemonLikeStats): Future[Result] = {
    pks.find(pstat => name.equals(pstat.name)) match {
      case None => Future { Ok(Json.toJson(PokemonLikeStats(0, name, 0, 0))) }
      case Some(pkstat) =>
        val pstat = f(pkstat)
        val pkss = repo.update(f(pkstat))
        Future { Ok(Json.toJson(pstat)) }
    }
  }

  // Algebraic data type to perform pattern matching
  sealed trait Opinion
  case class Like() extends Opinion
  case class Dislike() extends Opinion

  /** Define which function is going to be called to modify a stat in function of being a `like` or a `dislike`.
    * @param name the pokemon name
    * @param pks the list of stats from the database
    * @param o an opinio `Like/Dislike`
    */
  def findAndAddOpinion(name:String, pks:Seq[PokemonLikeStats], o:Opinion): Future[Result]  = {
    val f =
      o match {
        case Like() => likes(_)
        case Dislike() => dislike(_)
      }
    findAndModify(name, pks, f)
  }

  /** Add an opinion on the database
    *
    * @param name the pokemon name
    * @param opinion the opinion `like` or `dislike`
    * @return a Future `like/dislike` stat
    */
  private def addOpinion(name:String, opinion:Opinion): Future[Result]  = {
    for {
      pks <- repo.list()
      r <- findAndAddOpinion(name, pks, opinion)
    } yield r
  }

  /** Define a shortcut to a function `add like` (an opinion for a pokemon)
    * @param name the pokemon name
    * @return the function add like
    */
  def addLike(name:String) = Action.async { addOpinion(name, Like()) }

  /** Define a shortcut to a function `add dislike` (an opinion for a pokemon)
    * @param name the pokemon name
    * @return the function add dislike
    */
  def addDisLike(name:String) = Action.async { addOpinion(name, Dislike()) }

  /**
    * A REST endpoint that gets all the people as JSON.
    */
  def get = Action.async {
    repo.list().map { pks => Ok(Json.toJson(pks))
    }
  }

  /** Find an opinion `Like/Dislike` to a pokemon in the database. It creates a new entry if necessary.
    * @param name the pokemon name
    * @return a Future opinion
    */
  def getOpinion(name:String) = Action.async {
    for {
      lk_stat_f <- repo.get(name).map {
        stats => stats match {
          case None => repo.create(name)
          case Some(v) => Future { v }
        }
      }
      lk_stat <- lk_stat_f
    } yield Ok(Json.toJson(lk_stat))
  }
}
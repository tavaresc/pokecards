package controllers

import akka.actor.ActorSystem
import javax.inject._

import play.api._
import play.api.mvc._
import play.api.libs.json._

import scala.concurrent.{ExecutionContext, Future}
import scala.collection.mutable

import pokedex._ // for pokedex model
import dal.{PokemonStatRepository, PokemonTypeStatsRepository} // for database models of stats and type

/** This class calculates everything related to type stats of pokemons.
  * @param pkstats_repo pokemon stats on database
  * @param tystats_repo pokemon (average) type stats on database
  * @param papi an accessor to the pokedex REST API
  * @param exec We need an `ExecutionContext` to execute our
  *             asynchronous code.
  */
@Singleton
class StatsController @Inject()(  pkstats_repo : PokemonStatRepository
                                  , tystats_repo : PokemonTypeStatsRepository
                                  , papi         : pokedex.API)
                               (implicit exec:ExecutionContext) extends Controller {

  /** Sort type stats by lexicographic order on type names.
    *
    * @param p a pokemon
    * @return  copy of pokemon with orderd type stats
    */
  private def reorder(p:Pokemon) = {
    p.copy(stats = p.stats.sortBy(st => st.stat.name))
  }

  /**
    * Get a list of stats from the database sorted by stat name from each pokemon name.
    * @param name a valid PokemonType
    * @return a (Future) list of type stats
    */
  private def localGetStats(name:String): Future[models.PokemonStats] = {
    Logger.debug("Request for stats for " + name)
    for {
      stats <- pkstats_repo.get(name)
      pkstat <-
      stats match {
        case None =>
          papi.getPokemonFromName(name).map(
            pokemon => {
              val pkstats = models.PokemonStats.ofPokemon(pokemon)
              pkstats_repo.create(pkstats) // We ignore the result of the create
              pkstats
            }
          )
        case Some(stat) => Future {
          stat
        }
      }
    } yield pkstat
  }


  /** Request for a list of stats and converts it from Future to JSON
    *
    * @param name the type name
    * @return the type stats
    */
  def getStats(name:String) = Action.async {
    for {
      pkstat <- localGetStats(name)
    } yield Ok(Json.toJson(pkstat))
  }

  /**
    * Request calculation of average stats for each pokemon type
    * @param typename the type name
    * @result a (Future) a type stats
    */
  private def computeTypeStats(typename:String): Future[models.PokemonTypeStats]= {
    for {
      ty <- papi.getType(typename)
      /** Hard hypothesis here: localGetStats never fails
        * Otherwise we might want to try something like what is described at
        * https://stackoverflow.com/questions/20874186/scala-listfuture-to-futurelist-disregarding-failed-futures
        */
      // `Future.sequence()` allows handling Future objects as static ones
      pkstats <- Future.sequence(ty.pokemon.map(ty => localGetStats(ty.pokemon.name)))
    } yield models.PokemonTypeStats.averageFromPokemons(ty.id, ty.name, pkstats)
  }

  /** Get average stats for a type into the database. It also calls a method to calculate it in case of new entries.
    *
    * @param typename the type name
    * @return a Future type stats
    */
  private def localGetTypeStats(typename:String): Future[models.PokemonTypeStats] = {
    for {
      tystats <- tystats_repo.get(typename)
      tystat <-
      tystats match {
        case None => // It's not in DB yet
          computeTypeStats (typename).map (
            tyst =>{
              tystats_repo.create(tyst)
              tyst}
          )
        case Some (tyst) => Future { tyst }
      }
    } yield tystat
  }

  /** Request for average type stats and converts it from Future to JSON
    *
    * @param typename the type name
    * @return the type stats
    */
  def getTypeStats(typename: String) = Action.async {
    Logger.debug("----------------------------- Request for type stats for type: " + typename)
    for {
      st <- localGetTypeStats(typename)
    } yield Ok(Json.toJson(st))
  }
}
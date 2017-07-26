/**
  * This package is responsible for all that concerns the interaction with the
  * local Slick database. It manipulates data got from the PokeAPI and modeled via package `models`.
  * Its base on Play application example available at:
  * https://github.com/playframework/play-scala-slick-example
  */
package dal

import javax.inject.{Inject, Singleton}
import play.api.db.slick.DatabaseConfigProvider
import slick.driver.JdbcProfile
import scala.concurrent.{ExecutionContext, Future}
import models.PokemonStats // use defined models for PokemonStats

/**
  * A repository for Pokemon stats.
  *
  * @param dbConfigProvider The Play db config provider. Play will inject this for you.
  */
@Singleton
class PokemonStatRepository @Inject()(dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext) {
  // We want the JdbcProfile for this provider
  private val dbConfig = dbConfigProvider.get[JdbcProfile]

  // These imports are important, the first one brings db into scope, which will let you do the actual db operations.
  // The second one brings the Slick DSL into scope, which lets you define the table and other queries.
  import dbConfig._
  import driver.api._

  /**
    * Here we define the table. It will have the name `pokemon_stats`
    * @param tag the name of the table
    */
  private class PokemonStatsTable(tag: Tag) extends Table[PokemonStats](tag, "pokemon_stats") {

    /** The ID column, which is the primary key, and auto incremented */
    def id = column[Int]("id", O.PrimaryKey, O.AutoInc)

    /** The name column */
    def name = column[String]("name")

    /** A column for each kind of stats */
    def attack = column[Int]("attack")
    def defense = column[Int]("defense")
    def hp = column[Int]("hp")
    def speed = column[Int]("speed")
    def sp_attack = column[Int]("sp_attack")
    def sp_defense = column[Int]("sp_defense")

    /** The depiction column */
    def img = column[String]("img")

    /** The pokemon types column */
    def types = column[String]("types")

    /**
      * The separator for types into a single String with all types.
      */
    private val sep = ":"

    /**
      * The aux_tupled an aux_unapply below are helpers for a hack
      * Types are stored in the DB as a string, with a ':' between each types
      * Therefore:
      * - aux_tupled splits the types String to a List[String]
      * - aux_unapply does the reverse: the List[String] is converted to a String
      *
      * The hack stays encapsulated in the table driver
      *
      * A cleaner solution would be to have another table registering this one-to-many relationship name x types
      */

    /**
      * Split the types String to a List[String]
      * @param id the pokemon id
      * @param name the pokemon name
      * @param speed the pokemon speed stats
      * @param attack the pokemon attack stats
      * @param defense the pokemon defense stats
      * @param hp the pokemon hp stats
      * @param sp_attack the pokemon super-attack stats
      * @param sp_defense the pokemon super-defense stats
      * @param img the pokemon depiction
      * @param types the types of the pokemon into a single string
      * @return a list of types of the pokemon
      */
    private def aux_tupled (id:Int, name:String, speed:Int,
                            attack:Int, defense:Int, hp:Int, sp_attack:Int, sp_defense:Int, img:String, types:String)
    = {
      models.PokemonStats(id, name, speed, attack, defense, hp, sp_attack, sp_defense, img,
        types.split(sep).toList
      )
    }

    /**
      * Split the List[String] to a single types String
      * @param p the list of types of a pokemon
      * @return the types of a pokemon into a single string
      */
    private def aux_unapply(p:PokemonStats) = {
      PokemonStats.unapply(p) match {
        case None => None
        case Some(p) => Some(p.copy(_10 = p._10.mkString(sep)))
      }
    }

    /**
      * This is the tables default "projection".
      *
      * It defines how the columns are converted to and from the PokemonStats object.
      * The `(aux_tupled _)` instruction works like `(PokemonStats.apply _)`
      */
    def * = (id, name, speed, attack, defense, hp, sp_attack, sp_defense, img, types) <> ((aux_tupled _).tupled, aux_unapply)
  }

  /**
    * The starting point for all queries on the stats table.
    */
  private val stats = TableQuery[PokemonStatsTable]

  /**
    * Create a stats entry.
    *
    * This is an asynchronous operation, it will return a future of the created stats
    * @param pkstats the stats of a pokemon
    * @return (Future) stats of a pokemon
    */
  def create(pkstats : PokemonStats): Future[PokemonStats] = db.run {
    (stats.map(s => s)
      returning stats.map(_.id)
      into ((v, _) => v)
    ) += pkstats
  }

  /**
    * Get the stats of a pokemon on the DB by name
    * @param pokemon_name the pokemon name
    * @return either the (Future) stats for this pokemon (with Some) or None if it does not exist
    */
  def get(pokemon_name:String): Future[Option[PokemonStats]] = db.run {
    stats.filter(_.name === pokemon_name).result.map {
      pokemons => // pokemons:Vector[PokemonStats]
        pokemons match {
          case Nil => None
          case p +: _ => Some(p)
        }
    }
  }

  /**
    * List all the pokemon stats in the database.
    * @return the (Future) current stats
    */
  def list(): Future[Seq[PokemonStats]] = db.run {
    stats.result
  }
}
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

import models.PokemonTypeStats // use defined models for PokemonTypeStats


/**
  * A repository for Pokemon type stats.
  *
  * @param dbConfigProvider The Play db config provider. Play will inject this for you.
  */
@Singleton
class PokemonTypeStatsRepository @Inject()(dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext) {
  // We want the JdbcProfile for this provider
  private val dbConfig = dbConfigProvider.get[JdbcProfile]

  // These imports are important, the first one brings db into scope, which will let you do the actual db operations.
  // The second one brings the Slick DSL into scope, which lets you define the table and other queries.
  import dbConfig._
  import driver.api._

  /**
    * Here we define the table. It will have the name `pokemon_type_stats`
    * @param tag the name of the table
    */
  private class PokemonTypeStatsTable(tag: Tag) extends Table[PokemonTypeStats](tag, "pokemon_type_stats") {

    /** The ID column, which is the primary key, and auto incremented */
    def id = column[Int]("id", O.PrimaryKey, O.AutoInc)

    /** Other columns */
    def name = column[String]("name")
    def attack = column[Int]("attack")
    def defense = column[Int]("defense")
    def hp = column[Int]("hp")
    def speed = column[Int]("speed")
    def sp_attack = column[Int]("sp_attack")
    def sp_defense = column[Int]("sp_defense")

    /**
      * This is the tables default "projection".
      *
      * It defines how the columns are converted to and from the PokemonTypeStats object.
      *
      * In this case, we are simply passing the id, name and each stats parameters to the PokemonTypeStats case classes
      * apply and unapply methods.
      */
    def * = (id, name, speed, attack, defense, hp, sp_attack, sp_defense) <> ((PokemonTypeStats.apply _).tupled, PokemonTypeStats.unapply)
  }

  /**
    * The starting point for all queries on the stats table.
    */
  private val stats = TableQuery[PokemonTypeStatsTable]

  /**
    * Create a stats entry.
    *
    * This is an asynchronous operation, it will return a future of the created stats
    * @param pkstats the stats of a pokemon type
    * @return (Future) stats of a pokemon type
    */
  def create(pkstats : PokemonTypeStats): Future[PokemonTypeStats] = db.run {
    (stats.map(s => s)
      returning stats.map(_.id)
      into ((v, _) => v)
      ) += pkstats
  }

  /**
    * Get the stats of a pokemon type on the DB by typename
    * @param type_name the name of the type for which to retrieve average stats
    * @return either the (Future) average stats for this type (with Some) or None if it does not exist
    */
  def get(type_name:String): Future[Option[PokemonTypeStats]] = db.run {
    stats.filter(_.name === type_name).result.map {
      pokemon_types => // pokemons:Vector[PokemonTypeStats]
        pokemon_types match {
          case Nil => None
          case p +: _ => Some(p)
        }
    }
  }

  /**
    * List all the pokemon type stats in the database.
    * @return the (Future) current stats for pokemon types
    */
  def list(): Future[Seq[PokemonTypeStats]] = db.run {
    stats.result
  }
}
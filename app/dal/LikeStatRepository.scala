/**
  * This package is responsible for all that concerns the interaction with the
  * local Slick database. It manipulates data got from the PokeAPI and modeled via package `models`.
  * Its base on Play application example available at:
  * https://github.com/playframework/play-scala-slick-example
  */
package dal

import javax.inject.{ Inject, Singleton }
import play.api.db.slick.DatabaseConfigProvider
import slick.driver.JdbcProfile
import scala.concurrent.{ Future, ExecutionContext }

import models.PokemonLikeStats // use defined models for PokemonLikeStats


/**
  * A repository for `Like/Dislike`.
  *
  * @param dbConfigProvider The Play db config provider. Play will inject this for you.
  * @param exec We need an `ExecutionContext` to execute our asynchronous code.

  */
@Singleton
class LikeStatRepository @Inject() (dbConfigProvider: DatabaseConfigProvider)(implicit exec: ExecutionContext) {
  // We want the JdbcProfile for this provider
  private val dbConfig = dbConfigProvider.get[JdbcProfile]

  // These imports are important, the first one brings db into scope, which will let you do the actual db operations.
  // The second one brings the Slick DSL into scope, which lets you define the table and other queries.
  import dbConfig._
  import driver.api._

  /**
    * Here we define the table. It will have the name `pklikestats`
    * @param tag the name of the table
    */
  private class LikeStatsTable(tag: Tag) extends Table[PokemonLikeStats](tag, "pklikestats") {

    /** The ID column, which is the primary key, and auto incremented */
    def id = column[Int]("id", O.PrimaryKey, O.AutoInc)

    /** The name column */
    def name = column[String]("name")

    def likes = column[Int]("likes")
    def dislikes = column[Int]("dislikes")

    /**
      * This is the tables default "projection".
      *
      * It defines how the columns are converted to and from the PokemonLikeStats object.
      *
      * In this case, we are simply passing the id, name, likes and dislikes parameters to the PokemonLikeStats case classes
      * apply and unapply methods.
      */
    def * = (id, name, likes, dislikes) <> ((PokemonLikeStats.apply _).tupled, PokemonLikeStats.unapply)
  }

  /**
    * The starting point for all queries on the likestats table.
    */
  private val likestats = TableQuery[LikeStatsTable]

  /**
    * Create a likestats with the given pokemon name.
    *
    * This is an asynchronous operation, it will return a future of the created stats, which can be used to obtain the
    * id for that pokemon.
    * @param name the pokemon name
    * @return (Future) `Like/Dislike` stats for the pokemon
    */
  def create(name: String): Future[PokemonLikeStats] = db.run {
    (likestats.map(s => (s.name, s.likes, s.dislikes))
      returning likestats.map(_.id)
      into ((v, id) => PokemonLikeStats(id, v._1, v._2, v._3))
      ) += (name, 0, 0)
  }

  /**
    * Update the likestats in the database
    *
    * @param st the stats to update
    * @return the (Future) likestats modified or added of one entry
    */
  def update(st: PokemonLikeStats): Future[Seq[PokemonLikeStats]] = {
    db.run {
      likestats.insertOrUpdate(st)
    }
    list()
  }

  /**
    * Get likestats in the database by name
    * @param pokemon_name the pokemon name
    * @return the stats of this pokemon
    */
  def get(pokemon_name:String): Future[Option[PokemonLikeStats]] = db.run {
    likestats.filter(_.name === pokemon_name).result.map {
      pokemons => // pokemons:Vector[PokemonLikeStats]
        pokemons match {
          case Nil => None
          case p +: _ => Some(p)
        }
    }
  }

  /**
    * List all the likestats in the database.
    * @return the (Future) current likestats
    */
  def list(): Future[Seq[PokemonLikeStats]] = db.run {
    likestats.result
  }
}
/**
  * This package is responsible for defining data models for the local Slick database.
  * Methods for manipulating these models are defined in package `dal`.
  */
package models

import play.api.libs.json.Json

/** Minimalistic representation of the average stats of a Pokemon type.
  * This uses the same basic stats.
  *
  * @param id the type id
  * @param name the type name
  * @param speed avg speed stats for this type
  * @param attack avg attack stats for this type
  * @param defense avg defense stats for this type
  * @param hp avg hp stats for this type
  * @param sp_attack avg super-attack stats for this type
  * @param sp_defense avg super-defense stats for this type
  */
case class PokemonTypeStats(  id: Int
                              , name:String
                              , speed: Int
                              , attack : Int
                              , defense : Int
                              , hp : Int
                              , sp_attack : Int
                              , sp_defense : Int
                           )


object PokemonTypeStats {
  /** JSON reader / writer for the PokemonTypeStats case class */
  implicit val pokemonTypeStatsFormat = Json.format[PokemonTypeStats]

  /** Calculate the average type stats of a type stats accumulator
    *
    * @param id the type id
    * @param name the type name
    * @param pkstats the type stats
    * @return the average type stats (n1 + ... + nx) / x
    */
  def averageFromPokemons(id:Int, name: String, pkstats: List[PokemonStats]): PokemonTypeStats = {
    val pts = PokemonTypeStats(id, name, 0, 0, 0, 0, 0, 0)
    val len = pkstats.length
    val pts_sum = pkstats.foldLeft(pts)(addPokemonStats)
    pts_sum.copy(
      speed = pts_sum.speed / len,
      attack = pts_sum.attack / len,
      defense = pts_sum.defense / len,
      hp = pts_sum.hp / len,
      sp_attack = pts_sum.sp_attack / len,
      sp_defense = pts_sum.sp_defense / len
    )
  }

  /** Add a single pokemon stats to the type stats accumulator
    *
    * @param t the type stats accumulator
    * @param p the pokemon stats
    * @return the result type stats accumulator (t + p)
    */
  private def addPokemonStats(t: PokemonTypeStats, p: PokemonStats): PokemonTypeStats = {
    PokemonTypeStats(
      t.id,
      t.name,
      t.speed + p.speed,
      t.attack + p.attack,
      t.defense + p.defense,
      t.hp + p.hp,
      t.sp_attack + p.sp_attack,
      t.sp_defense + p.sp_defense
    )
  }
}
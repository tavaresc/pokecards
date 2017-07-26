/**
  * This package is responsible for defining data models for the local Slick database.
  * Methods for manipulating these models are defined in package `dal`.
  */
package models

import play.api.libs.json.Json

/**
 * `Like/Dislike` stats for a pokemon.
 * @param id the pokemon id
 * @param name the pokemon name
 * @param likes number of likes
 * @param dislikes number os dislikes
 */
case  class PokemonLikeStats(id: Int, name: String, likes : Int, dislikes:Int)

/** JSON reader / writer for PokemonLikeStats case class */
object PokemonLikeStats {
  implicit val pokemonFormat = Json.format[PokemonLikeStats]
}
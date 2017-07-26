/**
  * This package is responsible for defining data models for the local Slick database.
  * Methods for manipulating these models are defined in package `dal`.
  */
package models

import play.api.libs.json._

/** Minimalistic representation of a Pokemon with its basic stats
  *
  * @param id the pokemon id
  * @param name the pokemon name
  * @param speed speed stats of pokemon
  * @param attack attack stats of pokemon
  * @param defense defense stats of a pokemon
  * @param hp hp stats of a pokemon
  * @param sp_attack super-attack stats of a pokemon
  * @param sp_defense super-defense stats of a pokemon
  * @param img the pokemon depiction
  * @param types the types of the pokemon
  */
case class PokemonStats(id: Int
                        , name:String
                        , speed: Int
                        , attack : Int
                        , defense : Int
                        , hp : Int
                        , sp_attack : Int
                        , sp_defense: Int
                        , img: String
                        , types : List[String]
                       )

object PokemonStats {
  /** JSON reader / writer for the PokemonStats case class */
  implicit  val pokemonFormat = Json.format[PokemonStats]

  /** Extract the stats from a Pokedex Pokemon
    * This function is not very efficient since every call to getStatValue has a O(n) cost,
    * therefore we have here a theoretical O(n^2) part.
    * In practice: the list of stats has 6 elements so it's not that bad
    * @param pokemon a pokemon from the pokedex
    * @return a pokemon stats
    */
  def ofPokemon(pokemon: pokedex.Pokemon): PokemonStats = {
    PokemonStats(
      pokemon.id,
      pokemon.name,
      pokedex.Pokemon.getStatValue(pokemon, "speed"),
      pokedex.Pokemon.getStatValue(pokemon, "attack"),
      pokedex.Pokemon.getStatValue(pokemon, "defense"),
      pokedex.Pokemon.getStatValue(pokemon, "hp"),
      pokedex.Pokemon.getStatValue(pokemon, "special-attack"),
      pokedex.Pokemon.getStatValue(pokemon, "special-defense"),
      pokemon.sprites.front_default match {
        case None => "" //TODO add path for local img pokeball.png
        case Some(url) => url
      },
      pokemon.types.map(ty => ty.`type`.name)
    )
  }
}
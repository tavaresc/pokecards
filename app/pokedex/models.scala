/**
  * This package is responsible for all that concerns the interaction with the
  * PokeAPI.
  * This file is to define the ADT -- Abstract Data Type -- (data model) for the
  * resources got from the Poke API version 2.
  */
package pokedex

import play.api.Logger

// we use Scala-JSON combinators
// https://www.playframework.com/documentation/2.5.x/ScalaJsonCombinators
import play.api.libs.json.Json

/*
 * NamedAPIResource (basic pokeapi element).
 * See http://pokeapi.co/docsv2/#namedapiresource
 * @param name the name of the resource
 * @param url the URL of the resource
 */
case class NamedAPIResource(name:String, url: String)
/** JSON reader / writer for NamedAPIResource case class */
object NamedAPIResource {
  implicit val namedUriFormats = Json.format[NamedAPIResource]
}

/*
 * PokemonStat (a NamedAPIResource) of a pokemon.
 * See https://pokeapi.co/docsv2/#pokemon
 * @param stat the stat of the pokemon
 * @param effort the effort points of the pokemon
 * @param base_stat the minimum value of stat
 */
case class PokemonStat(stat:NamedAPIResource, effort: Int, base_stat: Int)
/** JSON reader / writer for PokemonStat case class */
object PokemonStat {
  implicit val statFormats = Json.format[PokemonStat]
}

/*
 * PokemonAbility of a pokemon.
 * See https://pokeapi.co/docsv2/#pokemonability
 * @param is_hidden flag for the ability
 * @param slot the slot it occupies in species
 * @param ability the ability of the pokemon
 */
case class PokemonAbility(
                           is_hidden: Boolean,
                           slot: Int,
                           ability: NamedAPIResource
                         )
/** JSON reader / writer for PokemonAbility case class */
object PokemonAbility {
  implicit val abilityFormats = Json.format[PokemonAbility]
}

/*
 * PokemonMoveVersion of a pokemon.
 * See http://pokeapi.co/docsv2/#pokemonmoveversion
 * @param move_learn_method the method of learning
 * @param version_group the version group in which it is learnt
 * @param level_learned_at the minimum level to learn
 */
case class PokemonMoveVersion(
                               move_learn_method: NamedAPIResource,
                               version_group: NamedAPIResource,
                               level_learned_at: Int
                             )
/** JSON reader / writer for PokemonMoveVersion case class */
object PokemonMoveVersion {
  implicit val moveVersionFormats = Json.format[PokemonMoveVersion]
}

/*
 * PokemonMove of a pokemon.
 * See https://pokeapi.co/docsv2/#pokemonmove
 * @param move the move that can be learnt
 * @param version_group_details the version group in which it is
 */
case class PokemonMove(
                        move: NamedAPIResource,
                        version_group_details: List[PokemonMoveVersion]
                      )
/** JSON reader / writer for PokemonMove case class */
object PokemonMove {
  implicit val moveFormats = Json.format[PokemonMove]
}

/*
 * Sprite of a pokemon.
 * We use `Option` type since some pokemons do not have all the fields.
 * See https://pokeapi.co/docsv2/#moves
 * @param front_default the default depiction of a pokemon
 * @param front_shiny the depiction of a pokemon in a shy mood
 * @param front_female the depiction of a female pokemon
 * @param front_shiny_female the depiction of a female pokemon in a shy mood
 * @param back_default the default depiction of a pokemon's back
 * @param back_shiny the default depiction of a pokemon's back in a  shy mood
 * @param back_female the depiction of a female pokemon's back
 * @param back_shiny_female the depiction of female pokemon's back in shy mood
 */
case class PokemonSprites( front_default: Option[String]
                           , front_shiny: Option[String]
                           , front_female: Option[String]
                           , front_shiny_female: Option[String]
                           , back_default: Option[String]
                           , back_shiny: Option[String]
                           , back_female: Option[String]
                           , back_shiny_female: Option[String]
                         )
/** JSON reader / writer for PokemonSprites case class */
object PokemonSprites {
  implicit val spritesFormats = Json.format[PokemonSprites]
}

/*
 * Type of a pokemon.
 * See https://pokeapi.co/docsv2/#pokemontype
 * We add backquotes to the `type` parameter since is a Scala keyword; it also
 * facilitated the Json calling.
 * See https://stackoverflow.com/questions/34017680/scala-reserved-word-as-json-field-name-with-json-writesa-play-equivalent-for
 * @param slot the order (index) of a pokemon type
 * @param `type` the type of a pokemon
 */
case class PokemonType (slot: Int,
                        `type`: NamedAPIResource
                       )
/** JSON reader / writer for PokemonType case class */
object PokemonType {
  implicit val typeFormats = Json.format[PokemonType]
}

/**
  * Version of a pokemon item (what a pokemon holds).
  * See https://pokeapi.co/docsv2/#pokemonhelditemversion
  * @param version the version in which the item is hold
  * @param rarity the rarity degree of an item
  */
case class PokemonHeldItemVersion(
                                   version: NamedAPIResource,
                                   rarity: Int
                                 )
/** JSON reader / writer for PokemonHeldItemVersion case class */
object PokemonHeldItemVersion {
  implicit val heldItemVersionFormats = Json.format[PokemonHeldItemVersion]
}

/**
  * Item of a pokemon (what a pokemon holds).
  * See https://pokeapi.co/docsv2/#pokemonhelditem
  * @param item the item the pokemon holds
  * @param version_details the details of different version of the item
  */
case class PokemonHeldItem(item: NamedAPIResource,
                           version_details: List[PokemonHeldItemVersion])
/** JSON reader / writer for PokemonHeldItem case class */
object PokemonHeldItem {
  implicit val heldItemFormats = Json.format[PokemonHeldItem]
}

/**
  * Simplified version of a pokemon element (for internal purposes).
  * @param slot a joker slot
  * @param pokemon the real pokemon
  */
case class PokemonElement(slot:Int, pokemon: NamedAPIResource)
/** JSON reader / writer for PokemonElement case class */
object PokemonElement {
  implicit val elementFormats = Json.format[PokemonElement]
}

  /**
  * Single pokemon containing many resources
  * See https://pokeapi.co/docsv2/#pokemon
  * @param id the pokemon id
  * @param name the pokemon name
  * @param base_experience the base experience gained from this pokemon
  * @param height the height of the pokemon
  * @param weight the weight of the pokemon
  * @param is_default flag to set an single default pokemon
  * @param abilities the abilities of a pokemon
  * @param forms the forms a pokemon can takes
  * @param stats the base stats of a pokemon
  * @param moves the moves of a pokemon
  * @param sprites the sprites of a pokemon
  * @param species the species a pokemon belongs to
  * @param types the types a pokemon has
  * @param held_items the items a pokemon holds
  */
case class Pokemon(id: Int,
                   name: String,
                   base_experience: Int,
                   height: Int,
                   weight: Int,
                   is_default: Boolean,
                   abilities: List[PokemonAbility],
                   forms: List[NamedAPIResource],
                   stats: List[PokemonStat],
                   moves: List[PokemonMove],
                   sprites: PokemonSprites,
                   species: NamedAPIResource,
                   types: List[PokemonType],
                   held_items : List[PokemonHeldItem]
                  )
/** JSON reader / writer for Pokemon case class */
object Pokemon {
  implicit val pokemonFormat = Json.format[Pokemon]

  /** Get a given stats value. Defaults to 0 if not found.
    * @param pokemon a Pokemon
    * @param stat_name  the name of the stat we are looking for
    * @return the stats value
    */
  def getStatValue(pokemon: Pokemon, stat_name: String): Int = {
    pokemon.stats.find(p => p.stat.name.equals(stat_name)) match {
      case None =>
        Logger.warn("No value found for statistics %s of pokemon %s".format(stat_name, pokemon.name))
        0
      case Some(st) => st.base_stat
    }
  }
}

/*
// A placeholder for stats
case class Stat(name: String, value: Int)
case class AverageTypeStats(name: String, stats:List[Stat])
case class SimplePk(id:Int, name:String, stats: List[Stat])
*/

/** A slot for a type and a pokemon of that type.
  * See http://pokeapi.co/docsv2/#typepokemon
  * @param slot the order types are listed in
  * @param pokemon a pokemon
  */
case class PokemonTypePokemon(slot: Int, pokemon: NamedAPIResource)
/** JSON reader / writer for PokemonTypePokemon case class */
object PokemonTypePokemon {
  implicit val pokemonTypePokemonFormats = Json.format[PokemonTypePokemon]
}

// http://pokeapi.co/docsv2/#types
// We are just modeling a relevant subset of the fields
// BTW: the pokemon field does not conform to the specification
/** A pokemon type with all pokemons of that type
  * See http://pokeapi.co/docsv2/#types
  * @param id the type id
  * @param name the type name
  * @param pokemon the pokemons of this type
  */
case class Type( id: Int
                 , name : String
                 , pokemon: List[PokemonTypePokemon])
/** JSON reader / writer for Type case class */
object Type {
  implicit val typeFormats = Json.format[Type]
}

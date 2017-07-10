/*
 * This package is responsible for all that concerns the interaction with the
 * PokeAPI.
 * This file is to define the data model for the resources got from the Poke
 * API version 2.
 */
package pk

// Name and URL of a resource
// http://pokeapi.co/docsv2/#namedapiresource
case class NamedAPIResource(name:String, url: String)

// Stat (a NamedAPIResource), effort points in stat and base value of the stat
//https://pokeapi.co/docsv2/#pokemon
case class PokemonStat(stat:NamedAPIResource, effort: Int, base_stat: Int)

// Flag for hidden ability, the slot it occupies in species and ability
// https://pokeapi.co/docsv2/#pokemonability
case class PokemonAbility(
                         is_hidden: Boolean,
                         slot: Int,
                         ability: NamedAPIResource
                         )

// Method of learning, version group in which it is learned and minimum level to
// learn
// http://pokeapi.co/docsv2/#pokemonmoveversion
case class PokemonMoveVersion(
                             move_learn_method: NamedAPIResource,
                             version_group: NamedAPIResource,
                             level_learned_at: Int
                             )

// Move that can be learnt and in which version group
// https://pokeapi.co/docsv2/#pokemonmove
case class PokemonMove(
                      move: NamedAPIResource,
                      version_group_details: List[PokemonMoveVersion]
                      )


// Some do not have all the fields: what to do then ?
// Depiction or different angles, moods and genders
// https://pokeapi.co/docsv2/#pokemonsprites
case class PokemonSprites(
                         front_default: String,
                         front_shiny: String
                         )

// Order (index) and type of a pokemon
// https://pokeapi.co/docsv2/#pokemontype
// type is a keyword in Scala cannot use it
// see PkAPI to check how we read the value anyway
case class PokemonType (slot: Int, _type: NamedAPIResource)

// Version including item and rarity degree
// http://pokeapi.co/docsv2/#pokemonhelditemversion
case class PokemonHeldItemVersion(
                                 version: NamedAPIResource,
                                 rarity: Int
                                 )

// Item of the pokemon and details of different versions having this item
// https://pokeapi.co/docsv2/#pokemonhelditem
case class PokemonHeldItem(item: NamedAPIResource,
                           version_details: List[PokemonHeldItemVersion])

// A single Pokemon containing many resources
// https://pokeapi.co/docsv2/#pokemon
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

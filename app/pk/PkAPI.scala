/**
 * This package is responsible for all that concerns the interaction with the
 * PokeAPI.
 * This file is to define the connexion with the Poke API version 2.
 */
package pk

import scala.concurrent.ExecutionContext
import scala.util.{Failure, Random, Success}
import scala.concurrent.Future

// Enable the use o the WS API as a client able to process requests using 
// "dependency injection" (?)
import javax.inject.Inject
import play.api.libs.ws.{WSClient, WSRequest}

// Enable posting JSON data
import play.api.libs.json._
import play.api.libs.json.Reads._
import play.api.libs.functional.syntax._

/**
 * This class uses Json to do requests over the pokeapi.co and fill our model
 * @param ws We need the `WSClient` to call HTTP services.
 * @param exec We need an `ExecutionContext` to execute our
 * asynchronous code.
 */
class PkAPI @Inject() (ws:WSClient)(implicit exec: ExecutionContext) {

  // Set the poke API URL
  val base_url = "http://pokeapi.co"

  // Set other useful URLs
  private def extend_base_url(s:String):String = "%s/api/v2/%s/".format(base_url, s)
  private def pokemon_url() = extend_base_url("pokemon")
  private def named_pokemon_url(name:String) : String = extend_base_url("pokemon/%s".format(name))
  private def type_url(name:String) : String = extend_base_url("type/%s".format(name))

  // Automatic type convertion using Json
  // See https://www.playframework.com/documentation/2.5.x/ScalaJsonAutomated
  // See https://www.playframework.com/documentation/2.5.x/ScalaJsonCombinators
  implicit val namedUriReads = Json.reads[NamedAPIResource]
  implicit val statReads = Json.reads[PokemonStat]
  implicit val abilityReads = Json.reads[PokemonAbility]

  implicit val moveVersionReads = Json.reads[PokemonMoveVersion]
  implicit val moveReads = Json.reads[PokemonMove]
  implicit val spritesReads = Json.reads[PokemonSprites]

  implicit val typeReads = Json.reads[PokemonType]
  implicit val elementReads = Json.reads[PokemonElement]
  implicit val heldItemVersionReads = Json.reads[PokemonHeldItemVersion]
  implicit val heldItemReads = Json.reads[PokemonHeldItem]
  implicit val pokemonReads = Json.reads[Pokemon]

  /**
   * Request an HTML URL
   * @param uri the URL to call
   * @return the URL requested
   */
  // Might be useful to access resource URIs directly
  def getPureUri(uri:String) = {
    ws.url("%s%s".format(base_url,uri)).get.map{r => r.json}
  }

  /**
   * Get the pokemons from the pokeapi
   * @return a list of the first 20 pokemons
   */
  def getPokemon(): Future[List[Pokemon]] = {
    ws.url(pokemon_url()).get().map(r => r.json.as[List[Pokemon]])
  }

  /**
   * Get a pokemon from the pokeapi
   * @param name a pokemon name
   * @return the pokemon with that name
   */
  def getPokemonFromName(name: String): Future[Pokemon] = {
    ws.url(named_pokemon_url(name)).get().map(r => r.json.as[Pokemon])
  }

  /**
   * Get a pokemon from the pokeapi
   * @param id a pokemon id
   * @return the pokemon with that id
   */
  def getPokemonFromId(id: Int) = {
    getPokemonFromName("%d".format(id)) // This is an abuse w.r.t to typing
  }

  /**
   * Get all pokemons of an specific type from the pokeapi
   * @param pokemon_type a valid PokemonType
   * @return a list of pokemons with that type
   */
  def getAllPokemonSameType(pokemon_type: PokemonType):
    Future[List[PokemonElement]] = {
      val name = pokemon_type.`type`.name
      val url = type_url(name)

      println(url) // printing on sbt for debug
      ws.url(url).get.map(
        r => (r.json \ "pokemon").get.as[List[PokemonElement]])
  }
}

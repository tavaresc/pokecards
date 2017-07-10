/*
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

class PkAPI @Inject() (ws:WSClient)(implicit exec: ExecutionContext) {

  // Set the poke API URL
  val base_url = "http://pokeapi.co"

  private def extend_base_url(s:String):String = "%s/api/v2/%s/".format(base_url, s)

  private def pokedex_url() = extend_base_url("pokemon/?")
  private def pokemon_url() = extend_base_url("pokemon")
  private def named_pokemon_url(name:String) : String = extend_base_url("pokemon/%s".format(name))

  // implicit val pokedexReads = Json.reads[Pokedex]
  // Automatic type convertion
  // https://www.playframework.com/documentation/2.5.x/ScalaJsonAutomated
  implicit val namedUriReads = Json.reads[NamedAPIResource]
  implicit val statReads = Json.reads[PokemonStat]
  implicit val abilityReads = Json.reads[PokemonAbility]

  implicit val moveVersionReads = Json.reads[PokemonMoveVersion]
  implicit val moveReads = Json.reads[PokemonMove]
  implicit val spritesReads = Json.reads[PokemonSprites]

  // https://www.playframework.com/documentation/2.5.x/ScalaJsonCombinators
  // We are forced to do that because type is a Scala keyword
  // Therefore we need some manual mapping here, provided by the lines below
  implicit val typeReads : Reads[PokemonType] = (
    (JsPath \ "slot").read[Int] and
      (JsPath \ "type").read[NamedAPIResource]
    )(PokemonType.apply _)

  implicit val heldItemVersionReads = Json.reads[PokemonHeldItemVersion]
  implicit val heldItemReads = Json.reads[PokemonHeldItem]
  implicit val pokemonReads = Json.reads[Pokemon]

  // A HTTP request to the poke API.
  // It might be useful to access resource URIs directly
  def getPureUri(uri:String) = { ws.url("%s%s".format(base_url,uri)).get.map{r => r.json} }

  def getPokemonList() : Future[List[Pokemon]]= {
    val rq : WSRequest = ws.url(pokedex_url())
    // Process the json response
    val response = rq.get().map{ r => (r.json \ "results").get.as[List[Pokemon]]}
    response
  }

   def getPokemon() = {
    ws.url(pokemon_url()).get().map(r => r.json)
  }

  def getPokemonFromName(name:String): Future[Pokemon] = {
    ws.url(named_pokemon_url(name)).get().map{
      r => r.json.as[Pokemon]
    }

  }

  def getPokemonFromId(id: Int) = {
    getPokemonFromName("%d".format(id)) // This is an abuse w.r.t to typing
  }


  def getType() = {


  }
}

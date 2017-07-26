/**
  * This package is responsible for all that concerns the interaction with the
  * PokeAPI.
  * This file is to define the connexion with the Poke API version 2.
  */
package pokedex


// Using loggers
import play.api.Logger
import play.api.cache._

import scala.collection.JavaConverters._
import scala.concurrent.duration._

// Enable the use o the WS API as a client able to process requests using
// "dependency injection" (?)
import javax.inject.Inject
import play.api.libs.ws.{WSClient, WSRequest}
import scala.concurrent.ExecutionContext
import play.api.libs.json.Reads._
import scala.concurrent.Future
import play.api._

/**
  * This class uses JSON to do requests over the pokeapi.co and fill our model
  * @param ws We need the `WSClient` to call HTTP services.
  * @param exec We need an `ExecutionContext` to execute our asynchronous code.
  */
class API @Inject()(ws:WSClient, cache: CacheApi)(implicit exec: ExecutionContext) {

  // The list of URLs making Pokemon's REST API
  val base_url = "http://pokeapi.co"

  // Set other useful URLs
  private def extend_base_url(s:String):String = "%s/api/v2/%s/".format(base_url, s)
  private def pokedex_url() = extend_base_url("pokedex/1")
  private def pokemon_url() = extend_base_url("pokemon")
  private def named_pokemon_url(name:String) : String = extend_base_url("pokemon/%s".format(name))
  private def type_url(name:String) : String = extend_base_url("type/%s".format(name))

  // implicit val pokedexReads = Json.reads[Pokedex]

  /**
    * Get a pokemon from the pokeapi
    * @param name a pokemon name
    * @return the (Future) pokemon with that name
    */
  def getPokemonFromName(name:String): Future[Pokemon] = {
    Logger.debug("Retrieving pokemon characteristics for " + name)
    ws.url(named_pokemon_url(name)).get().map{
      r => r.json.as[Pokemon]
    }
  }

  /**
    * Get a pokemon from the pokeapi
    * @param id a pokemon id
    * @return the (Future) pokemon with that id
    */
  def getPokemonFromId(id: Int): Future[Pokemon] = {
    getPokemonFromName("%d".format(id)) // This is an abuse w.r.t to typing
  }

  /**
    * Get all pokemons of an specific type from the pokeapi.
    * @param pokemon_type a valid PokemonType
    * @return a (Future) list of pokemons with that type
    */
  def getAllPokemonSameType(pokemon_type:PokemonType) : Future[List[PokemonElement]]= {
    val name = pokemon_type.`type`.name
    val url = type_url(name)
    Logger.debug("Retrieving type characteristics for " + name)
    ws.url(url).get.map { r => (r.json \ "pokemon").get.as[List[PokemonElement]] }
  }

  /**
    * Get the type of a pokemon from the pokeapi.
    * @param typename a valid PokemonType name
    * @return a pokemon type
    */
  def getType(typename: String): Future[pokedex.Type] = {
    val url = type_url(typename)
    Logger.debug("Retrieving type characteristics for " + typename + " @ " + url)
    ws.url(url).get.map { r => r.json.as[pokedex.Type] }
  }

  /** Get all available pokemons from the pokeapi.
    * @return a (Future) list of pokemons
    */
  def listAvailablePokemons() : Future[List[String]] = {
    /** This is the number of Pokemons given by pokeapi as of now
      * We could also retrieve it by a first call to the same url
      * Count is indeed one of the fields of the JSON object returned by a call to
      * urlWithLimit
      */
    val pokemonMaxCount = 811;
    val url = "http://pokeapi.co/api/v2/pokemon/?limit=" + pokemonMaxCount
    Logger.debug("Pokemon url:" + url)
    // define cache (of Play) key
    val key = "all_pokemons"

    // Explicit type coercion seems to help here in not having server erros
    val pokemonNameList: Option[List[String]] = None // cache.get(key)
    pokemonNameList match {
      case None =>
        Logger.debug("Pokemon url:" + url)
      // First : retrieve the list of pokemon resources linked to the url
      val nameAPIResourceList: Future[List[NamedAPIResource]] =
      ws.url(url).get.map { r => (r.json \ "results").as[List[NamedAPIResource]] }


      // Second : extract only the names from the resources since this is the only information we want
      val pokemonNameList: Future[List[String]] =
          nameAPIResourceList.map { list => list.map(named_api_resource => named_api_resource.name) }

      /** Third and last: since we are in a cache miss (case None) we set the cache to the value
       *  before returning
       */
       pokemonNameList.map {
          list =>
            Logger.debug("Found %d pokemons : ".format(list.length) + list.toString())
              // cache.set(key, list, 5.minutes)
            list
    }
      case Some(pokemons) => Future { pokemons }
    }
  }
}
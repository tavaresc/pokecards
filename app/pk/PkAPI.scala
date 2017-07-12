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
   * Find all "brothers" of a pokemon from the pokeapi.
   * A brother is another pokemon with the same type.
   * @param pkmon a pokemon instance
   * @return all other pokemons having the same type
   */
  // Maybe it should be called a cousin ? Or a type_cousin ?
  def findBrothers(pkmon: Pokemon): Future[List[String]] = {
    // `aux` recursive auxiliar function to iterate on the list of PokemonType
    // by pattern matching
    def aux(types:List[PokemonType]): Future[List[String]] = {
      types match {
        case Nil => Future { List[String]() }
        case  ty :: tys =>
          println("Type: " + ty.`type`.name) // printing on sbt for debug
          val pokemon_elements = getAllPokemonSameType(ty)
          for { // `for-yield` combinator for future lists
            pelts <- pokemon_elements
            other_guys <- aux(tys)
          } yield ("New type " + ty.`type`.name) :: pelts.map(
            pty => pty.pokemon.name) ++ other_guys
      }
    }
    println("Pokemon Name: " + pkmon.name + "\n")
    aux(pkmon.types)
  }

  /**
   * Get all pokemons of an specific type from the pokeapi.
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

  /**
   * Get a list of stats for each pokemon name
   * @param pk_names a list of pokemon names
   * @result a (Future) list of stat list
   */
  //MOVE to Asynccontroller ?
  def getFutureStats(pk_names: Future[List[String]]):
    Future[List[(String, Int)]] = {
    def aux(names: List[String]): Future[List[List[(String, Int)]]] = {
      names match {
        case Nil => Future { List[List[(String, Int)]]() }
        case n :: ns => {
          println("Getting stats for " + n)// printing on sbt for debug
          for { // `for-yield` combinator for future lists
            stats <- getStats(n)
            other_stats <- aux(ns)
          } yield stats :: other_stats
        }
      }
    }
    for {
      pkn <- pk_names
      stats <- aux(pkn)
    } yield getAverageStats(stats)
  }

  /**
   * Get a list of stats sorted by stat name from each pokemon name.
   * @param pk_name a valid PokemonType
   * @return a list of pairs (stat_name, stat_level)
   */
  private def getStats(pk_name: String): Future[List[(String, Int)]] = {
    getPokemonFromName(pk_name).map(// map each name to its stats list
      pk =>
        // map each `stat` to (st_name, st_level) and sort the result by st_name
        pk.stats.map(st => (st.stat.name, st.base_stat)).sortBy {
          case (s, _) => s
        }
    )
  }

  /**
   * Get the average of each stat in a list of stat list.
   * It uses quite complex Scala combinators (foldLeft, zip)
   * @param stat_list a list of a list of pairs (stat_name, stat_level)
   * @return a list of pairs (stat_name, stat_avg)
   */
  private def getAverageStats(stat_list: List[List[(String, Int)]]):
    List[(String, Int)] = {
    stat_list match {
      case Nil => List[(String, Int)]()
      case sts :: _ => {
         // create a new stat list element with accumulator setted to 0
        val init = sts.map{ case (st_name, acc) => (st_name, 0) }
        
        /** Calculate the sum of each accumulator wrt the counter of previous stat
          * list element (which is also a list), i.e. innermost way.
          * We initialize the foldLeft with the new stat list element created
          * above.
          * (List(x1, ..., xn).foldLeft(z)(op) = (...(z op x1) op ... ) op xn
          */
        val acc_stats = stat_list.foldLeft(init) {
          /** Create ordered pairs from each stat list element (which is also a
            * list) using the `zip` function:
            * List(x1, ..., xn) zip List(y1, ..., ym)
            *   = List((x1, y1), ... (xn,yn))
            * Then we map each pair to their sum.
            */
          (sts_sum, sts_left) => 
            (sts_sum zip sts_left).map{
              case ((st_name, st_acc1), (_, st_acc2)) =>
                (st_name, st_acc1 + st_acc2)
            }
        }

        val len = stat_list.length
        // divide the sum of each accumulator by the list size to get average
        val avg_stats = acc_stats.map{
          case (st_name, st_acc) => (st_name, st_acc / len)
        }
        avg_stats
      }
    }
  }
}

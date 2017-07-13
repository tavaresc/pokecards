/**
 * This package is responsible for all that concerns the interaction with the
 * PokeAPI.
 * This file is to define the connexion with the Poke API version 2.
 */
package pk

// Using loggers
import play.api.Logger

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
    Logger.debug("getPokemonFromName PkAPI - name:" + name)
    ws.url(named_pokemon_url(name)).get().map{r => r.json.as[Pokemon]}
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
   * Find the types of a pokemon and collect all its "brothers" from the pokeapi.
   * A brother is another pokemon with the same type.
   * @param pkmon a pokemon instance
   * @return a list of pairs (type, brothers) for each pokemon type
   */
  // Maybe it should be called a cousin ? Or a type_cousin ?
  def findBrothers(pkmon: Pokemon):
    Future[List[(String, List[String])]] = {
    // `aux` recursive auxiliary function to iterate on a list of PokemonType
    // by pattern matching and collect a list of pairs (type, brothers)
    def aux(types:List[PokemonType]):
      Future[List[(String, List[String])]] = {
      types match {
        case Nil => Future { List[(String, List[String])]() }
        case  ty :: tys =>
          val typename = ty.`type`.name
          Logger.debug("Type: " + typename)
          val pokemon_elements = getAllPokemonSameType(ty)
          for { // `for-yield` combinator for future lists
            pelts <- pokemon_elements
            other_guys <- aux(tys)
          } yield (typename, pelts.map(pty => pty.pokemon.name)) :: other_guys
      }
    }
    //Logger.debug("Pokemon Name: " + pkmon.name + "\n")
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

    //Logger.debug(url)
    ws.url(url).get.map(
      r => (r.json \ "pokemon").get.as[List[PokemonElement]])
  }

  /**
   * Get a list of average stats for each pokemon type
   * @param pk_type_brothers a list of pairs (type, brothers)
   * @result a (Future) a list of pairs (type, avg_stats)
   */
  //MOVE to Asynccontroller ?
  def getFutureStats(pk_type_brothers: Future[List[(String, List[String])]]):
    Future[List[AverageTypeStats]] = {
    // `aux` recursive auxiliary function to iterate on a list of brothers'
    // name and collect its stats average
    def aux(names: List[String]): Future[List[List[(String, Int)]]] = {
      names match {
        case Nil => Future { List[List[(String, Int)]]() }
        case n :: ns => {
          //Logger.debug("Getting stats for " + n)
          for { // `for-yield` combinator for future lists
            stats <- getStats(n)
            other_stats <- aux(ns)
          } yield stats :: other_stats
        }
      }
    }

    // `loop` recursive auxiliary function to iterate on a list of (type,
    // brothers) and collect the average of brothers' stats
    def loop(classes: List[(String, List[String])]): 
      Future[List[AverageTypeStats]] = {
      classes match {
        case Nil => Future { List[AverageTypeStats]() }
        case (type_name, brothers_name) :: cls => {
          for {
            brothers_stats <- aux(brothers_name)
            other_classes <- loop(cls)
          } yield AverageTypeStats(type_name, getAverageStats(brothers_stats)) :: other_classes
        }
      }
    }


    for {
      classes <- pk_type_brothers
      avg_stats <- loop(classes)
    } yield avg_stats
  }

  /**
   * Get a list of stats sorted by stat name from each pokemon name.
   * @param pk_name a valid PokemonType
   * @return a list of pairs (stat_name, stat_level)
   */
  private def getStats(pk_name: String): Future[List[(String, Int)]] = {
    //Logger.debug("DEBUG getStats pPkAPI")
    getPokemonFromName(pk_name).map{// map each name to its stats list
      pk =>
        //Logger.debug("Inside getStats pPkAPI")
        // map each `stat` to (st_name, st_level) and sort the result by st_name
        pk.stats.map(st => (st.stat.name, st.base_stat)).sortBy {
          case (s, _) => s
        }
    }
  }

  /**
   * Get the average of each stat in a list of stat list.
   * It uses quite complex Scala combinators (foldLeft, zip)
   * @param stat_list a list of a list of pairs (stat_name, stat_level)
   * @return a list of pairs (stat_name, stat_avg)
   */
  private def getAverageStats(stat_list: List[List[(String, Int)]]):
    List[StatElement] = {
    stat_list match {
      case Nil => List[StatElement]()
      case sts :: _ => {
         // create a new stat list element with accumulator setted to 0
        val init = sts.map{ case (st_name, acc) => StatElement(st_name, 0) }
        
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
              case (StatElement(st_name, st_acc1), (_, st_acc2)) =>
                StatElement(st_name, st_acc1 + st_acc2)
            }
        }

        val len = stat_list.length
        // divide the sum of each accumulator by the list size to get average
        val avg_stats = acc_stats.map{
          case StatElement(st_name, st_acc) => StatElement(st_name, st_acc / len)
        }
        avg_stats
      }
    }
  }
}

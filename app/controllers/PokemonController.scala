package controllers

import javax.inject._
import play.api.libs.json._
import play.api.mvc._
import scala.concurrent.{ExecutionContext}

/**  Get all pokemons from the internal Pokemon API and maps them to Json data
  *  @param papi an accessor to the pokedex REST API
  *  @param exec We need an `ExecutionContext` to execute our asynchronous code.
  */
@Singleton
class PokemonController @Inject()(papi         : pokedex.API)
                                 (implicit exec:ExecutionContext) extends Controller {

  /** Get all pokemons from the internal Pokemon API and maps them to Json data */
  def index() = Action.async {
    val pokemons = papi.listAvailablePokemons()
    pokemons.map { pks => Ok(Json.toJson(pks)) }
  }
}
package controllers

import javax.inject._
import play.api.mvc._
import models.Pokemon

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * pokemon's home page with all cards.
 */
class Pokemons @Inject() extends Controller() {
  def list = Action { implicit request =>
    Ok(views.html.pokemons.list(List[pk.Pokemon]()))
    //val pokemons = Pokemon.findAll
    //Ok(views.html.pokemons.list(pokemons))
  }
}

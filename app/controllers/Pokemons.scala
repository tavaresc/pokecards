package controllers

import javax.inject._
import play.api.mvc._
import models.Pokemon

class Pokemons @Inject()(cc: ControllerComponents) extends AbstractController(cc) {
  def list = Action { implicit request =>
    val pokemons = Pokemon.findAll
    Ok(views.html.pokemons.list(pokemons))
  }
}

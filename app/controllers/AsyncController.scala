package controllers

import akka.actor.ActorSystem
import javax.inject._
import play.api._
import play.api.mvc._
import scala.concurrent.{ExecutionContext, Future, Promise}
import scala.concurrent.duration._
import scala.util.Random

/* Enable the use of the WS API */
import play.api.libs.ws._

/* Import my pokemon library */
import pk._

/**
 * This controller creates an `Action` that demonstrates how to write
 * simple asynchronous code in a controller. It uses a timer to
 * asynchronously delay sending a response for 1 second.
 *
 * @param actorSystem We need the `ActorSystem`'s `Scheduler` to
 * run code after a delay.
 * @param papi We need to call the poke API 'PkAPI'.
 * @param exec We need an `ExecutionContext` to execute our
 * asynchronous code.
 */
@Singleton
class AsyncController @Inject() (actorSystem: ActorSystem, papi:pk.PkAPI)(implicit exec: ExecutionContext) extends Controller {

  /**
   * Create an Action that returns a plain text message after a delay
   * of 1 second.
   *
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/message`.
   */
  def message = Action.async {
    //val id = Random.nextInt(150)
    
    // Get a list of all pokemons in the Poke API
    val p = papi.getPokemonList()
    p.map(pk =>
      // Ok("We're ready with %d pokemons".format(pkdx.length))
      Ok(views.html.index("Wohoo", pk))
    )
    //getFutureMessage(1.second).map { msg => Ok(msg) }
  }

  /**
   * Get a pokemon from the PokeAPI by its name and show it in the web page
   */
  def getName (name:String) = Action.async {
    val p = papi.getPokemonFromName(name)
    p.map(pk =>
      // Ok("We're ready with %d pokemons".format(pkdx.length))
      Ok(views.html.card(pk))
    )
    //getFutureMessage(1.second).map { msg => Ok(msg) }
  }

  private def getFutureMessage(delayTime: FiniteDuration): Future[String] = {
    val promise: Promise[String] = Promise[String]()
    actorSystem.scheduler.scheduleOnce(delayTime) { promise.success("Hi!") }
    promise.future
  }

}

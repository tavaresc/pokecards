package controllers

import akka.actor.ActorSystem
import javax.inject._

import play.api._
import play.api.mvc._

import scala.concurrent.{ExecutionContext, Future, Promise, future}
import scala.concurrent.duration._
import scala.util.Random
import scala.util.{Success, Failure}

// Import my pokemon library
import pk._



/**
 * This controller creates an `Action` that demonstrates how to write
 * simple asynchronous code in a controller. It uses a timer to
 * asynchronously delay sending a response for 1 second.
 *
 * @param actorSystem We need the `ActorSystem`'s `Scheduler` to
 * run code after a delay.
 * @param papi We need the `PkAPI` to build our Pokemon models and libraries
 * @param exec We need an `ExecutionContext` to execute our
 * asynchronous code.
 */
@Singleton
class AsyncController @Inject() (actorSystem: ActorSystem, papi:pk.PkAPI)
  (implicit exec:ExecutionContext) extends Controller {

  /**
   * Create an Action that returns a plain text message after a delay
   * of 1 second.
   *
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/message`.
   */
  def message = Action.async {
    val id = Random.nextInt(150)
    val p = papi.getPokemonFromId(id)
    p.map(pk =>
      // Ok("We're ready with %d pokemons".format(pkdx.length))
      Ok(views.html.pokemon_detail(pk, List[String](), List[(String, Int)]()))
    )
    //getFutureMessage(1.second).map { msg => Ok(msg) }
  }

  /**
   * Create an Action that returns the detailed view of a pokemon and all its
   * brothers
   * @param name the pokemon's name
   */
  def getName (name:String) = Action.async {
    val pkmon = papi.getPokemonFromName(name)
    for { // `for-yield` combinator for future lists
      p <- pkmon
      bs <- papi.findBrothers(p)
      stats <- papi.getFutureStats(papi.findBrothers(p))
    } yield Ok(views.html.pokemon_detail(p, bs, stats.take(5)))
  }

  /**
  * Working with Future objects
  */
  private def getFutureMessage(delayTime: FiniteDuration): Future[String] = {
    val promise: Promise[String] = Promise[String]()
    actorSystem.scheduler.scheduleOnce(delayTime) { promise.success("Hi!") }
    promise.future
  }

}

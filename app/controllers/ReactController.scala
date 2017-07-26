package controllers

import javax.inject._
import org.webjars.play.RequireJS
import play.api.mvc._
import scala.concurrent.ExecutionContext


/**  This controller displays everything based on React interfaces.
  *  Basically this does nothing apart from calling the dedicated Twirl template,
  *  which in turns is embedded into a main template including all needed
  *  CSS and Javascript libraries.
  *  @param webJarAssets an accessor to the WebJarAssets
  *  @param requireJS import nodeJS
  *  @param exec We need an `ExecutionContext` to execute our asynchronous code.
  */
class ReactController @Inject()(webJarAssets: WebJarAssets
                                , requireJS: RequireJS)
                               (implicit exec: ExecutionContext) extends Controller {

  /**  Display the initial homepage */
  def index() = Action {
    Ok(views.html.index_react(webJarAssets, requireJS))
  }

  /**  Display an individual page for a Pokemon
    *  @param name a String representing the name of the Pokemon to display
    */
  def pokemon(name: String) = Action {
    Ok(views.html.card_react(name, webJarAssets, requireJS))
  }
}

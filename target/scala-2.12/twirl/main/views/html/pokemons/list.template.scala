
package views.html.pokemons

import _root_.play.twirl.api.TwirlFeatureImports._
import _root_.play.twirl.api.TwirlHelperImports._
import _root_.play.twirl.api.Html
import _root_.play.twirl.api.JavaScript
import _root_.play.twirl.api.Txt
import _root_.play.twirl.api.Xml
import models._
import controllers._
import play.api.i18n._
import views.html._
import play.api.templates.PlayMagic._
import play.api.mvc._
import play.api.data._

object list extends _root_.play.twirl.api.BaseScalaTemplate[play.twirl.api.HtmlFormat.Appendable,_root_.play.twirl.api.Format[play.twirl.api.HtmlFormat.Appendable]](play.twirl.api.HtmlFormat) with _root_.play.twirl.api.Template1[List[Pokemon],play.twirl.api.HtmlFormat.Appendable] {

  /*
 * This template is called from the `index` template. This template
 * handles the rendering of the page header and body tags. It takes
 * two arguments, a `String` for the title of the page and an `Html`
 * object to insert into the body of the page.
 */
  def apply/*7.2*/(pokemons: List[Pokemon]):play.twirl.api.HtmlFormat.Appendable = {
    _display_ {
      {


Seq[Any](format.raw/*7.27*/("""

"""),_display_(/*9.2*/main("My pokemons")/*9.21*/ {_display_(Seq[Any](format.raw/*9.23*/("""
  """),format.raw/*10.3*/("""<ul class="pokemons">
    """),_display_(/*11.6*/for(pokemon <- pokemons) yield /*11.30*/ {_display_(Seq[Any](format.raw/*11.32*/("""
    """),format.raw/*12.5*/("""<li>"""),_display_(/*12.10*/pokemon/*12.17*/.name),format.raw/*12.22*/(""": """),_display_(/*12.25*/pokemon/*12.32*/.category),format.raw/*12.41*/("""</li>
      """)))}),format.raw/*13.8*/("""
  """),format.raw/*14.3*/("""</ul>
""")))}),format.raw/*15.2*/("""
"""))
      }
    }
  }

  def render(pokemons:List[Pokemon]): play.twirl.api.HtmlFormat.Appendable = apply(pokemons)

  def f:((List[Pokemon]) => play.twirl.api.HtmlFormat.Appendable) = (pokemons) => apply(pokemons)

  def ref: this.type = this

}


              /*
                  -- GENERATED --
                  DATE: Sun Jul 09 12:43:54 CEST 2017
                  SOURCE: /home/claudia/ZengularityProject/github/pokecards/app/views/pokemons/list.scala.html
                  HASH: 48ba3b7d7affb27431a1b20ac983d80296cd9d72
                  MATRIX: 998->260|1118->285|1146->288|1173->307|1212->309|1242->312|1295->339|1335->363|1375->365|1407->370|1439->375|1455->382|1481->387|1511->390|1527->397|1557->406|1600->419|1630->422|1667->429
                  LINES: 26->7|31->7|33->9|33->9|33->9|34->10|35->11|35->11|35->11|36->12|36->12|36->12|36->12|36->12|36->12|36->12|37->13|38->14|39->15
                  -- GENERATED --
              */
          
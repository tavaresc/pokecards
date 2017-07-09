
package views.html.pokemons

import play.twirl.api._
import play.twirl.api.TemplateMagic._


     object list_Scope0 {
import models._
import controllers._
import play.api.i18n._
import views.html._
import play.api.templates.PlayMagic._
import play.api.mvc._
import play.api.data._

class list extends BaseScalaTemplate[play.twirl.api.HtmlFormat.Appendable,Format[play.twirl.api.HtmlFormat.Appendable]](play.twirl.api.HtmlFormat) with play.twirl.api.Template1[List[Pokemon],play.twirl.api.HtmlFormat.Appendable] {

  /*
 * This template is called from the `pokewelcome` template. This template
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


}

/*
 * This template is called from the `pokewelcome` template. This template
 * handles the rendering of the page header and body tags. It takes
 * two arguments, a `String` for the title of the page and an `Html`
 * object to insert into the body of the page.
 */
object list extends list_Scope0.list
              /*
                  -- GENERATED --
                  DATE: Sun Jul 09 16:23:45 CEST 2017
                  SOURCE: /home/claudia/ZengularityProject/play/play-scala-starter-example-2.5.x/app/views/pokemons/list.scala.html
                  HASH: d525c20cf991f232dd77cc1902a6eb3a69124bb0
                  MATRIX: 801->266|921->291|949->294|976->313|1015->315|1045->318|1098->345|1138->369|1178->371|1210->376|1242->381|1258->388|1284->393|1314->396|1330->403|1360->412|1403->425|1433->428|1470->435
                  LINES: 25->7|30->7|32->9|32->9|32->9|33->10|34->11|34->11|34->11|35->12|35->12|35->12|35->12|35->12|35->12|35->12|36->13|37->14|38->15
                  -- GENERATED --
              */
          
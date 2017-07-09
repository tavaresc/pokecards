
package views.html

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

object pokewelcome extends _root_.play.twirl.api.BaseScalaTemplate[play.twirl.api.HtmlFormat.Appendable,_root_.play.twirl.api.Format[play.twirl.api.HtmlFormat.Appendable]](play.twirl.api.HtmlFormat) with _root_.play.twirl.api.Template2[String,String,play.twirl.api.HtmlFormat.Appendable] {

  /**/
  def apply/*1.2*/(message: String, style: String = "scala"):play.twirl.api.HtmlFormat.Appendable = {
    _display_ {
      {


Seq[Any](format.raw/*1.44*/(""" 

"""),_display_(/*3.2*/defining(play.core.PlayVersion.current)/*3.41*/ { version =>_display_(Seq[Any](format.raw/*3.54*/("""

"""),format.raw/*5.1*/("""<section id="top">
  <div class="wrapper">
    <h1><a href="https://playframework.com/documentation/"""),_display_(/*7.59*/version),format.raw/*7.66*/("""/Home">"""),_display_(/*7.74*/message),format.raw/*7.81*/("""</a></h1>
  </div>
</section>

<div id="content" class="wrapper doc">
<article>

  <h1>Welcome to Pokecards</h1>

  <p>
    Congratulations, you’ve just created a new Play application. This page will help you with the next few steps.
  </p>

  """),_display_(/*20.4*/pokemons/*20.12*/.list(Pokemon.pokemons)),format.raw/*20.35*/("""

  """),format.raw/*22.3*/("""<blockquote>
    <p>
      You’re using Play """),_display_(/*24.26*/version),format.raw/*24.33*/("""
    """),format.raw/*25.5*/("""</p>
  </blockquote>
</article>

</div>
""")))}),format.raw/*30.2*/("""
"""))
      }
    }
  }

  def render(message:String,style:String): play.twirl.api.HtmlFormat.Appendable = apply(message,style)

  def f:((String,String) => play.twirl.api.HtmlFormat.Appendable) = (message,style) => apply(message,style)

  def ref: this.type = this

}


              /*
                  -- GENERATED --
                  DATE: Sun Jul 09 12:37:21 CEST 2017
                  SOURCE: /home/claudia/ZengularityProject/github/pokecards/app/views/pokewelcome.scala.html
                  HASH: b38eebed3d76bda928df5992c54a9bd1987e84a1
                  MATRIX: 742->1|879->43|908->47|955->86|1005->99|1033->101|1160->202|1187->209|1221->217|1248->224|1519->469|1536->477|1580->500|1611->504|1684->550|1712->557|1744->562|1815->603
                  LINES: 21->1|26->1|28->3|28->3|28->3|30->5|32->7|32->7|32->7|32->7|45->20|45->20|45->20|47->22|49->24|49->24|50->25|55->30
                  -- GENERATED --
              */
          
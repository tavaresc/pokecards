
package views.html

import play.twirl.api._
import play.twirl.api.TemplateMagic._


     object pokewelcome_Scope0 {
import models._
import controllers._
import play.api.i18n._
import views.html._
import play.api.templates.PlayMagic._
import play.api.mvc._
import play.api.data._

class pokewelcome extends BaseScalaTemplate[play.twirl.api.HtmlFormat.Appendable,Format[play.twirl.api.HtmlFormat.Appendable]](play.twirl.api.HtmlFormat) with play.twirl.api.Template2[String,String,play.twirl.api.HtmlFormat.Appendable] {

  /**/
  def apply/*1.2*/(message: String, style: String = "scala"):play.twirl.api.HtmlFormat.Appendable = {
    _display_ {
      {


Seq[Any](format.raw/*1.44*/(""" 

"""),_display_(/*3.2*/defining(play.core.PlayVersion.current)/*3.41*/ { version =>_display_(Seq[Any](format.raw/*3.54*/("""

"""),format.raw/*5.1*/("""<link rel="stylesheet" media="screen" href="/@documentation/resources/style/main.css">

<section id="top">
  <div class="wrapper">
    <h1><a href="https://playframework.com/documentation/"""),_display_(/*9.59*/version),format.raw/*9.66*/("""/Home">"""),_display_(/*9.74*/message),format.raw/*9.81*/("""</a></h1>
  </div>
</section>

<div id="content" class="wrapper doc">
<article>

  <h1>Welcome to Pokecards</h1>

  <p>
    Congratulations, you’ve just created a new Play application. This page will help you with the next few steps.
  </p>

  """),_display_(/*22.4*/pokemons/*22.12*/.list(Pokemon.pokemons)),format.raw/*22.35*/("""

  """),format.raw/*24.3*/("""<blockquote>
    <p>
      You’re using Play """),_display_(/*26.26*/version),format.raw/*26.33*/("""
    """),format.raw/*27.5*/("""</p>
  </blockquote>
</article>

</div>
""")))}),format.raw/*32.2*/("""
"""))
      }
    }
  }

  def render(message:String,style:String): play.twirl.api.HtmlFormat.Appendable = apply(message,style)

  def f:((String,String) => play.twirl.api.HtmlFormat.Appendable) = (message,style) => apply(message,style)

  def ref: this.type = this

}


}

/**/
object pokewelcome extends pokewelcome_Scope0.pokewelcome
              /*
                  -- GENERATED --
                  DATE: Sun Jul 09 16:27:55 CEST 2017
                  SOURCE: /home/claudia/ZengularityProject/play/play-scala-starter-example-2.5.x/app/views/pokewelcome.scala.html
                  HASH: 72231f2e611109b6139ccac8ca21c83135e4aa49
                  MATRIX: 546->1|683->43|712->47|759->86|809->99|837->101|1052->291|1079->298|1113->306|1140->313|1411->558|1428->566|1472->589|1503->593|1576->639|1604->646|1636->651|1707->692
                  LINES: 20->1|25->1|27->3|27->3|27->3|29->5|33->9|33->9|33->9|33->9|46->22|46->22|46->22|48->24|50->26|50->26|51->27|56->32
                  -- GENERATED --
              */
          
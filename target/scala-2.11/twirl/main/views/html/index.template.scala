
package views.html

import play.twirl.api._
import play.twirl.api.TemplateMagic._


     object index_Scope0 {
import models._
import controllers._
import play.api.i18n._
import views.html._
import play.api.templates.PlayMagic._
import play.api.mvc._
import play.api.data._

class index extends BaseScalaTemplate[play.twirl.api.HtmlFormat.Appendable,Format[play.twirl.api.HtmlFormat.Appendable]](play.twirl.api.HtmlFormat) with play.twirl.api.Template1[String,play.twirl.api.HtmlFormat.Appendable] {

  /*
 * This template takes a single argument, a String containing a
 * message to display.
 */
  def apply/*5.2*/(message: String):play.twirl.api.HtmlFormat.Appendable = {
    _display_ {
      {


Seq[Any](format.raw/*5.19*/("""

"""),format.raw/*11.4*/("""
"""),_display_(/*12.2*/main("Welcome")/*12.17*/ {_display_(Seq[Any](format.raw/*12.19*/("""

    """),format.raw/*17.8*/("""
    """),_display_(/*18.6*/pokewelcome(message, style = "scala")),format.raw/*18.43*/("""

""")))}),format.raw/*20.2*/("""
"""))
      }
    }
  }

  def render(message:String): play.twirl.api.HtmlFormat.Appendable = apply(message)

  def f:((String) => play.twirl.api.HtmlFormat.Appendable) = (message) => apply(message)

  def ref: this.type = this

}


}

/*
 * This template takes a single argument, a String containing a
 * message to display.
 */
object index extends index_Scope0.index
              /*
                  -- GENERATED --
                  DATE: Sun Jul 09 16:23:45 CEST 2017
                  SOURCE: /home/claudia/ZengularityProject/play/play-scala-starter-example-2.5.x/app/views/index.scala.html
                  HASH: ef085fc0b7c76f996b719b5d6983a1240293508d
                  MATRIX: 616->95|728->112|757->308|785->310|809->325|849->327|882->456|914->462|972->499|1005->502
                  LINES: 23->5|28->5|30->11|31->12|31->12|31->12|33->17|34->18|34->18|36->20
                  -- GENERATED --
              */
          
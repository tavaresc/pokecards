
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

object index extends _root_.play.twirl.api.BaseScalaTemplate[play.twirl.api.HtmlFormat.Appendable,_root_.play.twirl.api.Format[play.twirl.api.HtmlFormat.Appendable]](play.twirl.api.HtmlFormat) with _root_.play.twirl.api.Template1[String,play.twirl.api.HtmlFormat.Appendable] {

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


              /*
                  -- GENERATED --
                  DATE: Sun Jul 09 12:31:41 CEST 2017
                  SOURCE: /home/claudia/ZengularityProject/github/pokecards/app/views/index.scala.html
                  HASH: 9c0c57212d23964e9b12b582d141303e94d97bc0
                  MATRIX: 818->95|930->112|959->308|987->310|1011->325|1051->327|1084->456|1116->462|1174->499|1207->502
                  LINES: 24->5|29->5|31->11|32->12|32->12|32->12|34->17|35->18|35->18|37->20
                  -- GENERATED --
              */
          
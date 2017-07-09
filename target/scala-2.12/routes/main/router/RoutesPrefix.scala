
// @GENERATOR:play-routes-compiler
// @SOURCE:/home/claudia/ZengularityProject/github/pokecards/conf/routes
// @DATE:Sun Jul 09 12:43:06 CEST 2017


package router {
  object RoutesPrefix {
    private var _prefix: String = "/"
    def setPrefix(p: String): Unit = {
      _prefix = p
    }
    def prefix: String = _prefix
    val byNamePrefix: Function0[String] = { () => prefix }
  }
}

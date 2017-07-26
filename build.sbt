name := """play-scala"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala, SbtWeb)

ReactJsKeys.harmony := true
ReactJsKeys.es6module := true

scalaVersion := "2.11.11"

libraryDependencies += "com.typesafe.play" %% "play-slick" % "2.0.2"
libraryDependencies += "com.typesafe.play" %% "play-slick-evolutions" % "2.0.2"
libraryDependencies += "com.h2database" % "h2" % "1.4.194"
libraryDependencies += ws
libraryDependencies += cache
libraryDependencies += specs2 % Test
libraryDependencies += filters

// Add webjars as dependencies for webjar-play and bootstrap and react
// resolvers += Resolver.bintrayRepo("webjars","maven")
libraryDependencies ++= Seq(
  "com.danielasfregola" %% "twitter4s" % "5.1",
  "org.webjars" %% "webjars-play" % "2.5.0-4",
  "org.webjars" % "requirejs" % "2.2.0",
  "org.webjars.bower" % "react" % "15.6.1",
  "org.webjars.npm" % "node-fetch"	% "1.0.1",
  "org.webjars.npm" % "react-autosuggest" % "8.0.0",
  "org.webjars" % "bootstrap" % "3.3.7",
  "org.webjars.bower" % "font-awesome" % "4.7.0",
  "org.webjars.npm" % "react-router-dom" % "4.1.2",
  "org.webjars.bower" %	"axios"	% "0.16.1"
)

// Include the sbt-react plugin to enable server-side precompilation
// See http://ticofab.io/react-js-tutorial-with-play_scala_webjars/
//root = (project in file(".")).enablePlugins(SbtWeb)
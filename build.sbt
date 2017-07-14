name := """play-scala"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala, SbtWeb)

scalaVersion := "2.11.11"

libraryDependencies += jdbc
libraryDependencies += cache
libraryDependencies += ws
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "2.0.0" % Test

// Add webjars as dependencies for webjar-play and bootstrap and react
resolvers += Resolver.bintrayRepo("webjars","maven")
libraryDependencies ++= Seq(
  "org.webjars" %% "webjars-play" % "2.5.0",
  "org.webjars" % "bootstrap" % "3.3.7", //not sure this works
  "org.webjars" % "react" % "0.14.8",
  "org.webjars.npm" % "types__react" % "15.0.34"
)

// Include the sbt-react plugin to enable server-side precompilation
// See http://ticofab.io/react-js-tutorial-with-play_scala_webjars/
//root = (project in file(".")).enablePlugins(SbtWeb)

/**
  * This package is responsible for defining data models for the local Slick database.
  * Methods for manipulating these models are defined in package `dal`.
  */
package models

import play.api.libs.json._

/** Minimalistic representation of a tweet
  *
  * @param name the user name
  * @param screen_name the twitter handle
  * @param id the user id
  * @param profile_img the user profile picture
  */
case class TwUser(name: String
                  , screen_name : String
                  , id: Long
                  , profile_img : String
                 )

object TwUser {
  /** JSON reader / writer for the TwUser case class */
  implicit val userFormat = Json.format[TwUser]
}

/** Minimalistic representation of a media
  *
  * @param `type` the type of media (photo, video)
  * @param url the url of a media
  */
case class Media(`type`: String, url:String)
object Media {
  /** JSON reader / writer for the Media case class */
  implicit val mediaFormat = Json.format[Media]
}

/** Minimalistic representation of a pokemon tweet (#pokemon_name)
  *
  * @param id the unique tweet id
  * @param date the tweet date
  * @param urls the urls quoted on a tweet
  * @param hashtags the query hastags
  * @param text the content of a tweet
  * @param media the media embedded on a tweet
  * @param user the author of the tweet
  */
case class PokemonTweet(id: Long
                        , date: String // Probably bad but not too worrisome,
                        , urls: Seq[String]
                        , hashtags: Seq[String]
                        , text: String
                        , media: Seq[Media] // type * url
                        , user: Option[TwUser]
                       )
object PokemonTweet {
  /** JSON reader / writer for the PokemonTweet case class */
  implicit val pokemonTweetFormat = Json.format[PokemonTweet]
}
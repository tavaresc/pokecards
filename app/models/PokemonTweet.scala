package models

import play.api.libs.json._

case class TwUser(name: String
                  , screen_name : String
                  , id: Long
                  , profile_img : String
                 )

object TwUser {
  implicit val userFormat = Json.format[TwUser]
}

case class Media(`type`: String, url:String)
object Media {
  implicit val mediaFormat = Json.format[Media]
}

case class PokemonTweet(date: String // Probably bad but not too worrisome,
                        , urls: Seq[String]
                        , hashtags: Seq[String]
                        , text: String
                        , media: Seq[Media] // type * url
                        , user: Option[TwUser]
                       )
// Json de/serializers

object PokemonTweet {
  implicit val pokemonTweetFormat = Json.format[PokemonTweet]
}
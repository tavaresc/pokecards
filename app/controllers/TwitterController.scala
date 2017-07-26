package controllers

import javax.inject._

import com.danielasfregola.twitter4s.TwitterRestClient
import play.api._
import play.api.mvc._
import play.api.libs.json._

import scala.concurrent.{ExecutionContext, Future}
import play.api.libs.oauth._
import play.api.libs.ws.WSClient

// Scala client for twitter API - See https://danielasfregola.com/tag/scala/
import com.danielasfregola.twitter4s.entities.{AccessToken, ConsumerToken, Entities, Tweet}
import com.danielasfregola.twitter4s.entities.enums.ResultType
import models.{PokemonTweet, TwUser}  // for pokemon tweets model

/** This class handles everything related to pokemon tweet queries.
  * @param config configuration data
  * @param ws we need the `WSClient` to call HTTP services
  * @param exec We need an `ExecutionContext` to execute our
  *             asynchronous code.
  */
@Singleton
class TwitterController @Inject()(config: Configuration,ws:WSClient)(implicit exec: ExecutionContext) extends Controller {

  /** Access to a Twitter account (authentication)
    *
    * @return the access token (?)
    */
  private def obtainCredentialsFromHOCON: Option[(ConsumerToken, AccessToken)] = for {
    consumerKey <- config.getString("twitter.consumer_key")
    consumerSecret <- config.getString("twitter.consumer_secret")
    accessToken <- config.getString("twitter.access_token")
    accessTokenSecret <- config.getString("twitter.access_token_secret")
  } yield (ConsumerToken(consumerKey, consumerSecret),
    AccessToken(accessToken, accessTokenSecret))

  /* define a Twitter client to authentication */
  private val client: TwitterRestClient =
    obtainCredentialsFromHOCON match {
      case Some((consumerToken, accessToken)) => TwitterRestClient(consumerToken, accessToken)
      case None => TwitterRestClient()
    }

  /** Add the name of the author (the user who tweeted) in a pokemon tweet
    *
    * @param tw the original tweet
    * @param pktw the pokemon tweet
    * @return the updated pokemon tweet
    */
  private def insertUser(tw:Tweet, pktw: PokemonTweet): PokemonTweet = {
    val user: Option[TwUser] = tw.user match {
      case None => None
      case Some(u) => Some(TwUser(u.name, u.screen_name, u.id, u.profile_image_url.mini))
    }
    pktw.copy(user = user)
  }

  /** Insert new entities to a tweet data (such as a src image isntead of a path to a tweet showing it
    *
    * @param pktw a pokemon tweet
    * @param entities entities to be added to the tweet
    * @return the updated pokemon tweet
    */
  private def insertEntities(pktw: PokemonTweet, entities: Option[Entities]) : PokemonTweet = {
    entities match {
      case Some(e) =>
        Logger.debug("Tweetings:" + e.toString)
        pktw.copy(urls = e.urls.map{ u => u.expanded_url}
          , hashtags = e.hashtags.map { h => h.text }
          /* Among the fields ﬀfrom
           * https://github.com/DanielaSfregola/twitter4s/blob/master/src/main/scala/com/danielasfregola/twitter4s/entities/Media.scala
           * media_url contains the image source
           */
          , media = e.media.map { m => models.Media(m.`type`, m.media_url) })
      case None => pktw
    }
  }

  /** Extract some data from a tweet query result and creates a pokemon tweet is the result
    *
    * @param tweets a list of tweets
    * @return a pokemon tweets list (sublist of initial tweets)
    */
  private def extract(tweets:List[Tweet]): List[PokemonTweet] = {
    tweets.sortBy(_.created_at).map {
      t =>
        val v = Seq[String]()
        Logger.debug("Full tweet: " + t.toString)
        val pktw = PokemonTweet(t.id, t.created_at.toString, v, v, t.text, Seq[models.Media](), None)
        insertUser(t, insertEntities(pktw, t.entities))
    }

  }

  /** Get some data from tweets of requested pokemon
    *
    * @param name the pokemon name
    * @return a (Future) list of tweets sorted lexicographically
    */
  def getTweets(name:String): Future[List[PokemonTweet]] = {
    val query = "#" + name // The query is the hashtag of the pokemon name
    client.searchTweet(query, count = 5, result_type = ResultType.Recent).flatMap { ratedData =>
      val result = ratedData.data
      val tweets = result.statuses
      Future(tweets.sortBy(_.created_at))
    }.map( tweets => extract(tweets) )
  }

  /** Request for tweets and converts it from Future to JSON
    * @param name the pokemon name for query search
    * @return the tweets
    */
  def get(name:String) = Action.async {
    getTweets(name).map { tweets => Ok(Json.toJson(tweets)) }
  }
}

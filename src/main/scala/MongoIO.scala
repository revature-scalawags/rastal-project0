import org.mongodb.scala._
import org.mongodb.scala.bson.codecs.Macros._
import org.mongodb.scala.bson.collection.immutable.Document
import org.mongodb.scala.model.Filters._
import org.mongodb.scala.MongoClient.DEFAULT_CODEC_REGISTRY
import org.bson.codecs.configuration.CodecRegistries.{fromRegistries, fromProviders}
import scala.collection.immutable.ListMap
import scala.io.Source
import tour.Helpers._

object MongoIO {
  val providers = fromProviders(classOf[Counts], classOf[Count], classOf[Tweets])
  val registry = fromRegistries(providers, DEFAULT_CODEC_REGISTRY)
  val client = MongoClient()
  val database = client.getDatabase("proj0").withCodecRegistry(registry)

  def insertTweets(tweets: Tweets): Unit = {
    val collection: MongoCollection[Tweets] = database.getCollection("tweets")
    // Only insert the Tweets document if one with this hashtag doesn't already exist
    if (collection.find(equal("hashtag", tweets.hashtag)).results().isEmpty)
      collection.insertOne(tweets).results()
    else 
      println(s"Mongo Document with ${tweets.hashtag} already exists.")
  }

  def getTweets(hashtag: String = "gamedev"): Tweets = {
    val collection: MongoCollection[Tweets] = database.getCollection("tweets")
    collection.find(equal("hashtag", "gamedev")).results().head
  }

  def insertCounts(counts: Counts): Unit = {
    val collection: MongoCollection[Counts] = database.getCollection("counts")
    collection.insertOne(counts).results()
  }
}

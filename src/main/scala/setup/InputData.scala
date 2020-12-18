import org.mongodb.scala._
import org.mongodb.scala.bson.codecs.Macros._
import org.mongodb.scala.bson.collection.immutable.Document
import org.mongodb.scala.model.Filters._
import org.mongodb.scala.MongoClient.DEFAULT_CODEC_REGISTRY
import org.bson.codecs.configuration.CodecRegistries.{fromRegistries, fromProviders}
import scala.io.Source
import tour.Helpers._

object InputData {
  val DefaultPath = "data/data.txt"
  
  /** Returns the lines of text from the source file as a List[String],
    * for processing.
    */
  def read(path: String = DefaultPath): List[String] =
    Source.fromFile(path).getLines().toList

  /** Returns a Tweets object from a List of Strings
    * 
    * The Tweets' hashtag is the first item from the List,
    * and the tweets are the rest of the items.
    */
  def convert(data: List[String]): Tweets = 
    Tweets(data(0), data.takeRight(data.length - 1))

  /** Inserts a Tweets Document into a specified MongoDB Collection. */
  def write(tweets: Tweets, db: String = "proj0", coll: String = "input"): Unit = {
    val registry = fromRegistries(fromProviders(classOf[Tweets]), DEFAULT_CODEC_REGISTRY)
    val client = MongoClient()
    val database = client.getDatabase(db).withCodecRegistry(registry)
    
    // Make sure the collection is specified as type Tweets, otherwise
    // it'll default to Document, and the insertion will fail.
    val collection: MongoCollection[Tweets] = database.getCollection(coll)
    
    // Only insert the Tweets document if one with this hashtag doesn't already exist
    if (collection.find(equal("hashtag", tweets.hashtag)).results().isEmpty) {
      collection.insertOne(tweets).results()
    }
  }
}

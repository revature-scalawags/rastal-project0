import org.mongodb.scala._
import org.mongodb.scala.bson.codecs.Macros._
import org.mongodb.scala.bson.collection.immutable.Document
import org.mongodb.scala.model.Filters._
import org.mongodb.scala.MongoClient.DEFAULT_CODEC_REGISTRY
import org.bson.codecs.configuration.CodecRegistries.{fromRegistries, fromProviders}
import scala.io.Source
import tour.Helpers._

/** This object is only used in setting up the MongoDB collection that the main
  * program will read data from, and shouldn't be called accessed during actual
  * calculations.  
  */
object MongoTweetsSetup {
  val DefaultPath = "data/data.txt"
  
  /** Returns the lines of text from the source file as a List[String],
    * for processing.
    */
  def readFromSource(path: String = DefaultPath): List[String] =
    Source.fromFile(path).getLines().toList

  /** Returns a Tweets object from a List of Strings
    * 
    * The Tweets' hashtag is the first item from the List,
    * and the tweets are the rest of the items.
    */
  def convertToMongo(data: List[String]): Tweets = 
    Tweets(data(0), data.takeRight(data.length - 1))
}

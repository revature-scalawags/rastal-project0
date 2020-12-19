import org.mongodb.scala._
import org.mongodb.scala.bson.codecs.Macros._
import org.mongodb.scala.bson.collection.immutable.Document
import org.mongodb.scala.model.Filters._
import org.mongodb.scala.MongoClient.DEFAULT_CODEC_REGISTRY
import org.bson.codecs.configuration.CodecRegistries.{fromRegistries, fromProviders}
import scala.collection.immutable.ListMap
import scala.io.Source
import tour.Helpers._

object OutputData {
  def convert(maxResults: Int = 100, counts: ListMap[String, Int]): Results = {
    // Converts each Word/Count pair into a Count object and adds 
    // it to a Results object for inserting into a MongoDB collection
    Results((for ((k,v) <- counts) yield Count(k,v)).toList)
  }

  def write(results: Results, db: String = "proj0", coll: String = "output"): Unit = {
    val registry = fromRegistries(fromProviders(classOf[Results], classOf[Count]), DEFAULT_CODEC_REGISTRY)
    val client = MongoClient()
    val database = client.getDatabase(db).withCodecRegistry(registry)

    // Make sure the collection is specified as type Results, otherwise
    // it'll default to Document, and the insertion will fail.
    val collection: MongoCollection[Results] = database.getCollection(coll)
    
    collection.insertOne(results).results()
  }
}

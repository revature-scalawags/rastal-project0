import org.mongodb.scala._

import scala.collection.mutable
import scala.concurrent.{Await, Future}
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success}

import tour.Helpers._
import java.util.Calendar

object MongoWriter {
  val dbName = "proj0"
  val collName = "tweets"

  val client = MongoClient()
  val database = client.getDatabase(dbName)
  val collection = database.getCollection(collName)


  def writeCounts(counts: mutable.ListMap[String, Int]): Unit = {}
}
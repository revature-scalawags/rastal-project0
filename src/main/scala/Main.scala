import com.typesafe.scalalogging.LazyLogging

import scala.collection.immutable.ListMap
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.util.control.Breaks
import scala.util.{Failure, Success}

import HashtagAnalysis._

/** Reads text data from a file and counts the occurrences of each word.
  * 
  * If no integer argument between 1 and 100 is passed in as an argument,
  * defaults to printing top 100 results.
  */
object Main extends App with LazyLogging {
  var maxResults = 100  // Default max results to return
  
  // Process the CLI arguments passed in with sbt "run [argument]"
  val warning = "\nWARNING: "
  val error = "For maxResults argument, please enter a valid integer " +
    "between 1 and 100 (inclusive).\nDefaulting to 100.\n"
  if (args.length == 1) {
    val keyword = args.head
    // Pulled from Alvin Alexander's article on Option, Some, and None.
    // https://alvinalexander.com/scala/using-scala-option-some-none-idiom-function-java-null/
    toInt(keyword) match {
      case Some(i) if (i > 0 && i < 100) => maxResults = i
      case _ => println(s"$warning$error") 
    }
    if (maxResults > 100 || maxResults < 1) {
      println(s"$warning$error")
    }
  } else if (args.length > 1) {
    println(s"${warning}Main accepts a maximum of one argument with 'sbt run'.\n$error")
  }
  
  // Only run to set up the raw Tweets data in MongoDB
  //MongoIO.insertTweets(MongoTweetsSetup.convertToMongo(MongoTweetsSetup.readFromSource()))
  val logMessages = List("Retrieving hashtag's tweets from MongoDB...\n",
                         "Tallying word occurrences in tweets...\n",
                         "Inserting results into MongoDB...\n",
                         "MongoDB successfully updated!\n")
  printAndLog(logMessages(0))
  val tweetsFuture = Future { MongoIO.getTweets() }
  tweetsFuture.onComplete {
    case Success(tweets) =>  
      printAndLog(logMessages(1))
      val counts = tweets.tweets.toCleanedWords.tally
      printAndLog(logMessages(2))
      //MongoIO.insertCounts(counts.sort.take(maxResults).toCounts)
      printAndLog(logMessages(3))
      println("Top five results from word count:")
      displayResults(counts.sort.take(5))
    case Failure(exception) => exception.printStackTrace
  }

  // Necessary to make sure the JVM stays alive long enough for the 
  // Future to actually return and print
  Thread.sleep(5000)

  /** Prints the most occurring words from the text, along with their counts. */
  def displayResults(results: ListMap[String, Int]): Unit = {
    var i = 0
    val loop = new Breaks
    loop.breakable {
      for ((word,count) <- results) {
        println(s"   $word: $count")
        i += 1
        if (i > maxResults) {
          loop.break()
        }
      }
    }
  }

  /** Both prints out and logs whatever you pass into it.*/
  def printAndLog(message: String): Unit = {
    println(message)
    logger.info(if (message.last == '\n') message.dropRight(1) else message)
  }

  /** Returns a String as Some(Int) if possible, None if not.
    * 
    * Pulled from Alvin Alexander's article on casting Strings to Ints in Scala.
    * (https://alvinalexander.com/scala/how-cast-string-to-int-in-scala-string-int-conversion/)
    */
  def toInt(s: String): Option[Int] = {
    try {
      Some(s.toInt)
    } catch {
      case e: Exception => None
    }
  }
}
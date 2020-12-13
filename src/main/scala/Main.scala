import scala.collection.immutable.ListMap
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.control.Breaks
import scala.util.{Failure, Success}

/**
  * Reads text data from a file and counts the occurrences of each word.
  * 
  * If no integer argument between 1 and 100 is passed in as an argument,
  * defaults to printing top 100 results.
  */
object Main extends App {
  var maxResults = 100  // Default max results to return
  
  // Process the CLI arguments passed in with sbt "run [argument]"
  val error = "For maxResults argument, please enter a valid integer " +
    "between 1 and 100 (inclusive).\nDefaulting to 100."
  if (args.length == 1) {
    val keyword = args(0)
    // Pulled from Alvin Alexander's article on Option, Some, and None.
    // https://alvinalexander.com/scala/using-scala-option-some-none-idiom-function-java-null/
    toInt(keyword) match {
      case Some(i) if (i > 0 && i < 100) => maxResults = i
      case _ => println(error)
    }
    if (maxResults > 100 || maxResults < 1) {
      println(error)
    }
  } else if (args.length > 1) {
    println(s"Main accepts a maximum of one argument with 'sbt run'.\n$error")
  }
  
  val dataFuture = DataReader.readData()
  dataFuture.onComplete {
    case Success(x) => displayResults(TextProcessor.processData(x))
    case Failure(e) => e.printStackTrace
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
        println(s"$word: $count")
        i += 1
        if (i > maxResults) {
          loop.break()
        }
      }
    }
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
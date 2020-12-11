import scala.util.control.Breaks

/**
  * Reads text data from a file and counts the occurrences of each word.
  * 
  * If no integer argument between 1 and 100 is passed in as an argument,
  * defaults to printing top 100 results.
  */
object Main extends App {
  var keyword = ""
  var maxResults = 100
  if (args.length > 0) {
    keyword = args(0)
    val error = "For maxResults argument, please enter a valid integer " +
        "between 1 and 100 (inclusive).\nDefaulting to 100."
    // Pulled from Alvin Alexander's article on Option, Some, and None.
    // https://alvinalexander.com/scala/using-scala-option-some-none-idiom-function-java-null/
    toInt(keyword) match {
      case Some(i) if (i > 0 && i < 100) => maxResults = i
      case _ => println(error)
    }
    if (maxResults > 100 || maxResults < 1) {
      println(error)
    }
  }
  val data = DataReader.readData()
  val sortedCounts = TextProcessor.processData(data)
  var i = 0
  val loop = new Breaks
  loop.breakable {
    for ((word,count) <- sortedCounts) {
      println(s"$word: $count")
      i += 1
      if (i > maxResults) {
        loop.break()
      }
    }
  }

  /** Returns a String as Some(Int) if possible, None if not.
    * 
    * Pulled from Alvin Alexander's article on casting Strings to Ints in Scala.
    * (https://alvinalexander.com/scala/how-cast-string-to-int-in-scala-string-int-conversion/)
    *
    * @param s
    * @return Some(s.toInt) if valid Int, None if not
    */
  def toInt(s: String): Option[Int] = {
    try {
      Some(s.toInt)
    } catch {
      case e: Exception => None
    }
  }
}
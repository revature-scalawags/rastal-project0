import scala.util.control.Breaks

/**
  * Reads text data from a file and counts the occurrences of each word.
  */
object Program extends App {
  var keyword = ""
  if (args.length > 0) {
    keyword = args(0)
    println(keyword)
  }
  val data = DataReader.readData()
  val sortedCounts = TextProcessor.processData(data)
  var i = 0
  val loop = new Breaks
  loop.breakable {
    for ((word,count) <- sortedCounts) {
      println(s"$word: $count")
      i += 1
      if (i > 25) {
        loop.break()
      }
    }
  }
}
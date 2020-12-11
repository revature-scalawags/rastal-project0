import scala.collection.immutable.ListMap
import scala.collection.mutable
import scala.io.BufferedSource

object TextProcessor extends App {
  val data = TsvReader.readTsvSource()
  val counts = mutable.Map[String, Int]()
  for (chunk <- data) {
    val words = chunk.split(" ")
    for (word <- words) {
      if (isWord(word)) {
        val cleanedWord = cleanWord(word)
        if (counts.contains(cleanedWord))
          counts(cleanedWord) += 1
        else
          counts(cleanedWord) = 1
      }
    }
  }
  val sortedCounts = ListMap(counts.toSeq.sortWith(_._2 > _._2):_*)
  for ((word,count) <- sortedCounts) println(s"$word: $count")

  def isWord(text: String): Boolean = {
    if (text.length == 0 ||
        text(0) == '#' ||
        (text.length > 4 && text.contains("http")))
      false
    else
      true
  }
  
  def cleanWord(text: String): String = {
    var cleanedText = ""
    if (text.last == '!' ||
        text.last == '.' ||
        text.last == '?')
      cleanedText = text.substring(0, text.length-1)
    else
      cleanedText = text

    cleanedText
  }
}

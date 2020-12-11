import scala.collection.immutable.ListMap
import scala.collection.mutable
import scala.io.BufferedSource

object TextProcessor {

  def processData(data: mutable.ArrayBuffer[String]): ListMap[String, Int] = {
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

    ListMap(counts.toSeq.sortWith(_._2 > _._2):_*)
  }

  def isWord(text: String): Boolean = {
    if (text.length == 0 ||
        text(0) == '#' ||
        (text.length > 4 && text.contains("http")))
      false
    else
      true
  }
  
  def cleanWord(text: String): String = {
    if (text.last == '!' ||
        text.last == '.' ||
        text.last == '?')
      text.substring(0, text.length-1)
    else
      text
  }
}

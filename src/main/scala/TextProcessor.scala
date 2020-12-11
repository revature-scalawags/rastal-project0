import scala.collection.immutable.ListMap
import scala.collection.mutable
import scala.io.BufferedSource

object TextProcessor {
  
  /** Returns a map of words and their counts, sorted in descending order
    * by count.
    * 
    * Verifies that each "word" in the data is an actual word, then cleans
    * it of extraneous characters before counting the occurrence of each word.
    *
    * @param data
    * @return ListMap[String, Int]
    */
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

  /** Returns true if the string is a word.
    * 
    * Checks that the string contains characters, that it begins with 
    * an alphanumeric character, and that it isn't a url (in other words,
    * that it doesn't start with "http").
    *
    * @param text
    * @return true or false
    */
  def isWord(text: String): Boolean = {
    val alphasNumbers = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" +
      "abcdefghijklmnopqrstuvwxyz" +
      "1234567890"
    if (text.length == 0 ||
        !alphasNumbers.contains(text(0)) ||
        (text.length > 4 && text.contains("http")))
      false
    else
      true
  }

  /** Returns word cleaned of extraneous characters.
    * 
    * Removes punctuation, hastags, quotes, and @ symbols from the
    * beginning or end of the word.
    *
    * @param text
    * @return cleanedWord
    */
  def cleanWord(text: String): String = {
    var cleanedWord = text
    if (text(0) == '"' ||
        text(0) == '@' ||
        text(0) == '#') {
      cleanedWord = text.substring(1)
    }
    if (text.last == '!' ||
        text.last == '.' ||
        text.last == '?' ||
        text.last == '"') {
      cleanedWord = text.substring(0, text.length-1)
    }

    cleanedWord
  }
}

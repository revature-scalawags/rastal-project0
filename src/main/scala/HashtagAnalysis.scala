import scala.collection.immutable.ListMap
import scala.collection.mutable

object HashtagAnalysis {
  /** Helper methods for checking to see if a string is a valid word and to 
    * clean that word of extra characters (like '@', '#', and punctuation). 
    */
  implicit class Word(word: String) {
    /** Returns true if the string is a word.
      * 
      * Checks that the string is not empty, that it begins with 
      * an alphanumeric character, and that it isn't a url (in other words,
      * that it doesn't start with "http").
      */
    def isWord: Boolean = {
      val alphasNumbers = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" +
        "abcdefghijklmnopqrstuvwxyz" +
        "1234567890"
      if (word.isEmpty ||
          !alphasNumbers.contains(word(0)) ||
          (word.length > 4 && word.contains("http")))
        false
      else
        true
    }

    /** Returns word cleaned of leading symbols or ending punctuation.*/
    def clean: String = {
      var cleaned = word
      if (word(0) == '"' ||
          word(0) == '@' ||
          word(0) == '#') {
        cleaned = cleaned.drop(1)
      }
      if (word.last == ',' ||
          word.last == '!' ||
          word.last == '.' ||
          word.last == '?' ||
          word.last == '"') {
        cleaned = cleaned.dropRight(1)
      }

      cleaned
    }
  }

  /** Helper methods for converting a List of tweets (as strings) into a List
    * of single words and counting occurrences of each word in a List of Strings.
    */
  implicit class StringList(strings: List[String]) {
    /** Converts a List of multi-word strings to a List of single word strings.*/
    def toCleanedWords: List[String] = {
      strings.flatMap(_.split(" ")).filter(_.length > 1).map(_.clean).filter(_.isWord)
    }

    /** Returns a ListMap of words and their counts from a List of single word
      * Strings. The resulting ListMap can then be sorted and groomed, with the 
      * `sort` and `groom(n)` methods, respectively.
      */
    def tally: ListMap[String, Int] = {
      var counts = mutable.Map[String, Int]()
      for (word <- strings) {
        if (counts.contains(word))
          counts(word) += 1
        else
          counts(word) = 1
      }

      ListMap(counts.toSeq:_*)
    }
  }

  /** Helper methods for sorting a ListMap of word counts in descending order 
    * by count, and converting the ListMap to a Counts object for inserting 
    * into MongoDB.
    */
  implicit class WordCounts(counts: ListMap[String, Int]) {
    /** Returns a ListMap of words and counts, sorted in descending order by count.*/
    def sort: ListMap[String, Int] = {
      ListMap(counts.toSeq.sortWith(_._2 > _._2):_*)
    }
    
    /** Returns a Counts object from a ListMap containing words and their counts.
      * 
      * Used to convert raw counts into a form the Mongo Scala driver can interpret.
      */
    def toCounts: Counts = {
      Counts(((for ((k,v) <- counts) yield Count(k,v)).toList))
    }
  }
}

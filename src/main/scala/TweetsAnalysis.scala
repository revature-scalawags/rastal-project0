import scala.collection.mutable

object TweetsAnalysis {

  /** Returns a map of words and their counts, sorted in descending order
    * by count.
    * 
    * Verifies that each "word" in the data is an actual word, then cleans
    * it of extraneous characters before counting the occurrence of each word.
    *
    * @param tweets A List of strings, each item a separate tweet. Usually pulled
    *   from a Tweets object.
    * @return A Map of words with their counts, sorted in descending order
    *   by count
    */
  def cleanAndCountWords(tweets: List[String]): Map[String, Int] = {
    val words = tweets.flatMap(_.split(" ")).filter(_.isWord).map(_.clean)
    var counts = mutable.Map[String, Int]()
    for (word <- words) {
      if (counts.contains(word))
        counts(word) += 1
      else
        counts(word) = 1
    }

    counts.toMap
  }

  /** Helper methods for trimming a Map of word counts down to a certain size,
    * sorting the word counts in descending order by count, and converting the
    * map to a Counts object for inserting into MongoDB.
    */
  implicit class WordCounts(counts: Map[String, Int]) {
    /** Returns a map comtaining n number of words with their counts.
      * 
      * Only useful to call after the sort method is called.
      */
    def groom(n: Int = 100): Map[String, Int] = {
      counts.take(n)
    }

    /** Returns a map of words and counts, sorted in descending order by count.*/
    def sort: Map[String, Int] = {
      Map(counts.toSeq.sortWith(_._2 > _._2):_*)
    }
    
    /** Returns a Counts object from a Map containing words and their counts.
      * 
      * Used to convert raw counts into a form the Mongo Scala driver can interpret.
      */
    def toCounts: Counts = {
      Counts((for ((k,v) <- counts) yield Count(k,v)).toList)
    }
  }
  
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
      if (word.last == '!' ||
          word.last == '.' ||
          word.last == '?' ||
          word.last == '"') {
        cleaned = cleaned.dropRight(1)
      }

      cleaned
    }
  }
}

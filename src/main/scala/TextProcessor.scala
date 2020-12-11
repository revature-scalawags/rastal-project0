import scala.collection.mutable.Map
import scala.io.BufferedSource
import scala.collection.mutable.ArrayBuffer

object TextProcessor extends App {
  val data = TsvReader.readTsvSource()
  val counts = Map[String, Int]()
  for (chunk <- data) {
    val words = chunk.split(" ")
    for (word <- words) {
      if (counts.contains(word))
        counts(word) += 1
      else
        counts(word) = 1
    }
  }
  //for ((word,count) <- counts) println(s"$word: $count")
}

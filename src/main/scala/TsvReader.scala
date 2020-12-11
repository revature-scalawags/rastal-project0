import scala.collection.mutable.ArrayBuffer
import scala.io.Source

object TsvReader {
  val path = "src/main/python/test.tsv"

  def readTsvSource(): ArrayBuffer[String] = {
    val source = Source.fromFile(path)
    val data = new ArrayBuffer[String]()
    for (line <- source.getLines()) {
      data.append(line)
    }
    source.close()

    data
  }
}

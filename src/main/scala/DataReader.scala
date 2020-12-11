import scala.collection.mutable.ArrayBuffer
import scala.io.Source

object DataReader {
  val path = "data/data.txt"

  /** Returns the lines of text from the source file as an ArrayBuffer[String]
    * for processing.
    */
  def readData(): ArrayBuffer[String] = {
    val source = Source.fromFile(path)
    val data = new ArrayBuffer[String]()
    for (line <- source.getLines()) {
      data.append(line)
    }
    source.close()

    data
  }
}

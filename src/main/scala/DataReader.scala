import scala.collection.mutable.ArrayBuffer
import scala.io.Source

object DataReader {
  val path = "data/data.txt"

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

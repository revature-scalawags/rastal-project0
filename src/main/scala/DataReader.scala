import scala.collection.mutable.ArrayBuffer
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.io.Source

object DataReader {
  val DefaultPath = "data/data.txt"

  /** Returns the lines of text from the source file as an ArrayBuffer[String]
    * for processing.
    */
  def readData(path: String = DefaultPath): Future[ArrayBuffer[String]] = Future {
    val source = Source.fromFile(path)
    val data = new ArrayBuffer[String]()
    for (line <- source.getLines()) {
      data.append(line)
    }
    source.close()

    data
  }
}

import org.scalatest.funsuite.AnyFunSuite
import scala.io.Source
import scala.util.control.Breaks

class DataReaderTest extends AnyFunSuite {
  val Path = "data/test.txt"

  test("correctly opens source file") {
    val source = Source.fromFile(Path)
    val loop = new Breaks
    var i = 0
    loop.breakable {
      for (line <- source.getLines()) {
        assert(line == "#testA1 testA2")
        loop.break()
      }
    }
  }
  
  test("correctly converts lines of text to items in ArrayBuffer[String]") {
    val data = DataReader.readData(Path)
    assert(data(0) == "#testA1 testA2")
  }
}

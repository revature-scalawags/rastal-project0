import org.scalatest.funsuite.AnyFunSuite
import scala.collection.mutable

class TextProcessorTest extends AnyFunSuite {
  test("correctly cleans word of leading and trailing symbols") {
    val cleaned = TextProcessor.cleanWord("@test!")
    assert(cleaned == "test")
  }

  test("correctly determines if a string is a word") {
    assert(TextProcessor.isWord("test") && !TextProcessor.isWord("#@!"))
  }

  test("correctly counts word occurrences") {
    val raw = mutable.ArrayBuffer("test1", "test2", "test1")
    val sorted = TextProcessor.processData(raw)
    var i = 0
    for ((k,v) <- sorted) {
      if (i == 0)
        assert(k == "test1" && v == 2)
      else
        assert(k == "test2" && v == 1)
      i += 1
    }
  }
}

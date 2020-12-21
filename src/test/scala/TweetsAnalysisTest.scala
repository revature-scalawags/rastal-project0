import org.scalatest.funsuite.AnyFunSuite
import scala.collection.mutable
import TweetsAnalysis._

class TweetsAnalysisTest extends AnyFunSuite {
  test("correctly cleans word of leading and trailing symbols") {
    assert("@test!".clean == "test")
  }

  test("correctly determines if 'test' is a word") {
    assert("test".isWord)
  }

  test("correctly determines that '#@!' is not a word") {
    assert(!"#@!".isWord)
  }

  test("correctly determines that a url is not a word") {
    assert(!"https://test.test".isWord)
  }

  test("correctly counts word occurrences") {
    val text = List("test2 test1", "test1 test2", "test1")
    val counts = TweetsAnalysis.cleanAndCountWords(text)
    assert(counts("test1") == 3)
    assert(counts("test2") == 2)
  }

  test("correctly sorts word counts") {
    val text = List("test2 test1", "test1 test2", "test1")
    val sorted = TweetsAnalysis.cleanAndCountWords(text).sort
    val words = sorted.keys.toVector
    assert(words(0) == "test1")
    assert(words(1) == "test2")
  }

  test("correctly creates Counts object") {
    val text = List("test2 test1", "test1 test2", "test1")
    val counts = TweetsAnalysis.cleanAndCountWords(text).sort.toCounts
    val test1 = counts.counts(0)
    assert(test1.word == "test1" && test1.count == 3)
  }
}

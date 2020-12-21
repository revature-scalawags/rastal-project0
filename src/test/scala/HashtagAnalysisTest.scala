import org.scalatest.funsuite.AnyFunSuite

import scala.collection.mutable

import HashtagAnalysis._

class HashtagAnalysisTest extends AnyFunSuite {
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

  test("correctly converts List of multi-word strings to List of single words") {
    val text = List("test @test1 testA.", "testB, test2")
    val words = text.toCleanedWords
    assert(words(0) == "test")
    assert(words(1) == "test1")
    assert(words(3) == "testB")
  }

  test("correctly counts word occurrences") {
    val text = List("test2 test1", "test1 test2", "test1")
    val counts = text.toCleanedWords.tally
    assert(counts("test1") == 3)
    assert(counts("test2") == 2)
  }

  test("correctly sorts word counts") {
    val text = List("test2 test1", "test1 test2", "test1", "test3", "test4")
    val sorted = text.toCleanedWords.tally.sort
    val words = sorted.keys.toList
    assert(words(0) == "test1")
    assert(words(1) == "test2")
  }

  test("correctly creates Counts object") {
    val text = List("test2 test1", "test1 test2", "test1")
    val counts = text.toCleanedWords.tally.sort.toCounts
    val test1 = counts.counts(0)
    assert(test1.word == "test1" && test1.count == 3)
  }
}

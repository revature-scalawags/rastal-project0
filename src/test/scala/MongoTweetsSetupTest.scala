import org.scalatest.funsuite.AnyFunSuite

import scala.io.Source
import scala.util.control.Breaks

class MongoTweetsSetupTest extends AnyFunSuite {
  val Path = "data/test.txt"

  test("correctly opens source file") {
    val test = Source.fromFile(Path).getLines().toList
    assert(test.head == "#testA1 testA2")
  }
  
  test("correctly converts lines of text to items in List of Strings") {
    val data = MongoTweetsSetup.readFromSource(Path)
    assert(data(0) == "#testA1 testA2")
  }

  test("correctly converts List of Strings into a Tweets object") {
    val strings = List("hashtag", "testing testing", "also testing")
    val tweets = MongoTweetsSetup.convertToMongo(strings)
    assert(tweets.hashtag == "hashtag")
    assert(tweets.tweets(0) == "testing testing")
  }
}

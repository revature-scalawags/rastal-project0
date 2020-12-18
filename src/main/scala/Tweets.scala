import org.mongodb.scala.bson.ObjectId

object Tweets {
  def apply(hashtag: String, tweets: List[String]): Tweets =
    Tweets(new ObjectId(), hashtag, tweets)
}

case class Tweets(_id: ObjectId, hashtag: String, tweets: List[String])
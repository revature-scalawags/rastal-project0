import org.mongodb.scala.bson.ObjectId

object Tweets {
  def apply(tweets: List[String]): Tweets =
    Tweets(new ObjectId(), tweets)
}

case class Tweets(_id: ObjectId, tweets: List[String])
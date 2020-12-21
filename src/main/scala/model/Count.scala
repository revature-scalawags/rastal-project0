import org.mongodb.scala.bson.ObjectId

object Count {
  def apply(word: String, count: Int): Count =
    Count(new ObjectId(), word, count)
}

case class Count(_id: ObjectId, word: String, count: Int)
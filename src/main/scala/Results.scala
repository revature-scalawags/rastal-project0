import org.mongodb.scala.bson.ObjectId

object Results {
  def apply(counts: List[Count]): Results =
    Results(new ObjectId(), counts)
}

case class Results(_id: ObjectId, counts: List[Count])
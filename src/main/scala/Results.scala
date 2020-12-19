import java.text.SimpleDateFormat
import java.util.{Calendar, Date}
import org.mongodb.scala.bson.ObjectId

object Results {
  def apply(counts: List[Count]): Results =
    Results(new ObjectId(), Calendar.getInstance.getTime, counts)
}

case class Results(_id: ObjectId, timestamp: Date, counts: List[Count])
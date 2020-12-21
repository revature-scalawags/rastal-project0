import java.text.SimpleDateFormat
import java.util.{Calendar, Date}
import org.mongodb.scala.bson.ObjectId

object Counts {
  def apply(counts: List[Count]): Counts =
    Counts(new ObjectId(), Calendar.getInstance.getTime, counts)
}

case class Counts(_id: ObjectId, timestamp: Date, counts: List[Count])
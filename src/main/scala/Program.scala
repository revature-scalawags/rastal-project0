object Program extends App {
  var keyword = ""
  if (args.length > 0) {
    keyword = args(0)
  }
  val data = DataReader.readData()
  val sortedCounts = TextProcessor.processData(data)
  for ((word,count) <- sortedCounts) println(s"$word: $count")
}
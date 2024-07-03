import scala.io.Source
import scala.collection._

object MyApp extends App {
  val path = "src/main/resources/aefi.csv"
  val source = Source.fromFile(path)
  val lines = source.getLines()

  // read data from csv
  lines.foreach(println)

  source.close()
}
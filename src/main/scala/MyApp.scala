import scala.io.Source
import scala.collection._

case class AEFIData(
                     date: String,
                     vaxtype: String,
                     d1_headache: Int,
                     d2_headache: Int,
                     d1_vomiting: Int,
                     d2_vomiting: Int
                   )

object MyApp extends App {
  val path = "src/main/resources/aefi.csv"
  val source = Source.fromFile(path)
  val lines = source.getLines()


  source.close()
}
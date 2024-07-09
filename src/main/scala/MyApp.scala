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

  def readAEFIData(filePath: String): List[AEFIData] = {
      val source = Source.fromFile(filePath)
      val lines = source.getLines().drop(1) // Skip header line
      val data = lines.map { line =>
        val cols = line.split(",").map(_.trim)
        AEFIData(
          cols(0),
          // vaccination type
          cols(1),
          // headache
          cols(12).toInt,
          cols(24).toInt,
          // vomiting
          cols(17).toInt,
          cols(29).toInt
        )
      }.toList
      source.close()
      data
    }

    // read data
    val aefiData = readAEFIData(path)

    // question 1
    // Aggregate data for each vaccine type
    println("\nQuestion 1: Which vaccination product is the most commonly used by Malaysian?")

    val totalDosesByVaxType = aefiData.groupBy(_.vaxtype).map { case (vaxType, records) =>
      val totalDoses = records.size
      vaxType -> totalDoses
    }

    println("Total doses by vaccine type:")
    totalDosesByVaxType.foreach { case (vaxType, totalDoses) =>
      println(s"$vaxType: $totalDoses")
    }

    val mostCommonVaccine = totalDosesByVaxType.maxBy(_._2)
    println(s"Ans: The most commonly used vaccine is ${mostCommonVaccine._1} with a total of ${mostCommonVaccine._2} doses.")

  // question 2
  // Group data by vaccination type
  println("\nQuestion 2: What are the average occurrence of headache for each type of vaccination product in the provided data?")

  val aefiGroupedByVaxType = aefiData.groupBy(_.vaxtype)

  // Calculate average occurrences of headache for each vaccination type
  val avgHeadacheByVaxType = aefiGroupedByVaxType.map { case (vaxType, records) =>
    val totalHeadache = records.map(r => r.d1_headache + r.d2_headache).sum
    val count = records.size
    vaxType -> (totalHeadache.toDouble / count)
  }

  // display each brand
  println("Ans: \nAverage occurrence of headache for each vaccination product:")
  avgHeadacheByVaxType.foreach { case (vaxType, avgHeadache) =>
    println(s"$vaxType: $avgHeadache")
  }
}

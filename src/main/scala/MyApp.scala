import scala.io.Source
import scala.collection._

// extract useful data
case class AEFIData(
                     vaxtype: String,
                     d1_headache: Int,
                     d2_headache: Int,
                     d1_vomiting: Int,
                   )

object MyApp extends App {
  val current = System.currentTimeMillis()

  // convert each line to AEFIDate instances
  def readAEFIData(filePath: String): List[AEFIData] = {
    val source = Source.fromFile(filePath)
    try {
      val lines = source.getLines().drop(1) // skip header line
      val data = lines.map { line =>
        val cols = line.split(",").map(_.trim)
        AEFIData(
          // vaccination type
          cols(1),
          // headache
          cols(12).toInt, // d1_headache
          cols(24).toInt, // d2_headache
          // vomiting
          cols(17).toInt, // d1_vomiting
        )
      }.toList
      data
    } finally {
      source.close()
    }
  }
  // location of aefi.csv
  val path = "src/main/resources/aefi.csv"
  // read data from aefi.csv
  val aefiData = readAEFIData(path)
  // group aefiData by vax type
  val aefiGroupedData = aefiData.groupBy(_.vaxtype)


  // function used in question 1
  // get total number of doses by vax type
  def getTotalDosesByVaxType: Map[String, Int] = {
    aefiGroupedData.map { case (vaxType, records) =>
      vaxType -> records.size // total doses
    }
  }
  // function used in question 2
  // get average headache by vax type
  def getAverageHeadacheByVaxType: Map[String, Double] = {
    aefiGroupedData.map { case (vaxType, records) =>
      val totalHeadache = records.map(r => r.d1_headache + r.d2_headache).sum
      vaxType -> (totalHeadache.toDouble / records.size) // average headache
    }
  }
  // function used in question 3
  // get vomiting occurrence after first injection by vax type
  def getVomitingAfterFirstInjection: Map[String, Int] = {
    aefiGroupedData.map { case (vaxType, records) =>
      val totalVomitingD1 = records.map(_.d1_vomiting).sum
      vaxType -> totalVomitingD1 // total vomiting after first injection
    }
  }

  println("PRG2103 Assignment 2")
  // question 1
  println("\nQuestion 1: Which vaccination product is the most commonly used by Malaysian?")
  val totalDosesByVaxType = getTotalDosesByVaxType
  val mostCommonVaccine = totalDosesByVaxType.maxBy(_._2)
  println("Total doses for each vaccine product:")
  totalDosesByVaxType.foreach { case (vaxType, totalDoses) =>
    println(s"$vaxType: $totalDoses")
  }
  println(s"Ans: The most commonly used vaccine product is ${mostCommonVaccine._1} with a total of ${mostCommonVaccine._2} doses.")


  // question 2
  println("\nQuestion 2: What are the average occurrence of headache for each type of vaccination product in the provided data?")
  val avgHeadacheByVaxType = getAverageHeadacheByVaxType
  println("Ans: Average occurrence of headache for each type of vaccination product:")
  avgHeadacheByVaxType.foreach { case (vaxType, avgHeadache) =>
    println(s"$vaxType: $avgHeadache")
  }


  // question 3
  println("\nQuestion 3: Which vaccination type has the highest occurrence of vomiting after first injection in the provided data?")
  val vomitingAfterFirstInjection = getVomitingAfterFirstInjection
  val highestVomitingVaxType = vomitingAfterFirstInjection.maxBy(_._2)
  println("Total vomiting occurrences after first injection by vaccination type:")
  vomitingAfterFirstInjection.foreach { case (vaxType, totalVomitingD1) =>
    println(s"$vaxType: $totalVomitingD1")
  }
  println(s"Ans: The vaccination type with the highest occurrence of vomiting after the first injection is ${highestVomitingVaxType._1} with ${highestVomitingVaxType._2} occurrences.")

  val end = System.currentTimeMillis()
  println("Time taken: " + (end - current) + "ms" ) // 272 millisecond = 2.72s
}
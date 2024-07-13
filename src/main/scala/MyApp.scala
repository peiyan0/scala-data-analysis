import scala.io.Source
import scala.collection.mutable.{ArrayBuffer, HashMap}


// extract useful data
case class AEFIData(
 vax_type: String,
 d1_headache: Int,
 d2_headache: Int,
 d1_vomiting: Int,
)

object MyApp extends App {
  val startTime = System.currentTimeMillis()

  // read AEFI data from CSV file
  def readAEFIData(filePath: String): ArrayBuffer[AEFIData] = {
    val source = Source.fromFile(filePath)
    val dataBuffer = ArrayBuffer.empty[AEFIData]

    try {
      // Iterate over lines in parallel and process each line
      source.getLines().drop(1).foreach { line =>
        val cols = line.split(",").map(_.trim)
        val aefiData = AEFIData(
          cols(1),      // vax_type
          cols(12).toInt, // d1_headache
          cols(24).toInt, // d2_headache
          cols(17).toInt  // d1_vomiting
        )
        dataBuffer += aefiData
      }
    } catch {
      case ex: Exception =>
        println(s"Error reading data from $filePath: ${ex.getMessage}")
    } finally {
      source.close()
    }
    dataBuffer
  }

  // location of aefi.csv
  val path = "src/main/resources/aefi.csv"
  // read data from aefi.csv
  val aefiData: ArrayBuffer[AEFIData] = readAEFIData(path)

  // Function to process AEFI data and compute required statistics
  def processAEFIData(data: ArrayBuffer[AEFIData]): Unit = {
    // Use mutable HashMap for grouping
    val groupedData = HashMap.empty[String, ArrayBuffer[AEFIData]]

    // Populate grouped data
    data.foreach { aefi =>
      val vaxType = aefi.vax_type
      if (!groupedData.contains(vaxType)) {
        groupedData(vaxType) = ArrayBuffer.empty[AEFIData]
      }
      groupedData(vaxType) += aefi
    }

    // function used in question 1
    // get total number of doses by vax type
    def getTotalDosesByVaxType: Map[String, Int] = {
      groupedData.mapValues(_.size).toMap
    }

    // function used in question 2
    // get average headache by vax type
    def getAverageHeadacheByVaxType: Map[String, Double] = {
      groupedData.mapValues { records =>
        val totalHeadache = records.map(r => r.d1_headache + r.d2_headache).sum
        totalHeadache.toDouble / records.size
      }.toMap
    }

    // function used in question 3
    // get vomiting occurrence after first injection by vax type
    def getVomitingAfterFirstInjection: Map[String, Int] = {
      groupedData.mapValues(_.map(_.d1_vomiting).sum).toMap
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

    val endTime = System.currentTimeMillis()
    println("Time taken: " + (endTime - startTime) + "ms") // 272 millisecond = 2.72s
  }
  processAEFIData(aefiData)
}
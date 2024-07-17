import scala.io.Source
import scala.collection.mutable.ArrayBuffer

// extract useful data
case class AEFIData( vaxType: String,
                     d1_headache: Int,
                     d2_headache: Int,
                     d1_vomiting: Int )

object MyApp extends App {
  // read AEFI data from CSV file
  def readAEFIData(filePath: String): Seq[AEFIData] = {
    val source = Source.fromFile(filePath)
    val dataBuffer = ArrayBuffer.empty[AEFIData]
    try {
      source.getLines().drop(1).foreach { line => // remove header
        val cols = line.split(",").map(_.trim)
        val aefiData = AEFIData(
          cols(1),       // vaxType
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
    dataBuffer.toSeq
  }

  // group AEFI data by vaccine type
  def groupAEFIData[T](data: Seq[AEFIData], keyExtractor: AEFIData => T): Map[T, Seq[AEFIData]] = {
    data.groupBy(keyExtractor)
  }

  // print sorted map data given description in table with 2 columns
  def printSortedMap2[T](data: Seq[(T, Int)], description: String)(implicit ordering: Ordering[T]): Unit = {
    println(description)
    println(f"${"Vax Type"}%-20s | ${"Total Occurrence"}%-20s ")
    println("-" * 45)
    data.sortBy(_._1).foreach { case (vaxType, total) =>
      println(f"$vaxType%-20s | $total%-20d ")
    }
  }

  // print sorted map data given description in table with 3 columns
  def printSortedMap3[T](data: Seq[(T, (Int, Double))], description: String)(implicit ordering: Ordering[T]): Unit = {
    println(description)
    println(f"${"Vax Type"}%-20s | ${"Total Occurrence"}%-20s | ${"Average Occurrence"}%-20s")
    println("-" * 70)
    data.sortBy(_._1).foreach { case (vaxType, (total, avg)) =>
      println(f"$vaxType%-20s | $total%-20d | $avg%-20.2f")
    }
  }

  // calculate total doses by vaccine type
  def totalDosesByVaxType(groupedData: Map[String, Seq[AEFIData]]): Unit = {
    val totalDosesByVaxType = groupedData.mapValues(_.size).toSeq
    val mostCommonVaccine = totalDosesByVaxType.maxBy(_._2)
    printSortedMap2(totalDosesByVaxType, "Total doses for each vaccine product:")
    println(s"Ans: The most commonly used vaccine product is ${mostCommonVaccine._1} with a total of ${mostCommonVaccine._2} doses.")
  }

  // calculate total and average headache by vaccine type
  def headacheByVaxType(groupedData: Map[String, Seq[AEFIData]]): Unit = {
    val totalHeadacheByVaxType = groupedData.mapValues { records =>
      records.map(r => r.d1_headache + r.d2_headache).sum
    }
    val avgHeadacheByVaxType = totalHeadacheByVaxType.map { case (vaxType, totalHeadache) =>
      val avgHeadache = totalHeadache.toDouble / groupedData(vaxType).size
      (vaxType, (totalHeadache, avgHeadache))
    }.toSeq
    printSortedMap3(avgHeadacheByVaxType, "Total and Average Occurrence of Headache for each type of vaccination product:")
  }

  // calculate vomiting occurrences after first injection by vaccine type
  def vomitingAfterFirstInjection(groupedData: Map[String, Seq[AEFIData]]): Unit = {
    val vomitingAfterFirstInjection = groupedData.mapValues(_.map(_.d1_vomiting).sum).toSeq
    val highestVomitingVaxType = vomitingAfterFirstInjection.maxBy(_._2)
    printSortedMap2(vomitingAfterFirstInjection, "Total vomiting occurrences after first injection by vaccination type:")
    println(s"Ans: The vaccination type with the highest occurrence of vomiting after the first injection is ${highestVomitingVaxType._1} with ${highestVomitingVaxType._2} occurrences.")
  }

  // main execution
  // location of aefi.csv
  val path = "src/main/resources/aefi.csv"
  // read data from aefi.csv
  val aefiData: Seq[AEFIData] = readAEFIData(path)
  // group data by vax type
  val groupedData: Map[String, Seq[AEFIData]] = groupAEFIData(aefiData, _.vaxType)

  println("  PRG2103 Assignment 2")
  println("-" * 25) // decorator
  // Question 1
  println("\nQuestion 1: Which vaccination product is the most commonly used by Malaysian?")
  totalDosesByVaxType(groupedData)

  // Question 2
  println("\n\nQuestion 2: What are the average occurrence of headache for each type of vaccination product in the provided data?")
  headacheByVaxType(groupedData)

  // Question 3
  println("\n\nQuestion 3: Which vaccination type has the highest occurrence of vomiting after first injection in the provided data?")
  vomitingAfterFirstInjection(groupedData)
}

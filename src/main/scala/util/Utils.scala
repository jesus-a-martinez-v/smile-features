package util

object Utils {
  def printColumnSample(column: Array[Double], sampleSize: Int = 10): Unit =
    println(column.take(sampleSize).mkString(", "))

}

import smile.data.{Attribute, AttributeDataset, NumericAttribute}
import smile.feature.Standardizer
import smile.math.Math.standardize
import smile.read
import util.Utils

object Standardizing extends App {
  val attributes = new Array[Attribute](5)

  attributes(0) = new NumericAttribute("Frequency")
  attributes(1) = new NumericAttribute("Angle of attack")
  attributes(2) = new NumericAttribute("Chord length")
  attributes(3) = new NumericAttribute("Free-stream velocity")
  attributes(4) = new NumericAttribute("Suction side displacement thickness")

  val y = new NumericAttribute("Scaled sound pressure level")

  val dataFileUri = this.getClass.getClassLoader.getResource("airfoil_self_noise.dat").toURI.getPath
  val data: AttributeDataset = read.table(dataFileUri, attributes = attributes, response = Some((y, 5)))

  // Using Standardizer object.
  val standardizer = new Standardizer()
  standardizer.learn(attributes, data.x())
  println(standardizer)
  Utils.printColumnSample(standardizer.transform(data.x()(0)))

  // Using standardize function
  println(s"Data without standardizing: ")
  println(data)

  println(s"Standardize data: ")
  standardize(data.x())
  println(data)
}

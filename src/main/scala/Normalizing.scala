import smile.data.{Attribute, AttributeDataset, NumericAttribute}
import smile.feature.Normalizer
import smile.math.Math.normalize
import smile.read
import util.Utils

object Normalizing extends App {
  val attributes = new Array[Attribute](5)

  attributes(0) = new NumericAttribute("Frequency")
  attributes(1) = new NumericAttribute("Angle of attack")
  attributes(2) = new NumericAttribute("Chord length")
  attributes(3) = new NumericAttribute("Free-stream velocity")
  attributes(4) = new NumericAttribute("Suction side displacement thickness")

  val y = new NumericAttribute("Scaled sound pressure level")

  val dataFileUri = this.getClass.getClassLoader.getResource("airfoil_self_noise.dat").toURI.getPath
  val data: AttributeDataset = read.table(dataFileUri, attributes = attributes, response = Some((y, 5)))

  // Using Normalizer object.
  val normalizer = new Normalizer()
  normalizer.learn(attributes, data.x())
  println(normalizer)
  Utils.printColumnSample(normalizer.transform(data.x()(0)))

  // Using normalizer function
  println(s"Data without normalization: ")
  println(data)

  println(s"Normalized data: ")
  normalize(data.x())
  println(data)
}

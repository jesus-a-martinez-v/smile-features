import smile.data.{Attribute, AttributeDataset, NumericAttribute}
import smile.feature.{MaxAbsScaler, Scaler, WinsorScaler}
import smile.math.Math.scale
import smile.read
import util.Utils

object Scaling extends App {
  val attributes = new Array[Attribute](5)

  attributes(0) = new NumericAttribute("Frequency")
  attributes(1) = new NumericAttribute("Angle of attack")
  attributes(2) = new NumericAttribute("Chord length")
  attributes(3) = new NumericAttribute("Free-stream velocity")
  attributes(4) = new NumericAttribute("Suction side displacement thickness")

  val y = new NumericAttribute("Scaled sound pressure level")

  val dataFileUri = this.getClass.getClassLoader.getResource("airfoil_self_noise.dat").toURI.getPath
  val data: AttributeDataset = read.table(dataFileUri, attributes = attributes, response = Some((y, 5)))

  // Using Scaler object.
  val scaler = new Scaler()
  scaler.learn(attributes, data.x())
  println(scaler)
  Utils.printColumnSample(scaler.transform(data.x()(0)))

  // Using MaxAbsScaler
  val maxAbsScaler = new MaxAbsScaler()
  maxAbsScaler.learn(attributes, data.x())
  println(maxAbsScaler)
  Utils.printColumnSample(maxAbsScaler.transform(data.x()(0)))

  // Using MaxAbsScaler
  val winsorScaler = new WinsorScaler(0.05, 0.95)
  winsorScaler.learn(attributes, data.x())
  println(winsorScaler)
  Utils.printColumnSample(winsorScaler.transform(data.x()(0)))

  // Using scaler function
  println(s"Data without scaling: ")
  println(data)

  println(s"Scaled data: ")
  scale(data.x())
  println(data)
}

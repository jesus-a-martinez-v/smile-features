import smile.data.{Attribute, AttributeDataset, NominalAttribute}
import smile.feature.OneHotEncoder
import smile.read

object OneHotEncoding extends App {
  val attributes = new Array[Attribute](6)

  attributes(0) = new NominalAttribute("buying")
  attributes(1) = new NominalAttribute("maint")
  attributes(2) = new NominalAttribute("doors")
  attributes(3) = new NominalAttribute("persons")
  attributes(4) = new NominalAttribute("lug_boot")
  attributes(5) = new NominalAttribute("safety")

  val label = new NominalAttribute("Class")

  val dataFileUri = this.getClass.getClassLoader.getResource("car.data").toURI.getPath
  val data: AttributeDataset = read.csv(dataFileUri, attributes = attributes, response = Some((label, 6)))

  val oneHotEncoder = new OneHotEncoder(attributes)
  oneHotEncoder.attributes().foreach(println)
}

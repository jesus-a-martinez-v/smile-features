import smile.classification.{GradientTreeBoost, randomForest}
import smile.data.{Attribute, AttributeDataset, NominalAttribute, NumericAttribute}
import smile.feature.{GAFeatureSelection, sumSquaresRatio}
import smile.read
import smile.validation.Accuracy

object FeatureSelection extends App {
  val attributes = new Array[Attribute](4)

  attributes(0) = new NumericAttribute("sepal length in cm")
  attributes(1) = new NumericAttribute("sepal width in cm")
  attributes(2) = new NumericAttribute("petal length in cm")
  attributes(3) = new NumericAttribute("petal width in cm")

  val label = new NominalAttribute("class")

  val dataFileUri = this.getClass.getClassLoader.getResource("iris.data").toURI.getPath
  val data: AttributeDataset = read.csv(dataFileUri, attributes = attributes, response = Some((label, 4)), header = true)

  // Sum of squares ratio
  val sumOfSquaresRatio = sumSquaresRatio(data.x(), data.labels())
  println(s"Sum of squares ratio: ${sumOfSquaresRatio.mkString(", ")}")

  // Ensemble learning based feature selection
  val ensembleModel = randomForest(data.x(), data.labels())
  println(s"Importance: ${ensembleModel.importance().mkString(", ")}")

  // Genetic algorithm based feature selection
  val trainer = new GradientTreeBoost.Trainer(100)
  val measure = new Accuracy

  val selector = new GAFeatureSelection

  val result = selector.learn(50, 20, trainer, measure, data.x(), data.labels(), 5)

  result.foreach { bits =>
    print(f"${100 * bits.fitness()}%.2f%%")
    println(bits.bits.mkString(" "))
  }
}

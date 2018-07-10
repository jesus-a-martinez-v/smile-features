import smile.data.{Attribute, AttributeDataset, NominalAttribute, NumericAttribute}
import smile.math.kernel.GaussianKernel
import smile.projection.{gha, kpca, pca, ppca}
import smile.read

object DimensionalityReduction extends App {
  val attributes = new Array[Attribute](8)

  attributes(0) = new NominalAttribute("Sequence Name")
  attributes(1) = new NumericAttribute("mcg")
  attributes(2) = new NumericAttribute("gvh")
  attributes(3) = new NumericAttribute("lip")
  attributes(4) = new NumericAttribute("chg")
  attributes(5) = new NumericAttribute("aac")
  attributes(6) = new NumericAttribute("alm1")
  attributes(7) = new NumericAttribute("alm2")

  val label = new NominalAttribute("class")

  val dataFileUri = this.getClass.getClassLoader.getResource("ecoli.csv").toURI.getPath
  val data: AttributeDataset = read.csv(dataFileUri, attributes = attributes, response = Some((label, 8)))

  val pc = pca(data.x())
  pc.setProjection(3)
  val xPca = pc.project(data.x())

  println(s"Number of columns X: ${data.x().head.length}")
  println(s"Number of columns of projected X after PCA: ${xPca.head.length}")

  val ppc = ppca(data.x(), 3)
  val xPpca = ppc.project(data.x())

  println(s"Number of columns X: ${data.x().head.length}")
  println(s"Number of columns of projected X after PPCA: ${xPpca.head.length}")

  val kpc = kpca(data.x(), new GaussianKernel(45), 3)
  val xKpca = kpc.project(data.x())

  println(s"Number of columns X: ${data.x().head.length}")
  println(s"Number of columns of projected X after KPCA: ${xKpca.head.length}")

  val ghc = gha(data.x(), 3, 0.00001)
  val xGha = ghc.project(data.x())

  println(s"Number of columns X: ${data.x().head.length}")
  println(s"Number of columns of projected X after GHA: ${xGha.head.length}")

}

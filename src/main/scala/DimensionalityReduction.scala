import smile.data.{Attribute, AttributeDataset, NominalAttribute, NumericAttribute}
import smile.math.kernel.GaussianKernel
import smile.plot.{Palette, plot}
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
  compareLengthsAndPlot(data, xPca, "PCA")

  val ppc = ppca(data.x(), 3)
  val xPpca = ppc.project(data.x())
  compareLengthsAndPlot(data, xPpca, "PPCA")

  val kpc = kpca(data.x(), new GaussianKernel(45), 3)
  val xKpca = kpc.project(data.x())
  compareLengthsAndPlot(data, xKpca, "KPCA")

  val ghc = gha(data.x(), 3, 0.00001)
  val xGha = ghc.project(data.x())
  compareLengthsAndPlot(data, xGha, "GHA")

  private def compareLengthsAndPlot(d: AttributeDataset, projectedX: Array[Array[Double]], algorithmName: String): Unit = {
    println(s"Number of columns X: ${data.x().head.length}")
    println(s"Number of columns of projected X after $algorithmName: ${projectedX.head.length}")
    plot(projectedX, data.labels(), '.', Palette.COLORS)
  }
}

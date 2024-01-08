package polynomial.mermaid.render.p

import polynomial.mermaid.Mermaid.{ParamLabels, PolynomialLabels}
import polynomial.mermaid.render.{Font, Render}
import polynomial.`object`.{Monomial, Store}
import typename.NameOf
import typesize.SizeOf

trait MermaidP[P[_]]:
  // Base constructor of the P component of the graph
  def graphP(polynomialLabelP: PolynomialLabels[P], paramLabelsP: ParamLabels[P]): String
  // Impl constructors of the P component of the graph e.g. A[ ]-->B[ ]
  def graphPCardinal(polynomialLabelP: PolynomialLabels[P]): String
  def graphPGeneric(polynomialLabelP: PolynomialLabels[P], paramLabelsP: ParamLabels[P]): String
  def graphPSpecific(polynomialLabelP: PolynomialLabels[P]): String
  // Built-in coefficient labels and exponent labels, e.g., Boolean
  def paramLabelsCardinal: ParamLabels[P]
  def paramLabelsSpecific: ParamLabels[P]
  // Text representation of polynomials e.g., Byᴬ
  def polynomialCardinal: String
  def polynomialGeneric(paramLabelsP: ParamLabels[P]): String
  def polynomialSpecific: String
  // Variable labels, e.g., y
  def variableLabel: String

object MermaidP:

  given [S](using N: NameOf[S], S: SizeOf[S]): MermaidP[Store[S, _]] =
    new MermaidP[Store[S, _]]:
      def graphP(polynomialLabelP: String, paramLabelsP: String): String =
        s"S[$paramLabelsP]"
      def graphPCardinal(polynomialLabelP: String): String =
        graphP(polynomialLabelP, paramLabelsCardinal)
      def graphPGeneric(polynomialLabelP: String, paramLabelsP: String): String =
        graphP(polynomialLabelP, Font.courier(paramLabelsP))
      def graphPSpecific(polynomialLabelP: String): String =
        graphP(polynomialLabelP, Font.courier(paramLabelsSpecific))
      def paramLabelsCardinal: String =
        Render.cardinality(S.size)
      def paramLabelsSpecific: String =
        N.name
      def polynomialCardinal: String =
        Render.monomial(paramLabelsCardinal, variableLabel, paramLabelsCardinal)
      def polynomialGeneric(paramLabelsP: String): String =
        Render.monomial(Font.courier(paramLabelsP), variableLabel, Font.courier(paramLabelsP))
      def polynomialSpecific: String =
        Render.monomial(Font.courier(paramLabelsSpecific), variableLabel, Font.courier(paramLabelsSpecific))
      def variableLabel: String =
        Render.y

  given [A, B](using NA: NameOf[A], NB: NameOf[B], SA: SizeOf[A], SB: SizeOf[B]): MermaidP[Monomial[A, B, _]] =
    new MermaidP[Monomial[A, B, _]]:
      def graphP(polynomialLabelP: String, paramLabelsQ: (String, String)): String =
      s"""|A2[ ]:::point
          |subgraph s[ ]
          |  A2:::point---MermaidPMono
          |  MermaidPMono[${polynomialLabelP}]:::empty
          |  MermaidPMono---B2
          |end
          |B2[ ]:::point""".stripMargin
      def graphPCardinal(polynomialLabelP: String): String =
        graphP(polynomialLabelP, paramLabelsCardinal)
      def graphPGeneric(polynomialLabelP: String, paramLabelsP: (String, String)): String =
        graphP(polynomialLabelP, paramLabelsP)
      def graphPSpecific(polynomialLabelP: String): String =
         graphP(polynomialLabelP, paramLabelsSpecific)
      def paramLabelsCardinal: (String, String) =
        (Render.cardinality(SA.size), Render.cardinality(SB.size))
      def paramLabelsSpecific: (String, String) =
        (NA.name, NB.name)
      def polynomialCardinal: String =
        Render.monomial(Font.courier(paramLabelsCardinal._2), variableLabel, Font.courier(paramLabelsCardinal._1))
      def polynomialGeneric(paramLabelsP: (String, String)): String =
        Render.monomial(Font.courier(paramLabelsP._2), variableLabel, Font.courier(paramLabelsP._1))
      def polynomialSpecific: String =
        Render.monomial(Font.courier(paramLabelsSpecific._2), variableLabel, Font.courier(paramLabelsSpecific._1))
      def variableLabel: String = Render.y



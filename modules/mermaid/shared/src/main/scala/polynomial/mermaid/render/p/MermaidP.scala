package polynomial.mermaid.render.p

import polynomial.mermaid.Mermaid.{ParamLabels, PolynomialLabels}
import polynomial.mermaid.render.{Font, Render}
import polynomial.`object`.Monomial
import typename.NameOf
import typesize.SizeOf

trait MermaidP[P[_]]:
  // Base constructor of the P component of the graph
  def graphP[Y](nodeLabels: String, polynomialLabelP: PolynomialLabels[P[Y]], paramLabelsP: ParamLabels[P[Y]]): String
  // Impl constructors of the P component of the graph e.g. A[ ]-->B[ ]
  def graphPCardinal[Y](nodeLabels: String, polynomialLabelP: PolynomialLabels[P[Y]]): String
  def graphPGeneric[Y](nodeLabels: String, polynomialLabelP: PolynomialLabels[P[Y]], paramLabelsP: ParamLabels[P[Y]]): String
  def graphPSpecific[Y](nodeLabels: String, polynomialLabelP: PolynomialLabels[P[Y]]): String
  // Built-in coefficient labels and exponent labels, e.g., Boolean
  def paramLabelsCardinal[Y]: ParamLabels[P[Y]]
  def paramLabelsSpecific[Y]: ParamLabels[P[Y]]
  // Text representation of polynomials e.g., Byᴬ
  def polynomialCardinal: String
  def polynomialGeneric[Y](paramLabelsP: ParamLabels[P[Y]]): String
  def polynomialSpecific: String
  // Variable labels, e.g., y
  def variableLabel: String

object MermaidP:

  given [S](using N: NameOf[S], S: SizeOf[S]): MermaidP[Monomial.Store[S, _]] =
    new MermaidP[Monomial.Store[S, _]]:
      def graphP[Y](nodeLabels: String, polynomialLabelP: String, paramLabelsP: String): String =
        s"$nodeLabels[$paramLabelsP]"
      def graphPCardinal[Y](nodeLabels: String, polynomialLabelP: String): String =
        graphP(nodeLabels, polynomialLabelP, paramLabelsCardinal)
      def graphPGeneric[Y](nodeLabels: String, polynomialLabelP: String, paramLabelsP: String): String =
        graphP(nodeLabels, polynomialLabelP, Font.courier(paramLabelsP))
      def graphPSpecific[Y](nodeLabels: String, polynomialLabelP: String): String =
        graphP(nodeLabels, polynomialLabelP, Font.courier(paramLabelsSpecific))
      def paramLabelsCardinal[Y]: String =
        Render.cardinality(S.size)
      def paramLabelsSpecific[Y]: String =
        N.name
      def polynomialCardinal: String =
        Render.monomial(paramLabelsCardinal, variableLabel, paramLabelsCardinal)
      def polynomialGeneric[Y](paramLabelsP: String): String =
        Render.monomial(Font.courier(paramLabelsP), variableLabel, Font.courier(paramLabelsP))
      def polynomialSpecific: String =
        Render.monomial(Font.courier(paramLabelsSpecific), variableLabel, Font.courier(paramLabelsSpecific))
      def variableLabel: String =
        Render.y

  given [A, B](using NA: NameOf[A], NB: NameOf[B], SA: SizeOf[A], SB: SizeOf[B]): MermaidP[Monomial.Interface[A, B, _]] =
    new MermaidP[Monomial.Interface[A, B, _]]:
      def graphP[Y](nodeLabels: String, polynomialLabelP: String, paramLabelsQ: (String, String)): String =
      s"""|A_${nodeLabels}[ ]:::point
          |subgraph s[ ]
          |  A_${nodeLabels}:::point---${nodeLabels}
          |  ${nodeLabels}[${polynomialLabelP}]:::empty
          |  ${nodeLabels}---B_${nodeLabels}
          |end
          |B_${nodeLabels}[ ]:::point""".stripMargin
      def graphPCardinal[Y](nodeLabels: String, polynomialLabelP: String): String =
        graphP(nodeLabels, polynomialLabelP, paramLabelsCardinal)
      def graphPGeneric[Y](nodeLabels: String, polynomialLabelP: String, paramLabelsP: (String, String)): String =
        graphP(nodeLabels, polynomialLabelP, paramLabelsP)
      def graphPSpecific[Y](nodeLabels: String, polynomialLabelP: String): String =
         graphP(nodeLabels, polynomialLabelP, paramLabelsSpecific)
      def paramLabelsCardinal[Y]: (String, String) =
        (Render.cardinality(SA.size), Render.cardinality(SB.size))
      def paramLabelsSpecific[Y]: (String, String) =
        (NA.name, NB.name)
      def polynomialCardinal: String =
        Render.monomial(Font.courier(paramLabelsCardinal._2), variableLabel, Font.courier(paramLabelsCardinal._1))
      def polynomialGeneric[Y](paramLabelsP: (String, String)): String =
        Render.monomial(Font.courier(paramLabelsP._2), variableLabel, Font.courier(paramLabelsP._1))
      def polynomialSpecific: String =
        Render.monomial(Font.courier(paramLabelsSpecific._2), variableLabel, Font.courier(paramLabelsSpecific._1))
      def variableLabel: String = Render.y



package polynomial.mermaid.render.q

import polynomial.mermaid.Mermaid.ParamLabels
import polynomial.mermaid.render.{Font, Render}
import polynomial.`object`.Binomial.BiInterface
import polynomial.`object`.Monomial.Interface
import typename.NameOf
import typesize.SizeOf

trait MermaidQ[Q[_]]:
  // Base constructors of the Q component of the graph
  def graphQ[Y](p: String, nodeLabels: (String, String), paramLabelsQ: ParamLabels[Q[Y]]): String
  // Impl constructors of the Q component of the graph, e.g. A[ ]-->B[ ]
  def graphQCardinal(nodeLabels: (String, String)): String => String
  def graphQCustom[Y](nodeLabels: (String, String), paramLabelsQ: ParamLabels[Q[Y]]): String => String
  def graphQGeneric[Y](nodeLabels: (String, String), paramLabelsQ: ParamLabels[Q[Y]]): String => String
  def graphQSpecific(nodeLabels: (String, String)): String => String
  // Built-in coefficient labels and exponent labels, e.g., Boolean
  def paramLabelsCardinal[Y]: ParamLabels[Q[Y]]
  def paramLabelsSpecific[Y]: ParamLabels[Q[Y]]
  // String representation of polynomials e.g., Byᴬ
  def polynomialCardinal: String
  def polynomialGeneric[Y](paramLabelsQ: ParamLabels[Q[Y]]): String
  def polynomialSpecific: String
  // Variable labels, e.g., y
  def variableLabel: String

object MermaidQ:

  given mooreMonomial[A, B](
    using
      NA: NameOf[A],
      NB: NameOf[B],
      SA: SizeOf[A],
      SB: SizeOf[B]
    ): MermaidQ[Interface[A, B, _]] =
    new MermaidQ[Interface[A, B, _]]:
      def graphQ[Y](p: String, nodeLabels: (String, String), paramLabelsQ: (String, String)) =
        s"${nodeLabels._1}:::hidden---|${paramLabelsQ._1}|${p}---|${paramLabelsQ._2}|${nodeLabels._2}:::hidden;"
      def graphQCardinal(nodeLabels: (String, String)): String => String =
        p => graphQ(p, nodeLabels, paramLabelsCardinal)
      def graphQCustom[Y](nodeLabels: (String, String), paramLabelsQ: (String, String)): String => String =
        p => graphQ(p, nodeLabels, paramLabelsQ)
      def graphQGeneric[Y](nodeLabels: (String, String), paramLabelsQ: (String, String)): String => String =
        p => graphQ(p, nodeLabels, (Font.courier(paramLabelsQ._1), Font.courier(paramLabelsQ._2)))
      def graphQSpecific(nodeLabels: (String, String)): String => String =
        p => graphQ(p, nodeLabels, (Font.courier(paramLabelsSpecific._1), Font.courier(paramLabelsSpecific._2)))
      def paramLabelsCardinal[Y]: (String, String) =
        (Render.cardinality(SA.size), Render.cardinality(SB.size))
      def paramLabelsSpecific[Y]: (String, String) =
        (NA.name, NB.name)
      def polynomialCardinal: String =
        Render.monomial(paramLabelsCardinal._2, variableLabel, paramLabelsCardinal._1)
      def polynomialGeneric[Y](paramLabelsQ: (String, String)): String =
        Render.monomial(Font.courier(paramLabelsQ._2), variableLabel, Font.courier(paramLabelsQ._1))
      def polynomialSpecific: String =
        Render.monomial(Font.courier(paramLabelsSpecific._2), variableLabel, Font.courier(paramLabelsSpecific._1))
      def variableLabel: String =
        Render.y

  given mealyMonomial[A, B](
    using
      NA: NameOf[A],
      NB: NameOf[B],
      SA: SizeOf[A],
      SB: SizeOf[B]
    ): MermaidQ[Interface[A, A => B, _]] =
    new MermaidQ[Interface[A, A => B, _]]:
      def graphQ[Y](p: String, nodeLabels: (String, String), paramLabelsQ: (String, String)) =
        s"${nodeLabels._1}:::hidden---|${paramLabelsQ._1}|${p}---|${paramLabelsQ._2}|${nodeLabels._2}:::hidden;"
      def graphQCardinal(nodeLabels: (String, String)): String => String =
        p => graphQ(p, nodeLabels, paramLabelsCardinal)
      def graphQCustom[Y](nodeLabels: (String, String), paramLabelsQ: (String, String)): String => String =
        p => graphQ(p, nodeLabels, paramLabelsQ)
      def graphQGeneric[Y](nodeLabels: (String, String), paramLabelsQ: (String, String)): String => String =
        p => graphQ(p, nodeLabels, (Font.courier(paramLabelsQ._1), Font.courier(paramLabelsQ._2)))
      def graphQSpecific(nodeLabels: (String, String)): String => String =
        p => graphQ(p, nodeLabels, (Font.courier(paramLabelsSpecific._1), Font.courier(paramLabelsSpecific._2)))
      def paramLabelsCardinal[Y]: (String, String) =
        (Render.cardinality(SA.size), Render.cardinality(SA.size) + " => " + Render.cardinality(SB.size))
      def paramLabelsSpecific[Y]: (String, String) =
        (NA.name, (NA.name + " => " + NB.name))
      def polynomialCardinal: String =
        Render.monomial(paramLabelsCardinal._2, variableLabel, paramLabelsCardinal._1)
      def polynomialGeneric[Y](paramLabelsQ: (String, String)): String =
        Render.monomial(Font.courier(paramLabelsQ._2), variableLabel, Font.courier(paramLabelsQ._1))
      def polynomialSpecific: String =
        Render.monomial(Font.courier(paramLabelsSpecific._2), variableLabel, Font.courier(paramLabelsSpecific._1))
      def variableLabel: String =
        Render.y

  given mooreBinomial[A1, B1, A2, B2](using
    NA1: NameOf[A1],
    NB1: NameOf[B1],
    NA2: NameOf[A2],
    NB2: NameOf[B2],
    SA1: SizeOf[A1],
    SB1: SizeOf[B1],
    SA2: SizeOf[A2],
    SB2: SizeOf[B2],
  ): MermaidQ[BiInterface[A1, B1, A2, B2, _]] =
    new MermaidQ[BiInterface[A1, B1, A2, B2, _]]:
      def graphQ[Y](p: String, nodeLabels: (String, String), paramLabelsQ: ((String, String), (String, String))): String =
        s"""|${nodeLabels._1}[A1]:::hidden---|${paramLabelsQ._1._1}
            |     |    |S${p}
            |  ---|${paramLabelsQ._1._2}|${nodeLabels._2}:::hidden
            |    ~~~
            |  C[A2]:::hidden---|${paramLabelsQ._2._1}
            |     |    |T${p}
            |  ---|${paramLabelsQ._2._2}|D:::hidden;""".stripMargin
      def graphQCardinal(nodeLabels: (String, String)): String => String =
        p => graphQ(p, nodeLabels, paramLabelsCardinal)
      def graphQCustom[Y](nodeLabels: (String, String), paramLabelsQ: ((String, String), (String, String))): String => String =
        p => graphQ(p, nodeLabels, paramLabelsQ)
      def graphQGeneric[Y](nodeLabels: (String, String), paramLabelsQ: ((String, String), (String, String))): String => String =
        p => graphQ(p, nodeLabels, ((Font.courier(paramLabelsQ._1._1), Font.courier(paramLabelsQ._1._2)), (Font.courier(paramLabelsQ._2._1), Font.courier(paramLabelsQ._2._2))))
      def graphQSpecific(nodeLabels: (String, String)): String => String =
        p => graphQ(p, nodeLabels, paramLabelsSpecific)
      def paramLabelsCardinal[Y]: ((String, String), (String, String)) =
        ((Render.cardinality(SA1.size), Render.cardinality(SB1.size)), (Render.cardinality(SA2.size), Render.cardinality(SB2.size)))
      def paramLabelsSpecific[Y]: ((String, String), (String, String)) =
        ((NA1.name, NB1.name), (NA2.name, NB2.name))
      def polynomialCardinal: String =
        Render.binomial(
          paramLabelsCardinal._1._2, variableLabel, paramLabelsCardinal._1._1,
          paramLabelsCardinal._2._2, variableLabel, paramLabelsCardinal._2._1
        )
      def polynomialGeneric[Y](paramLabelsQ: ((String, String), (String, String))): String =
        Render.binomial(
          paramLabelsQ._1._2, variableLabel, paramLabelsQ._1._1,
          paramLabelsQ._2._2, variableLabel, paramLabelsQ._2._1
        )
      def polynomialSpecific: String =
        Render.binomial(
          paramLabelsSpecific._1._2, variableLabel, paramLabelsSpecific._1._1,
          paramLabelsSpecific._2._2, variableLabel, paramLabelsSpecific._2._1
        )
      def variableLabel: String =
        Render.y
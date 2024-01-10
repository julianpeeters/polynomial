package polynomial.mermaid

import polynomial.mermaid.Mermaid.CustomLabels
import polynomial.mermaid.render.p.MermaidP
import polynomial.mermaid.render.q.MermaidQ
import polynomial.mermaid.render.Format.{Cardinal, Generic, Specific}
import polynomial.mermaid.render.{Render, addTitle}
import polynomial.morphism.PolyMap
import polynomial.`object`.{Binomial, Monomial}

trait Mermaid[F[_]]:
  def showGraph(graphFmt: Format): String
  def showGraphCustom[Y](graphFmt: Format, labels: CustomLabels[F[Y]]): String
  def showTitle(titleFmt: Format): String
  def showTitleCustom[Y](labels: CustomLabels[F[Y]]): String
  def showTitleHtml(titleFmt: Format): String
  def showTitleHtmlCustom[Y](labels: CustomLabels[F[Y]]): String
  def showTitledGraph(titleFmt: Format, graphFmt: Format): String
  def showTitledGraphCustom[Y](graphFmt: Format, labels: CustomLabels[F[Y]]): String

object Mermaid:

  type CustomLabels[X] = X match
    case PolyMap[p, q, y] => CustomLabels[(p[y], q[y])]
    case (Monomial.Interface[a1, b1, y1], Monomial.Interface[a2, b2, y2]) => (String, String)
    case (Monomial.Store[s, y1], Binomial.Interface[a1, b1, a2, b2, y2]) => (String, String)
    case (Monomial.Store[s, y1], Monomial.Interface[a, b, y2]) => (String, String)

  type ParamLabels[X] = X match
    case Monomial.Interface[a, b, y] => (String, String)
    case Binomial.Interface[a1, b1, a2, b2, y] => ((String, String), (String, String))
    case Monomial.Store[s, y] => String
    
  type PolynomialLabels[X] = X match
    case Binomial.Interface[a1, b1, a2, b2, y] => String
    case Monomial.Interface[a, b, y] => String
    case Monomial.Store[s, y] => String

  given mooreStoreToMono[S, A, B](using
    P: MermaidP[Monomial.Store[S, _]],
    Q: MermaidQ[Monomial.Interface[A, B, _]],
  ): Mermaid[PolyMap[Monomial.Store[S, _], Monomial.Interface[A, B, _], _]] =
    new Mermaid[PolyMap[Monomial.Store[S, _], Monomial.Interface[A, B, _], _]]:
      private val labelS: String = "S"
      private val labelA: String = "A"
      private val labelB: String = "B"
      def showGraph(graphFmt: Format): String =
        graphFmt match
          case Cardinal =>
            Render.mermaidCodeFence(Q.graphQCardinal(P.graphPCardinal(P.polynomialCardinal)))
          case Generic =>
            Render.mermaidCodeFence(Q.graphQGeneric((labelA, labelB))(P.graphPGeneric(P.polynomialGeneric(labelS), labelS)))
          case Specific =>
            Render.mermaidCodeFence(Q.graphQSpecific(P.graphPSpecific(P.polynomialSpecific)))
      def showGraphCustom[Y](graphFmt: Format, labels: (String, String)): String =
        graphFmt match
          case Cardinal =>
            Render.mermaidCodeFence(Q.graphQCardinal(P.graphPCardinal(P.polynomialCardinal)))
          case Generic =>
            Render.mermaidCodeFence(Q.graphQGeneric((labelA, labelB))(P.graphPGeneric(P.polynomialGeneric(labelS), labelS)))
          case Specific =>
            Render.mermaidCodeFence(Q.graphQSpecific(P.graphPSpecific(P.polynomialSpecific)))
      def showTitle(titleFmt: Format): String =
        titleFmt match
          case Cardinal =>
            Render.mermaidCodeFence(Render.title(P.polynomialCardinal, 3, Q.polynomialCardinal, 3))
          case Generic =>
            Render.mermaidCodeFence(Render.title(P.polynomialGeneric(labelS), 3, Q.polynomialGeneric((labelA, labelB)), 3))
          case Specific =>
            Render.mermaidCodeFence(Render.title(P.polynomialSpecific, 3, Q.polynomialSpecific, 3))
      def showTitleCustom[Y](labels: (String, String)): String =
        Render.mermaidCodeFence(Render.title(labels._1, 3, labels._2, 3))     
      def showTitleHtml(titleFmt: Format): String =
        titleFmt match
          case Cardinal =>
            Render.polyMap(P.graphPCardinal(P.polynomialCardinal), Q.graphQCardinal(P.graphPCardinal(P.polynomialCardinal)))
          case Generic =>
            Render.polyMap(P.graphPGeneric(P.polynomialGeneric(labelS), labelS), Q.graphQGeneric((labelA, labelB))(P.graphPGeneric(P.polynomialGeneric(labelS), labelS)))
          case Specific =>
            Render.polyMap(P.graphPSpecific(P.polynomialSpecific), Q.graphQSpecific(P.graphPSpecific(P.polynomialSpecific)))
      def showTitleHtmlCustom[Y](labels: (String, String)): String =
        Render.polyMap(labels._1, labels._2)
      def showTitledGraph(titleFmt: Format, graphFmt: Format): String =
        (titleFmt, graphFmt) match
          case (Cardinal, Cardinal) =>
            Render.mermaidCodeFence(Q.graphQCardinal(P.graphPCardinal(P.polynomialCardinal)))
              .addTitle(P.polynomialCardinal, 3, Q.polynomialCardinal, 3)
          case (Cardinal, Generic) =>
            Render.mermaidCodeFence(Q.graphQGeneric((labelA, labelB))(P.graphPGeneric(P.polynomialGeneric(labelS), labelS)))
              .addTitle(P.polynomialCardinal, 3, Q.polynomialCardinal, 3)
          case (Cardinal, Specific) =>
            Render.mermaidCodeFence(Q.graphQCardinal(P.graphPCardinal(P.polynomialCardinal)))
              .addTitle(P.polynomialSpecific, 3, Q.polynomialSpecific, 3)
          case (Generic, Cardinal) =>
            Render.mermaidCodeFence(Q.graphQCardinal(P.graphPCardinal(P.polynomialCardinal)))
              .addTitle(P.polynomialGeneric(labelS), 3, Q.polynomialGeneric((labelA, labelB)), 3)
          case (Generic, Generic) =>
            Render.mermaidCodeFence(Q.graphQGeneric((labelA, labelB))(P.graphPGeneric(P.polynomialGeneric(labelS), labelS)))
              .addTitle(P.polynomialGeneric(labelS), 3, Q.polynomialGeneric((labelA, labelB)), 3)
          case (Generic, Specific) =>
            Render.mermaidCodeFence(Q.graphQSpecific(P.graphPSpecific(P.polynomialSpecific)))
              .addTitle(P.polynomialGeneric(labelS), 3, Q.polynomialGeneric((labelA, labelB)), 3)
          case (Specific, Cardinal) =>
            Render.mermaidCodeFence(Q.graphQSpecific(P.graphPSpecific(P.polynomialSpecific)))
              .addTitle(P.polynomialSpecific, 3, Q.polynomialSpecific, 3)
          case (Specific, Generic) =>
            Render.mermaidCodeFence(Q.graphQGeneric((labelA, labelB))(P.graphPGeneric(P.polynomialGeneric(labelS), labelS)))
              .addTitle(P.polynomialSpecific, 3, Q.polynomialSpecific, 3)
          case (Specific, Specific) =>
            Render.mermaidCodeFence(Q.graphQSpecific(P.graphPSpecific(P.polynomialSpecific)))
              .addTitle(P.polynomialSpecific, 3, Q.polynomialSpecific, 3)
      def showTitledGraphCustom[Y](graphFmt: Format, labels: (String, String)): String =
        graphFmt match
          case Cardinal =>
            Render.mermaidCodeFence(Q.graphQCardinal(P.graphPCardinal(P.polynomialCardinal)))
              .addTitle(labels._1, 3, labels._2, 3)
          case Generic =>
            Render.mermaidCodeFence(Q.graphQGeneric((labelA, labelB))(P.graphPGeneric(P.polynomialGeneric(labelS), labelS)))
              .addTitle(labels._1, 3, labels._2, 3)
          case Specific =>
            Render.mermaidCodeFence(Q.graphQSpecific(P.graphPSpecific(P.polynomialSpecific)))
              .addTitle(labels._1, 3, labels._2, 3)
        
  given mealyStoreToMono[S, A, B](using
    P: MermaidP[Monomial.Store[S, _]],
    Q: MermaidQ[Monomial.Interface[A, A => B, _]],
  ): Mermaid[PolyMap[Monomial.Store[S, _], Monomial.Interface[A, A => B, _], _]] =
    new Mermaid[PolyMap[Monomial.Store[S, _], Monomial.Interface[A, A => B, _], _]]:
      private val labelS = "S"
      private val labelA = "A"
      private val labelB = "B"
      def showGraph(graphFmt: Format): String =
        graphFmt match
          case Cardinal =>
            Render.mermaidCodeFence(Q.graphQCardinal(P.graphPCardinal(P.polynomialCardinal)))
          case Generic =>
            Render.mermaidCodeFence(Q.graphQGeneric((labelA, labelB))(P.graphPGeneric(P.polynomialGeneric(labelS), labelS)))
          case Specific =>
            Render.mermaidCodeFence(Q.graphQSpecific(P.graphPSpecific(P.polynomialSpecific)))
      def showGraphCustom[Y](graphFmt: Format, labels: (String, String)): String =
        graphFmt match
          case Cardinal =>
            Render.mermaidCodeFence(Q.graphQCardinal(P.graphPCardinal(P.polynomialCardinal)))
          case Generic =>
            Render.mermaidCodeFence(Q.graphQGeneric((labelA, labelB))(P.graphPGeneric(labels._1, labelS)))
          case Specific =>
            Render.mermaidCodeFence(Q.graphQSpecific(P.graphPSpecific(P.polynomialSpecific)))
      def showTitle(titleFmt: Format): String =
        titleFmt match
          case Cardinal =>
            Render.mermaidCodeFence(Render.title(P.polynomialCardinal, 3, Q.polynomialCardinal, 3))
          case Generic =>
            Render.mermaidCodeFence(Render.title(P.polynomialGeneric(labelS), 3, Q.polynomialGeneric((labelA, labelB)), 3))
          case Specific =>
            Render.mermaidCodeFence(Render.title(P.polynomialSpecific, 3, Q.polynomialSpecific, 3))
      def showTitleCustom[Y](labels: (String, String)): String =
        Render.mermaidCodeFence(Render.title(labels._1, 3, labels._2, 3))     
      def showTitleHtml(titleFmt: Format): String =
        titleFmt match
          case Cardinal =>
            Render.polyMap(P.graphPCardinal(P.polynomialCardinal), Q.graphQCardinal(P.graphPCardinal(P.polynomialCardinal)))
          case Generic =>
            Render.polyMap(P.graphPGeneric(P.polynomialGeneric(labelS), labelS), Q.graphQGeneric((labelA, labelB))(P.graphPGeneric(P.polynomialGeneric(labelS), labelS)))
          case Specific =>
            Render.polyMap(P.graphPSpecific(P.polynomialSpecific), Q.graphQSpecific(P.graphPSpecific(P.polynomialSpecific)))
      def showTitleHtmlCustom[Y](labels: (String, String)): String =
        Render.polyMap(labels._1, labels._2)
      def showTitledGraph(titleFmt: Format, graphFmt: Format): String =
        (titleFmt, graphFmt) match
          case (Cardinal, Cardinal) =>
            Render.mermaidCodeFence(Q.graphQCardinal(P.graphPCardinal(P.polynomialCardinal)))
              .addTitle(P.polynomialCardinal, 3, Q.polynomialCardinal, 3)
          case (Cardinal, Generic) =>
            Render.mermaidCodeFence(Q.graphQGeneric((labelA, labelB))(P.graphPGeneric(P.polynomialGeneric(labelS), labelS)))
              .addTitle(P.polynomialCardinal, 3, Q.polynomialCardinal, 3)
          case (Cardinal, Specific) =>
            Render.mermaidCodeFence(Q.graphQCardinal(P.graphPCardinal(P.polynomialCardinal)))
              .addTitle(P.polynomialSpecific, 3, Q.polynomialSpecific, 3)
          case (Generic, Cardinal) =>
            Render.mermaidCodeFence(Q.graphQCardinal(P.graphPCardinal(P.polynomialCardinal)))
              .addTitle(P.polynomialGeneric(labelS), 3, Q.polynomialGeneric((labelA, labelB)), 3)
          case (Generic, Generic) =>
            Render.mermaidCodeFence(Q.graphQGeneric((labelA, labelB))(P.graphPGeneric(P.polynomialGeneric(labelS), labelS)))
              .addTitle(P.polynomialGeneric(labelS), 3, Q.polynomialGeneric((labelA, labelB)), 3)
          case (Generic, Specific) =>
            Render.mermaidCodeFence(Q.graphQSpecific(P.graphPSpecific(P.polynomialSpecific)))
              .addTitle(P.polynomialGeneric(labelS), 3, Q.polynomialGeneric((labelA, labelB)), 3)
          case (Specific, Cardinal) =>
            Render.mermaidCodeFence(Q.graphQSpecific(P.graphPSpecific(P.polynomialSpecific)))
              .addTitle(P.polynomialSpecific, 3, Q.polynomialSpecific, 3)
          case (Specific, Generic) =>
            Render.mermaidCodeFence(Q.graphQGeneric((labelA, labelB))(P.graphPGeneric(P.polynomialGeneric(labelS), labelS)))
              .addTitle(P.polynomialSpecific, 3, Q.polynomialSpecific, 3)
          case (Specific, Specific) =>
            Render.mermaidCodeFence(Q.graphQSpecific(P.graphPSpecific(P.polynomialSpecific)))
              .addTitle(P.polynomialSpecific, 3, Q.polynomialSpecific, 3)
      def showTitledGraphCustom[Y](graphFmt: Format, labels: (String, String)): String =
        graphFmt match
          case Cardinal =>
            Render.mermaidCodeFence(Q.graphQCardinal(P.graphPCardinal(P.polynomialCardinal)))
              .addTitle(labels._1, 3, labels._2, 3)
          case Generic =>
            Render.mermaidCodeFence(Q.graphQGeneric((labelA, labelB))(P.graphPGeneric(labels._1, labelS)))
              .addTitle(labels._1, 3, labels._2, 3)
          case Specific =>
            Render.mermaidCodeFence(Q.graphQSpecific(P.graphPSpecific(P.polynomialSpecific)))
              .addTitle(labels._1, 3, labels._2, 3)

  given monoToMono[A1, B1, A2, B2](using
    P: MermaidP[Monomial.Interface[A1, B1, _]],
    Q: MermaidQ[Monomial.Interface[A2, B2, _]],
  ): Mermaid[PolyMap[Monomial.Interface[A1, B1, _], Monomial.Interface[A2, B2, _], _]] =
    new Mermaid[PolyMap[Monomial.Interface[A1, B1, _], Monomial.Interface[A2, B2, _], _]]:
      private val labelA1 = "A<sub>1</sub>"
      private val labelB1 = "B<sub>1</sub>"
      private val labelA2 = "A<sub>2</sub>"
      private val labelB2 = "B<sub>2</sub>"
      def showGraph(graphFmt: Format): String =
        graphFmt match
          case Cardinal =>
            Render.mermaidCodeFence(Q.graphQCardinal(P.graphPCardinal(P.polynomialCardinal)))
          case Generic =>
            Render.mermaidCodeFence(Q.graphQGeneric((labelA2, labelB2))(P.graphPGeneric(P.polynomialGeneric((labelA1, labelB1)), (labelA1, labelB1))))
          case Specific =>
            Render.mermaidCodeFence(Q.graphQSpecific(P.graphPSpecific(P.polynomialSpecific)))
      def showGraphCustom[Y](graphFmt: Format, labels: (String, String)): String =
        graphFmt match
          case Cardinal =>
            Render.mermaidCodeFence(Q.graphQCardinal(P.graphPCardinal(P.polynomialCardinal)))
          case Generic =>
            Render.mermaidCodeFence(Q.graphQGeneric((labelA2, labelB2))(P.graphPGeneric(labels._1, (labelA1, labelB1))))
          case Specific =>
            Render.mermaidCodeFence(Q.graphQSpecific(P.graphPSpecific(P.polynomialSpecific)))
      def showTitle(titleFmt: Format): String =
        titleFmt match
          case Cardinal =>
            Render.mermaidCodeFence(Render.title(P.polynomialCardinal, 4, Q.polynomialCardinal, 4))
          case Generic =>
            Render.mermaidCodeFence(Render.title(P.polynomialGeneric((labelA1, labelB1)), 4, Q.polynomialGeneric((labelA2, labelB2)), 4))
          case Specific =>
            Render.mermaidCodeFence(Render.title(P.polynomialSpecific, 4, Q.polynomialSpecific, 4))
      def showTitleCustom[Y](labels: (String, String)): String =
        Render.mermaidCodeFence(Render.title(labels._1, 4, labels._2, 4))     
      def showTitleHtml(titleFmt: Format): String =
        titleFmt match
          case Cardinal =>
            Render.polyMap(P.graphPCardinal(P.polynomialCardinal), Q.graphQCardinal(P.graphPCardinal(P.polynomialCardinal)))
          case Generic =>
            Render.polyMap(P.graphPGeneric(P.polynomialGeneric((labelA1, labelB1)), (labelA1, labelB1)), Q.graphQGeneric((labelA2, labelB2))(P.graphPGeneric(P.polynomialGeneric((labelA1, labelB1)), (labelA1, labelB1))))
          case Specific =>
            Render.polyMap(P.graphPSpecific(P.polynomialSpecific), Q.graphQSpecific(P.graphPSpecific(P.polynomialSpecific)))
      def showTitleHtmlCustom[Y](labels: (String, String)): String =
        Render.polyMap(labels._1, labels._2)
      def showTitledGraph(titleFmt: Format, graphFmt: Format): String =
        (titleFmt, graphFmt) match
          case (Cardinal, Cardinal) =>
            Render.mermaidCodeFence(Q.graphQCardinal(P.graphPCardinal(P.polynomialCardinal)))
              .addTitle(P.polynomialCardinal, 4, Q.polynomialCardinal, 4)
          case (Cardinal, Generic) =>
            Render.mermaidCodeFence(Q.graphQGeneric((labelA2, labelB2))(P.graphPGeneric(P.polynomialGeneric((labelA1, labelB1)), (labelA1, labelB1))))
              .addTitle(P.polynomialCardinal, 4, Q.polynomialCardinal, 4)
          case (Cardinal, Specific) =>
            Render.mermaidCodeFence(Q.graphQCardinal(P.graphPCardinal(P.polynomialCardinal)))
              .addTitle(P.polynomialSpecific, 4, Q.polynomialSpecific, 4)
          case (Generic, Cardinal) =>
            Render.mermaidCodeFence(Q.graphQCardinal(P.graphPCardinal(P.polynomialCardinal)))
              .addTitle(P.polynomialGeneric((labelA1, labelB1)), 4, Q.polynomialGeneric((labelA2, labelB2)), 4)
          case (Generic, Generic) =>
            Render.mermaidCodeFence(Q.graphQGeneric((labelA2, labelB2))(P.graphPGeneric(P.polynomialGeneric((labelA1, labelB1)), (labelA1, labelB1))))
              .addTitle(P.polynomialGeneric((labelA1, labelB1)), 4, Q.polynomialGeneric((labelA2, labelB2)), 4)
          case (Generic, Specific) =>
            Render.mermaidCodeFence(Q.graphQSpecific(P.graphPSpecific(P.polynomialSpecific)))
              .addTitle(P.polynomialGeneric((labelA1, labelB1)), 4, Q.polynomialGeneric((labelA2, labelB2)), 4)
          case (Specific, Cardinal) =>
            Render.mermaidCodeFence(Q.graphQSpecific(P.graphPSpecific(P.polynomialSpecific)))
              .addTitle(P.polynomialSpecific, 4, Q.polynomialSpecific, 4)
          case (Specific, Generic) =>
            Render.mermaidCodeFence(Q.graphQGeneric((labelA2, labelB2))(P.graphPGeneric(P.polynomialGeneric((labelA1, labelB1)), (labelA1, labelB1))))
              .addTitle(P.polynomialSpecific, 4, Q.polynomialSpecific, 4)
          case (Specific, Specific) =>
            Render.mermaidCodeFence(Q.graphQSpecific(P.graphPSpecific(P.polynomialSpecific)))
              .addTitle(P.polynomialSpecific, 4, Q.polynomialSpecific, 4)
      def showTitledGraphCustom[Y](graphFmt: Format, labels: (String, String)): String =
        graphFmt match
          case Cardinal =>
            Render.mermaidCodeFence(Q.graphQCardinal(P.graphPCardinal(P.polynomialCardinal)))
              .addTitle(labels._1, 4, labels._2, 4)
          case Generic =>
            Render.mermaidCodeFence(Q.graphQGeneric((labelA2, labelB2))(P.graphPGeneric(labels._1, (labelA1, labelB1))))
              .addTitle(labels._1, 4, labels._2, 4)
          case Specific =>
            Render.mermaidCodeFence(Q.graphQSpecific(P.graphPSpecific(P.polynomialSpecific)))
              .addTitle(labels._1, 4, labels._2, 4)
        
  given mooreStoreToBi[S, A1, B1, A2, B2](using
    P: MermaidP[Monomial.Store[S, _]],
    Q: MermaidQ[Binomial.Interface[A1, B1, A2, B2, _]],
  ): Mermaid[PolyMap[Monomial.Store[S, _], Binomial.Interface[A1, B1, A2, B2, _], _]] =
    new Mermaid[PolyMap[Monomial.Store[S, _], Binomial.Interface[A1, B1, A2, B2, _], _]]:
      private val labelS = "S"
      private val labelA1 = "A<sub>1</sub>"
      private val labelB1 = "B<sub>1</sub>"
      private val labelA2 = "A<sub>2</sub>"
      private val labelB2 = "B<sub>2</sub>"
      def showGraph(graphFmt: Format): String =
        graphFmt match
          case Cardinal =>
            Render.mermaidCodeFence(Q.graphQCardinal(P.graphPCardinal(P.polynomialCardinal)))
          case Generic =>
            Render.mermaidCodeFence(Q.graphQGeneric(((labelA1, labelB1), (labelA2, labelB2)))(P.graphPGeneric(P.polynomialGeneric(labelS), labelS)))
          case Specific =>
            Render.mermaidCodeFence(Q.graphQSpecific(P.graphPSpecific(P.polynomialSpecific)))
      def showGraphCustom[Y](graphFmt: Format, labels: (String, String)): String =
        graphFmt match
          case Cardinal =>
            Render.mermaidCodeFence(Q.graphQCardinal(P.graphPCardinal(P.polynomialCardinal)))
          case Generic =>
            Render.mermaidCodeFence(Q.graphQGeneric(((labelA1, labelB1), (labelA2, labelB2)))(P.graphPGeneric(P.polynomialGeneric(labelS), labelS)))
          case Specific =>
            Render.mermaidCodeFence(Q.graphQSpecific(P.graphPSpecific(P.polynomialSpecific)))
      def showTitle(titleFmt: Format): String =
        titleFmt match
          case Cardinal =>
            Render.mermaidCodeFence(Render.title(P.polynomialCardinal, 3, Q.polynomialCardinal, 3))
          case Generic =>
            Render.mermaidCodeFence(Render.title(P.polynomialGeneric(labelS), 3, Q.polynomialGeneric(((labelA1, labelB1), (labelA2, labelB2))), 3))
          case Specific =>
            Render.mermaidCodeFence(Render.title(P.polynomialSpecific, 3, Q.polynomialSpecific, 3))
      def showTitleCustom[Y](labels: (String, String)): String =
        Render.mermaidCodeFence(Render.title(labels._1, 3, labels._2, 3))     
      def showTitleHtml(titleFmt: Format): String =
        titleFmt match
          case Cardinal =>
            Render.polyMap(P.graphPCardinal(P.polynomialCardinal), Q.graphQCardinal(P.graphPCardinal(P.polynomialCardinal)))
          case Generic =>
            Render.polyMap(P.graphPGeneric(P.polynomialGeneric(labelS), labelS), Q.graphQGeneric(((labelA1, labelB1), (labelA2, labelB2)))(P.graphPGeneric(P.polynomialGeneric(labelS), labelS)))
          case Specific =>
            Render.polyMap(P.graphPSpecific(P.polynomialSpecific), Q.graphQSpecific(P.graphPSpecific(P.polynomialSpecific)))
      def showTitleHtmlCustom[Y](labels: (String, String)): String =
        Render.polyMap(labels._1, labels._2)
      def showTitledGraph(titleFmt: Format, graphFmt: Format): String =
        (titleFmt, graphFmt) match
          case (Cardinal, Cardinal) =>
            Render.mermaidCodeFence(Q.graphQCardinal(P.graphPCardinal(P.polynomialCardinal)))
              .addTitle(P.polynomialCardinal, 3, Q.polynomialCardinal, 3)
          case (Cardinal, Generic) =>
            Render.mermaidCodeFence(Q.graphQGeneric(((labelA1, labelB1), (labelA2, labelB2)))(P.graphPGeneric(P.polynomialGeneric(labelS), labelS)))
              .addTitle(P.polynomialCardinal, 3, Q.polynomialCardinal, 3)
          case (Cardinal, Specific) =>
            Render.mermaidCodeFence(Q.graphQCardinal(P.graphPCardinal(P.polynomialCardinal)))
              .addTitle(P.polynomialSpecific, 3, Q.polynomialSpecific, 3)
          case (Generic, Cardinal) =>
            Render.mermaidCodeFence(Q.graphQCardinal(P.graphPCardinal(P.polynomialCardinal)))
              .addTitle(P.polynomialGeneric(labelS), 3, Q.polynomialGeneric(((labelA1, labelB1), (labelA2, labelB2))), 3)
          case (Generic, Generic) =>
            Render.mermaidCodeFence(Q.graphQGeneric(((labelA1, labelB1), (labelA2, labelB2)))(P.graphPGeneric(P.polynomialGeneric(labelS), labelS)))
              .addTitle(P.polynomialGeneric(labelS), 3, Q.polynomialGeneric(((labelA1, labelB1), (labelA2, labelB2))), 3)
          case (Generic, Specific) =>
            Render.mermaidCodeFence(Q.graphQSpecific(P.graphPSpecific(P.polynomialSpecific)))
              .addTitle(P.polynomialGeneric(labelS), 3, Q.polynomialGeneric(((labelA1, labelB1), (labelA2, labelB2))), 3)
          case (Specific, Cardinal) =>
            Render.mermaidCodeFence(Q.graphQSpecific(P.graphPSpecific(P.polynomialSpecific)))
              .addTitle(P.polynomialSpecific, 3, Q.polynomialSpecific, 3)
          case (Specific, Generic) =>
            Render.mermaidCodeFence(Q.graphQGeneric(((labelA1, labelB1), (labelA2, labelB2)))(P.graphPGeneric(P.polynomialGeneric(labelS), labelS)))
              .addTitle(P.polynomialSpecific, 3, Q.polynomialSpecific, 3)
          case (Specific, Specific) =>
            Render.mermaidCodeFence(Q.graphQSpecific(P.graphPSpecific(P.polynomialSpecific)))
              .addTitle(P.polynomialSpecific, 3, Q.polynomialSpecific, 3)
      def showTitledGraphCustom[Y](graphFmt: Format, labels: (String, String)): String =
        graphFmt match
          case Cardinal =>
            Render.mermaidCodeFence(Q.graphQCardinal(P.graphPCardinal(P.polynomialCardinal)))
              .addTitle(labels._1, 3, labels._2, 3)
          case Generic =>
            Render.mermaidCodeFence(Q.graphQGeneric(((labelA1, labelB1), (labelA2, labelB2)))(P.graphPGeneric(P.polynomialGeneric(labelS), labelS)))
              .addTitle(labels._1, 3, labels._2, 3)
          case Specific =>
            Render.mermaidCodeFence(Q.graphQSpecific(P.graphPSpecific(P.polynomialSpecific)))
              .addTitle(labels._1, 3, labels._2, 3)
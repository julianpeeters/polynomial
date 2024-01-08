package polynomial.mermaid

import polynomial.mermaid.Mermaid.CustomLabels
import polynomial.mermaid.render.p.MermaidP
import polynomial.mermaid.render.q.MermaidQ
import polynomial.mermaid.render.Format.{Cardinal, Generic, Specific}
import polynomial.mermaid.render.{Render}
import polynomial.mermaid.render.addTitle
import polynomial.morphism.PolyMap
import polynomial.`object`.{Binomial, Monomial, Store}

trait Mermaid[F[_]]:
  def showGraph(graphFmt: Format): String
  def showGraphCustom(graphFmt: Format, labels: CustomLabels[F]): String
  def showTitle(titleFmt: Format): String
  def showTitleCustom(labels: CustomLabels[F]): String
  def showTitleHtml(titleFmt: Format): String
  def showTitleHtmlCustom(labels: CustomLabels[F]): String
  def showTitledGraph(titleFmt: Format, graphFmt: Format): String
  def showTitledGraphCustom(graphFmt: Format, labels: CustomLabels[F]): String

object Mermaid:

  type ParamLabels[P[_]] = P[Any] match
    case Monomial[a, b, Any] => (String, String)
    case Binomial[a1, b1, a2, b2, Any] => ((String, String), (String, String))
    case Store[s, Any] => String
    
  type PolynomialLabels[P[_]] = P[Any] match
    case Binomial[a1, b1, a2, b2, Any] => String
    case Monomial[a, b, Any] => String
    case Store[s, Any] => String

  type CustomLabels[P[_]] = P[Any] match
    case PolyMap[Binomial[a1, b1, a2, b2, _], Monomial[a3, b3, _], Any] => (String, String)
    case PolyMap[Monomial[a, b, _], Monomial[a3, b3, _], Any] => (String, String)
    case PolyMap[Store[s, _], Monomial[a3, b3, _], Any] => (String, String)
    case PolyMap[Store[s, _], Binomial[a1, b1, a2, b2, _], Any] => (String, String)

  given mooreStoreToMono[S, A, B](using
    P: MermaidP[Store[S, _]],
    Q: MermaidQ[Monomial[A, B, _]],
  ): Mermaid[PolyMap[Store[S, _], Monomial[A, B, _], _]] =
    new Mermaid[PolyMap[Store[S, _], Monomial[A, B, _], _]]:
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
      def showGraphCustom(graphFmt: Format, labels: (String, String)): String =
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
      def showTitleCustom(labels: (String, String)): String =
        Render.mermaidCodeFence(Render.title(labels._1, 3, labels._2, 3))     
      def showTitleHtml(titleFmt: Format): String =
        titleFmt match
          case Cardinal =>
            Render.polyMap(P.graphPCardinal(P.polynomialCardinal), Q.graphQCardinal(P.graphPCardinal(P.polynomialCardinal)))
          case Generic =>
            Render.polyMap(P.graphPGeneric(P.polynomialGeneric(labelS), labelS), Q.graphQGeneric((labelA, labelB))(P.graphPGeneric(P.polynomialGeneric(labelS), labelS)))
          case Specific =>
            Render.polyMap(P.graphPSpecific(P.polynomialSpecific), Q.graphQSpecific(P.graphPSpecific(P.polynomialSpecific)))
      def showTitleHtmlCustom(labels: (String, String)): String =
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
      def showTitledGraphCustom(graphFmt: Format, labels: (String, String)): String =
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
    P: MermaidP[Store[S, _]],
    Q: MermaidQ[Monomial[A, A => B, _]],
  ): Mermaid[PolyMap[Store[S, _], Monomial[A, A => B, _], _]] =
    new Mermaid[PolyMap[Store[S, _], Monomial[A, A => B, _], _]]:
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
      def showGraphCustom(graphFmt: Format, labels: (String, String)): String =
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
      def showTitleCustom(labels: (String, String)): String =
        Render.mermaidCodeFence(Render.title(labels._1, 3, labels._2, 3))     
      def showTitleHtml(titleFmt: Format): String =
        titleFmt match
          case Cardinal =>
            Render.polyMap(P.graphPCardinal(P.polynomialCardinal), Q.graphQCardinal(P.graphPCardinal(P.polynomialCardinal)))
          case Generic =>
            Render.polyMap(P.graphPGeneric(P.polynomialGeneric(labelS), labelS), Q.graphQGeneric((labelA, labelB))(P.graphPGeneric(P.polynomialGeneric(labelS), labelS)))
          case Specific =>
            Render.polyMap(P.graphPSpecific(P.polynomialSpecific), Q.graphQSpecific(P.graphPSpecific(P.polynomialSpecific)))
      def showTitleHtmlCustom(labels: (String, String)): String =
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
      def showTitledGraphCustom(graphFmt: Format, labels: (String, String)): String =
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
    P: MermaidP[Monomial[A1, B1, _]],
    Q: MermaidQ[Monomial[A2, B2, _]],
  ): Mermaid[PolyMap[Monomial[A1, B1, _], Monomial[A2, B2, _], _]] =
    new Mermaid[PolyMap[Monomial[A1, B1, _], Monomial[A2, B2, _], _]]:
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
      def showGraphCustom(graphFmt: Format, labels: (String, String)): String =
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
      def showTitleCustom(labels: (String, String)): String =
        Render.mermaidCodeFence(Render.title(labels._1, 4, labels._2, 4))     
      def showTitleHtml(titleFmt: Format): String =
        titleFmt match
          case Cardinal =>
            Render.polyMap(P.graphPCardinal(P.polynomialCardinal), Q.graphQCardinal(P.graphPCardinal(P.polynomialCardinal)))
          case Generic =>
            Render.polyMap(P.graphPGeneric(P.polynomialGeneric((labelA1, labelB1)), (labelA1, labelB1)), Q.graphQGeneric((labelA2, labelB2))(P.graphPGeneric(P.polynomialGeneric((labelA1, labelB1)), (labelA1, labelB1))))
          case Specific =>
            Render.polyMap(P.graphPSpecific(P.polynomialSpecific), Q.graphQSpecific(P.graphPSpecific(P.polynomialSpecific)))
      def showTitleHtmlCustom(labels: (String, String)): String =
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
      def showTitledGraphCustom(graphFmt: Format, labels: (String, String)): String =
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
    P: MermaidP[Store[S, _]],
    Q: MermaidQ[Binomial[A1, B1, A2, B2, _]],
  ): Mermaid[PolyMap[Store[S, _], Binomial[A1, B1, A2, B2, _], _]] =
    new Mermaid[PolyMap[Store[S, _], Binomial[A1, B1, A2, B2, _], _]]:
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
      def showGraphCustom(graphFmt: Format, labels: (String, String)): String =
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
      def showTitleCustom(labels: (String, String)): String =
        Render.mermaidCodeFence(Render.title(labels._1, 3, labels._2, 3))     
      def showTitleHtml(titleFmt: Format): String =
        titleFmt match
          case Cardinal =>
            Render.polyMap(P.graphPCardinal(P.polynomialCardinal), Q.graphQCardinal(P.graphPCardinal(P.polynomialCardinal)))
          case Generic =>
            Render.polyMap(P.graphPGeneric(P.polynomialGeneric(labelS), labelS), Q.graphQGeneric(((labelA1, labelB1), (labelA2, labelB2)))(P.graphPGeneric(P.polynomialGeneric(labelS), labelS)))
          case Specific =>
            Render.polyMap(P.graphPSpecific(P.polynomialSpecific), Q.graphQSpecific(P.graphPSpecific(P.polynomialSpecific)))
      def showTitleHtmlCustom(labels: (String, String)): String =
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
      def showTitledGraphCustom(graphFmt: Format, labels: (String, String)): String =
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
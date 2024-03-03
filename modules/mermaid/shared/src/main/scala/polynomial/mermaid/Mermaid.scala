package polynomial.mermaid

import polynomial.mermaid.Mermaid.CustomLabels
import polynomial.mermaid.render.p.MermaidP
import polynomial.mermaid.render.q.MermaidQ
import polynomial.mermaid.render.Format.{Cardinal, Generic, Specific}
import polynomial.mermaid.render.{Render, addTitle}
import polynomial.morphism.PolyMap
import polynomial.`object`.Binomial.BiInterface
import polynomial.`object`.Monomial.{Interface, Store}
import polynomial.product.{Tensor, ⊗}

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
    case (Interface[a1, b1, y1], Interface[a2, b2, y2]) => (String, String)
    case (Store[s, y1], BiInterface[a1, b1, a2, b2, y2]) => (String, String)
    case (Store[s, y1], Interface[a, b, y2]) => (String, String)
    case (Store[s1, y1], Store[s2, y2]) => (String, String)
    case (Tensor[o, p, y1], Interface[q, r, y2]) => (CustomLabels[(o[y1], p[y1])], String)
    case (Tensor[o, p, y1], Tensor[q, r, y2]) => (CustomLabels[(o[y1], p[y1])], CustomLabels[(q[y2], r[y2])])

  type ParamLabels[X] = X match
    case Interface[a, b, y] => (String, String)
    case BiInterface[a1, b1, a2, b2, y] => ((String, String), (String, String))
    case Store[s, y] => String
    
  type PolynomialLabels[X] = X match
    case BiInterface[a1, b1, a2, b2, y] => String
    case Interface[a, b, y] => String
    case Store[s, y] => String

  given mooreStoreToMono[S, A, B](using
    P: MermaidP[Store[S, _]],
    Q: MermaidQ[Interface[A, B, _]],
  ): Mermaid[PolyMap[Store[S, _], Interface[A, B, _], _]] =
    new Mermaid[PolyMap[Store[S, _], Interface[A, B, _], _]]:
      private val labelS: String = "S"
      private val labelA: String = "A"
      private val labelB: String = "B"
      def showGraph(graphFmt: Format): String =
        graphFmt match
          case Cardinal =>
            Render.mermaidCodeFence(Q.graphQCardinal((labelA, labelB))(P.graphPCardinal(labelS, P.polynomialCardinal)))
          case Generic =>
            Render.mermaidCodeFence(Q.graphQGeneric((labelA, labelB), (labelA, labelB))(P.graphPGeneric(labelS, P.polynomialGeneric(labelS), labelS)))
          case Specific =>
            Render.mermaidCodeFence(Q.graphQSpecific((labelA, labelB))(P.graphPSpecific(labelS, P.polynomialSpecific)))
      def showGraphCustom[Y](graphFmt: Format, labels: (String, String)): String =
        graphFmt match
          case Cardinal =>
            Render.mermaidCodeFence(Q.graphQCardinal((labelA, labelB))(P.graphPCardinal(labelS, P.polynomialCardinal)))
          case Generic =>
            Render.mermaidCodeFence(Q.graphQGeneric((labelA, labelB), (labelA, labelB))(P.graphPGeneric(labelS, P.polynomialGeneric(labelS), labelS)))
          case Specific =>
            Render.mermaidCodeFence(Q.graphQSpecific((labelA, labelB))(P.graphPSpecific(labelS, P.polynomialSpecific)))
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
            Render.polyMap(P.graphPCardinal(labelS, P.polynomialCardinal), Q.graphQCardinal((labelA, labelB))(P.graphPCardinal(labelS, P.polynomialCardinal)))
          case Generic =>
            Render.polyMap(P.graphPGeneric(labelS, P.polynomialGeneric(labelS), labelS), Q.graphQGeneric((labelA, labelB), (labelA, labelB))(P.graphPGeneric(labelS, P.polynomialGeneric(labelS), labelS)))
          case Specific =>
            Render.polyMap(P.graphPSpecific(labelS, P.polynomialSpecific), Q.graphQSpecific((labelA, labelB))(P.graphPSpecific(labelS, P.polynomialSpecific)))
      def showTitleHtmlCustom[Y](labels: (String, String)): String =
        Render.polyMap(labels._1, labels._2)
      def showTitledGraph(titleFmt: Format, graphFmt: Format): String =
        (titleFmt, graphFmt) match
          case (Cardinal, Cardinal) =>
            Render.mermaidCodeFence(Q.graphQCardinal((labelA, labelB))(P.graphPCardinal(labelS, P.polynomialCardinal)))
              .addTitle(P.polynomialCardinal, 3, Q.polynomialCardinal, 3)
          case (Cardinal, Generic) =>
            Render.mermaidCodeFence(Q.graphQGeneric((labelA, labelB), (labelA, labelB))(P.graphPGeneric(labelS, P.polynomialGeneric(labelS), labelS)))
              .addTitle(P.polynomialCardinal, 3, Q.polynomialCardinal, 3)
          case (Cardinal, Specific) =>
            Render.mermaidCodeFence(Q.graphQCardinal((labelA, labelB))(P.graphPCardinal(labelS, P.polynomialCardinal)))
              .addTitle(P.polynomialSpecific, 3, Q.polynomialSpecific, 3)
          case (Generic, Cardinal) =>
            Render.mermaidCodeFence(Q.graphQCardinal((labelA, labelB))(P.graphPCardinal(labelS, P.polynomialCardinal)))
              .addTitle(P.polynomialGeneric(labelS), 3, Q.polynomialGeneric((labelA, labelB)), 3)
          case (Generic, Generic) =>
            Render.mermaidCodeFence(Q.graphQGeneric((labelA, labelB), (labelA, labelB))(P.graphPGeneric(labelS, P.polynomialGeneric(labelS), labelS)))
              .addTitle(P.polynomialGeneric(labelS), 3, Q.polynomialGeneric((labelA, labelB)), 3)
          case (Generic, Specific) =>
            Render.mermaidCodeFence(Q.graphQSpecific((labelA, labelB))(P.graphPSpecific(labelS, P.polynomialSpecific)))
              .addTitle(P.polynomialGeneric(labelS), 3, Q.polynomialGeneric((labelA, labelB)), 3)
          case (Specific, Cardinal) =>
            Render.mermaidCodeFence(Q.graphQSpecific((labelA, labelB))(P.graphPSpecific(labelS, P.polynomialSpecific)))
              .addTitle(P.polynomialSpecific, 3, Q.polynomialSpecific, 3)
          case (Specific, Generic) =>
            Render.mermaidCodeFence(Q.graphQGeneric((labelA, labelB), (labelA, labelB))(P.graphPGeneric(labelS, P.polynomialGeneric(labelS), labelS)))
              .addTitle(P.polynomialSpecific, 3, Q.polynomialSpecific, 3)
          case (Specific, Specific) =>
            Render.mermaidCodeFence(Q.graphQSpecific((labelA, labelB))(P.graphPSpecific(labelS, P.polynomialSpecific)))
              .addTitle(P.polynomialSpecific, 3, Q.polynomialSpecific, 3)
      def showTitledGraphCustom[Y](graphFmt: Format, labels: (String, String)): String =
        graphFmt match
          case Cardinal =>
            Render.mermaidCodeFence(Q.graphQCardinal((labelA, labelB))(P.graphPCardinal(labelS, P.polynomialCardinal)))
              .addTitle(labels._1, 3, labels._2, 3)
          case Generic =>
            Render.mermaidCodeFence(Q.graphQGeneric((labelA, labelB), (labelA, labelB))(P.graphPGeneric(labelS, P.polynomialGeneric(labelS), labelS)))
              .addTitle(labels._1, 3, labels._2, 3)
          case Specific =>
            Render.mermaidCodeFence(Q.graphQSpecific((labelA, labelB))(P.graphPSpecific(labelS, P.polynomialSpecific)))
              .addTitle(labels._1, 3, labels._2, 3)
        
  given mealyStoreToMono[S, A, B](using
    P: MermaidP[Store[S, _]],
    Q: MermaidQ[Interface[A, A => B, _]],
  ): Mermaid[PolyMap[Store[S, _], Interface[A, A => B, _], _]] =
    new Mermaid[PolyMap[Store[S, _], Interface[A, A => B, _], _]]:
      private val labelS = "S"
      private val labelA = "A"
      private val labelB = "B"
      def showGraph(graphFmt: Format): String =
        graphFmt match
          case Cardinal =>
            Render.mermaidCodeFence(Q.graphQCardinal((labelA, labelB))(P.graphPCardinal(labelS, P.polynomialCardinal)))
          case Generic =>
            Render.mermaidCodeFence(Q.graphQGeneric((labelA, labelB), (labelA, labelB))(P.graphPGeneric(labelS, P.polynomialGeneric(labelS), labelS)))
          case Specific =>
            Render.mermaidCodeFence(Q.graphQSpecific((labelA, labelB))(P.graphPSpecific(labelS, P.polynomialSpecific)))
      def showGraphCustom[Y](graphFmt: Format, labels: (String, String)): String =
        graphFmt match
          case Cardinal =>
            Render.mermaidCodeFence(Q.graphQCardinal((labelA, labelB))(P.graphPCardinal(labelS, P.polynomialCardinal)))
          case Generic =>
            Render.mermaidCodeFence(Q.graphQGeneric((labelA, labelB), (labelA, labelB))(P.graphPGeneric(labelS, labels._1, labelS)))
          case Specific =>
            Render.mermaidCodeFence(Q.graphQSpecific((labelA, labelB))(P.graphPSpecific(labelS, P.polynomialSpecific)))
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
            Render.polyMap(P.graphPCardinal(labelS, P.polynomialCardinal), Q.graphQCardinal((labelA, labelB))(P.graphPCardinal(labelS, P.polynomialCardinal)))
          case Generic =>
            Render.polyMap(P.graphPGeneric(labelS, P.polynomialGeneric(labelS), labelS), Q.graphQGeneric((labelA, labelB), (labelA, labelB))(P.graphPGeneric(labelS, P.polynomialGeneric(labelS), labelS)))
          case Specific =>
            Render.polyMap(P.graphPSpecific(labelS, P.polynomialSpecific), Q.graphQSpecific((labelA, labelB))(P.graphPSpecific(labelS, P.polynomialSpecific)))
      def showTitleHtmlCustom[Y](labels: (String, String)): String =
        Render.polyMap(labels._1, labels._2)
      def showTitledGraph(titleFmt: Format, graphFmt: Format): String =
        (titleFmt, graphFmt) match
          case (Cardinal, Cardinal) =>
            Render.mermaidCodeFence(Q.graphQCardinal((labelA, labelB))(P.graphPCardinal(labelS, P.polynomialCardinal)))
              .addTitle(P.polynomialCardinal, 3, Q.polynomialCardinal, 3)
          case (Cardinal, Generic) =>
            Render.mermaidCodeFence(Q.graphQGeneric((labelA, labelB), (labelA, labelB))(P.graphPGeneric(labelS, P.polynomialGeneric(labelS), labelS)))
              .addTitle(P.polynomialCardinal, 3, Q.polynomialCardinal, 3)
          case (Cardinal, Specific) =>
            Render.mermaidCodeFence(Q.graphQCardinal((labelA, labelB))(P.graphPCardinal(labelS, P.polynomialCardinal)))
              .addTitle(P.polynomialSpecific, 3, Q.polynomialSpecific, 3)
          case (Generic, Cardinal) =>
            Render.mermaidCodeFence(Q.graphQCardinal((labelA, labelB))(P.graphPCardinal(labelS, P.polynomialCardinal)))
              .addTitle(P.polynomialGeneric(labelS), 3, Q.polynomialGeneric((labelA, labelB)), 3)
          case (Generic, Generic) =>
            Render.mermaidCodeFence(Q.graphQGeneric((labelA, labelB), (labelA, labelB))(P.graphPGeneric(labelS, P.polynomialGeneric(labelS), labelS)))
              .addTitle(P.polynomialGeneric(labelS), 3, Q.polynomialGeneric((labelA, labelB)), 3)
          case (Generic, Specific) =>
            Render.mermaidCodeFence(Q.graphQSpecific((labelA, labelB))(P.graphPSpecific(labelS, P.polynomialSpecific)))
              .addTitle(P.polynomialGeneric(labelS), 3, Q.polynomialGeneric((labelA, labelB)), 3)
          case (Specific, Cardinal) =>
            Render.mermaidCodeFence(Q.graphQSpecific((labelA, labelB))(P.graphPSpecific(labelS, P.polynomialSpecific)))
              .addTitle(P.polynomialSpecific, 3, Q.polynomialSpecific, 3)
          case (Specific, Generic) =>
            Render.mermaidCodeFence(Q.graphQGeneric((labelA, labelB), (labelA, labelB))(P.graphPGeneric(labelS, P.polynomialGeneric(labelS), labelS)))
              .addTitle(P.polynomialSpecific, 3, Q.polynomialSpecific, 3)
          case (Specific, Specific) =>
            Render.mermaidCodeFence(Q.graphQSpecific((labelA, labelB))(P.graphPSpecific(labelS, P.polynomialSpecific)))
              .addTitle(P.polynomialSpecific, 3, Q.polynomialSpecific, 3)
      def showTitledGraphCustom[Y](graphFmt: Format, labels: (String, String)): String =
        graphFmt match
          case Cardinal =>
            Render.mermaidCodeFence(Q.graphQCardinal((labelA, labelB))(P.graphPCardinal(labelS, P.polynomialCardinal)))
              .addTitle(labels._1, 3, labels._2, 3)
          case Generic =>
            Render.mermaidCodeFence(Q.graphQGeneric((labelA, labelB), (labelA, labelB))(P.graphPGeneric(labelS, labels._1, labelS)))
              .addTitle(labels._1, 3, labels._2, 3)
          case Specific =>
            Render.mermaidCodeFence(Q.graphQSpecific((labelA, labelB))(P.graphPSpecific(labelS, P.polynomialSpecific)))
              .addTitle(labels._1, 3, labels._2, 3)

  given monoToMono[A1, B1, A2, B2](using
    P: MermaidP[Interface[A1, B1, _]],
    Q: MermaidQ[Interface[A2, B2, _]],
  ): Mermaid[PolyMap[Interface[A1, B1, _], Interface[A2, B2, _], _]] =
    new Mermaid[PolyMap[Interface[A1, B1, _], Interface[A2, B2, _], _]]:
      private val labelA1 = "A<sub>1</sub>"
      private val labelB1 = "B<sub>1</sub>"
      private val labelA2 = "A<sub>2</sub>"
      private val labelB2 = "B<sub>2</sub>"
      private val nodeA1 = "A1"
      // private val nodeA2 = "A2"
      private val nodeB1 = "B1"
      // private val nodeB2 = "B2"
      def showGraph(graphFmt: Format): String =
        graphFmt match
          case Cardinal =>
            Render.mermaidCodeFence(Q.graphQCardinal((nodeA1, nodeB1))(P.graphPCardinal(nodeB1, P.polynomialCardinal)))
          case Generic =>
            Render.mermaidCodeFence(Q.graphQGeneric((nodeA1, nodeB1), (labelA2, labelB2))(P.graphPGeneric(nodeB1, P.polynomialGeneric((labelA1, labelB1)), (labelA1, labelB1))))
          case Specific =>
            Render.mermaidCodeFence(Q.graphQSpecific((nodeA1, nodeB1))(P.graphPSpecific(nodeB1, P.polynomialSpecific)))
      def showGraphCustom[Y](graphFmt: Format, labels: (String, String)): String =
        graphFmt match
          case Cardinal =>
            Render.mermaidCodeFence(Q.graphQCardinal((nodeA1, nodeB1))(P.graphPCardinal(nodeB1, P.polynomialCardinal)))
          case Generic =>
            Render.mermaidCodeFence(Q.graphQGeneric((nodeA1, nodeB1), (labelA2, labelB2))(P.graphPGeneric(nodeB1, labels._1, (labelA1, labelB1))))
          case Specific =>
            Render.mermaidCodeFence(Q.graphQSpecific((nodeA1, nodeB1))(P.graphPSpecific(nodeB1, P.polynomialSpecific)))
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
            Render.polyMap(P.graphPCardinal(nodeB1, P.polynomialCardinal), Q.graphQCardinal((nodeA1, nodeB1))(P.graphPCardinal(nodeB1, P.polynomialCardinal)))
          case Generic =>
            Render.polyMap(P.graphPGeneric(nodeB1, P.polynomialGeneric((labelA1, labelB1)), (labelA1, labelB1)), Q.graphQGeneric((nodeA1, nodeB1), (labelA2, labelB2))(P.graphPGeneric(nodeB1, P.polynomialGeneric((labelA1, labelB1)), (labelA1, labelB1))))
          case Specific =>
            Render.polyMap(P.graphPSpecific(nodeB1, P.polynomialSpecific), Q.graphQSpecific((nodeA1, nodeB1))(P.graphPSpecific(nodeB1, P.polynomialSpecific)))
      def showTitleHtmlCustom[Y](labels: (String, String)): String =
        Render.polyMap(labels._1, labels._2)
      def showTitledGraph(titleFmt: Format, graphFmt: Format): String =
        (titleFmt, graphFmt) match
          case (Cardinal, Cardinal) =>
            Render.mermaidCodeFence(Q.graphQCardinal((nodeA1, nodeB1))(P.graphPCardinal(nodeB1, P.polynomialCardinal)))
              .addTitle(P.polynomialCardinal, 4, Q.polynomialCardinal, 4)
          case (Cardinal, Generic) =>
            Render.mermaidCodeFence(Q.graphQGeneric((nodeA1, nodeB1), (labelA2, labelB2))(P.graphPGeneric(nodeB1, P.polynomialGeneric((labelA1, labelB1)), (labelA1, labelB1))))
              .addTitle(P.polynomialCardinal, 4, Q.polynomialCardinal, 4)
          case (Cardinal, Specific) =>
            Render.mermaidCodeFence(Q.graphQCardinal((nodeA1, nodeB1))(P.graphPCardinal(nodeB1, P.polynomialCardinal)))
              .addTitle(P.polynomialSpecific, 4, Q.polynomialSpecific, 4)
          case (Generic, Cardinal) =>
            Render.mermaidCodeFence(Q.graphQCardinal((nodeA1, nodeB1))(P.graphPCardinal(nodeB1, P.polynomialCardinal)))
              .addTitle(P.polynomialGeneric((labelA1, labelB1)), 4, Q.polynomialGeneric((labelA2, labelB2)), 4)
          case (Generic, Generic) =>
            Render.mermaidCodeFence(Q.graphQGeneric((nodeA1, nodeB1), (labelA2, labelB2))(P.graphPGeneric(nodeB1, P.polynomialGeneric((labelA1, labelB1)), (labelA1, labelB1))))
              .addTitle(P.polynomialGeneric((labelA1, labelB1)), 4, Q.polynomialGeneric((labelA2, labelB2)), 4)
          case (Generic, Specific) =>
            Render.mermaidCodeFence(Q.graphQSpecific((nodeA1, nodeB1))(P.graphPSpecific(nodeB1, P.polynomialSpecific)))
              .addTitle(P.polynomialGeneric((labelA1, labelB1)), 4, Q.polynomialGeneric((labelA2, labelB2)), 4)
          case (Specific, Cardinal) =>
            Render.mermaidCodeFence(Q.graphQSpecific((nodeA1, nodeB1))(P.graphPSpecific(nodeB1, P.polynomialSpecific)))
              .addTitle(P.polynomialSpecific, 4, Q.polynomialSpecific, 4)
          case (Specific, Generic) =>
            Render.mermaidCodeFence(Q.graphQGeneric((nodeA1, nodeB1), (labelA2, labelB2))(P.graphPGeneric(nodeB1, P.polynomialGeneric((labelA1, labelB1)), (labelA1, labelB1))))
              .addTitle(P.polynomialSpecific, 4, Q.polynomialSpecific, 4)
          case (Specific, Specific) =>
            Render.mermaidCodeFence(Q.graphQSpecific((nodeA1, nodeB1))(P.graphPSpecific(nodeB1, P.polynomialSpecific)))
              .addTitle(P.polynomialSpecific, 4, Q.polynomialSpecific, 4)
      def showTitledGraphCustom[Y](graphFmt: Format, labels: (String, String)): String =
        graphFmt match
          case Cardinal =>
            Render.mermaidCodeFence(Q.graphQCardinal((nodeA1, nodeB1))(P.graphPCardinal(nodeB1, P.polynomialCardinal)))
              .addTitle(labels._1, 4, labels._2, 4)
          case Generic =>
            Render.mermaidCodeFence(Q.graphQGeneric((nodeA1, nodeB1), (labelA2, labelB2))(P.graphPGeneric(nodeB1, labels._1, (labelA1, labelB1))))
              .addTitle(labels._1, 4, labels._2, 4)
          case Specific =>
            Render.mermaidCodeFence(Q.graphQSpecific((nodeA1, nodeB1))(P.graphPSpecific(nodeB1, P.polynomialSpecific)))
              .addTitle(labels._1, 4, labels._2, 4)
        
  given mooreStoreToBi[S, A1, B1, A2, B2](using
    P: MermaidP[Store[S, _]],
    Q: MermaidQ[BiInterface[A1, B1, A2, B2, _]],
  ): Mermaid[PolyMap[Store[S, _], BiInterface[A1, B1, A2, B2, _], _]] =
    new Mermaid[PolyMap[Store[S, _], BiInterface[A1, B1, A2, B2, _], _]]:
      private val labelS = "S"
      private val labelA1 = "A<sub>1</sub>"
      private val labelB1 = "B<sub>1</sub>"
      private val labelA2 = "A<sub>2</sub>"
      private val labelB2 = "B<sub>2</sub>"
      private val nodeA1 = "A1"
      private val nodeA2 = "A2"
      // private val nodeB1 = "B1"
      // private val nodeB2 = "B2"
      def showGraph(graphFmt: Format): String =
        graphFmt match
          case Cardinal =>
            Render.mermaidCodeFence(Q.graphQCardinal((nodeA1, nodeA2))(P.graphPCardinal(labelS, P.polynomialCardinal)))
          case Generic =>
            Render.mermaidCodeFence(Q.graphQGeneric((nodeA1, nodeA2), ((labelA1, labelB1), (labelA2, labelB2)))(P.graphPGeneric(labelS, P.polynomialGeneric(labelS), labelS)))
          case Specific =>
            Render.mermaidCodeFence(Q.graphQSpecific((nodeA1, nodeA2))(P.graphPSpecific(labelS, P.polynomialSpecific)))
      def showGraphCustom[Y](graphFmt: Format, labels: (String, String)): String =
        graphFmt match
          case Cardinal =>
            Render.mermaidCodeFence(Q.graphQCardinal((nodeA1, nodeA2))(P.graphPCardinal(labelS, P.polynomialCardinal)))
          case Generic =>
            Render.mermaidCodeFence(Q.graphQGeneric((nodeA1, nodeA2), ((labelA1, labelB1), (labelA2, labelB2)))(P.graphPGeneric(labelS, P.polynomialGeneric(labelS), labelS)))
          case Specific =>
            Render.mermaidCodeFence(Q.graphQSpecific((nodeA1, nodeA2))(P.graphPSpecific(labelS, P.polynomialSpecific)))
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
            Render.polyMap(P.graphPCardinal(labelS, P.polynomialCardinal), Q.graphQCardinal((nodeA1, nodeA2))(P.graphPCardinal(labelS, P.polynomialCardinal)))
          case Generic =>
            Render.polyMap(P.graphPGeneric(labelS, P.polynomialGeneric(labelS), labelS), Q.graphQGeneric((nodeA1, nodeA2), ((labelA1, labelB1), (labelA2, labelB2)))(P.graphPGeneric(labelS, P.polynomialGeneric(labelS), labelS)))
          case Specific =>
            Render.polyMap(P.graphPSpecific(labelS, P.polynomialSpecific), Q.graphQSpecific((nodeA1, nodeA2))(P.graphPSpecific(labelS, P.polynomialSpecific)))
      def showTitleHtmlCustom[Y](labels: (String, String)): String =
        Render.polyMap(labels._1, labels._2)
      def showTitledGraph(titleFmt: Format, graphFmt: Format): String =
        (titleFmt, graphFmt) match
          case (Cardinal, Cardinal) =>
            Render.mermaidCodeFence(Q.graphQCardinal((nodeA1, nodeA2))(P.graphPCardinal(labelS, P.polynomialCardinal)))
              .addTitle(P.polynomialCardinal, 3, Q.polynomialCardinal, 3)
          case (Cardinal, Generic) =>
            Render.mermaidCodeFence(Q.graphQGeneric((nodeA1, nodeA2), ((labelA1, labelB1), (labelA2, labelB2)))(P.graphPGeneric(labelS, P.polynomialGeneric(labelS), labelS)))
              .addTitle(P.polynomialCardinal, 3, Q.polynomialCardinal, 3)
          case (Cardinal, Specific) =>
            Render.mermaidCodeFence(Q.graphQCardinal((nodeA1, nodeA2))(P.graphPCardinal(labelS, P.polynomialCardinal)))
              .addTitle(P.polynomialSpecific, 3, Q.polynomialSpecific, 3)
          case (Generic, Cardinal) =>
            Render.mermaidCodeFence(Q.graphQCardinal((nodeA1, nodeA2))(P.graphPCardinal(labelS, P.polynomialCardinal)))
              .addTitle(P.polynomialGeneric(labelS), 3, Q.polynomialGeneric(((labelA1, labelB1), (labelA2, labelB2))), 3)
          case (Generic, Generic) =>
            Render.mermaidCodeFence(Q.graphQGeneric((nodeA1, nodeA2), ((labelA1, labelB1), (labelA2, labelB2)))(P.graphPGeneric(labelS, P.polynomialGeneric(labelS), labelS)))
              .addTitle(P.polynomialGeneric(labelS), 3, Q.polynomialGeneric(((labelA1, labelB1), (labelA2, labelB2))), 3)
          case (Generic, Specific) =>
            Render.mermaidCodeFence(Q.graphQSpecific((nodeA1, nodeA2))(P.graphPSpecific(labelS, P.polynomialSpecific)))
              .addTitle(P.polynomialGeneric(labelS), 3, Q.polynomialGeneric(((labelA1, labelB1), (labelA2, labelB2))), 3)
          case (Specific, Cardinal) =>
            Render.mermaidCodeFence(Q.graphQSpecific((nodeA1, nodeA2))(P.graphPSpecific(labelS, P.polynomialSpecific)))
              .addTitle(P.polynomialSpecific, 3, Q.polynomialSpecific, 3)
          case (Specific, Generic) =>
            Render.mermaidCodeFence(Q.graphQGeneric((nodeA1, nodeA2), ((labelA1, labelB1), (labelA2, labelB2)))(P.graphPGeneric(labelS, P.polynomialGeneric(labelS), labelS)))
              .addTitle(P.polynomialSpecific, 3, Q.polynomialSpecific, 3)
          case (Specific, Specific) =>
            Render.mermaidCodeFence(Q.graphQSpecific((nodeA1, nodeA2))(P.graphPSpecific(labelS, P.polynomialSpecific)))
              .addTitle(P.polynomialSpecific, 3, Q.polynomialSpecific, 3)
      def showTitledGraphCustom[Y](graphFmt: Format, labels: (String, String)): String =
        graphFmt match
          case Cardinal =>
            Render.mermaidCodeFence(Q.graphQCardinal((nodeA1, nodeA2))(P.graphPCardinal(labelS, P.polynomialCardinal)))
              .addTitle(labels._1, 3, labels._2, 3)
          case Generic =>
            Render.mermaidCodeFence(Q.graphQGeneric((nodeA1, nodeA2), ((labelA1, labelB1), (labelA2, labelB2)))(P.graphPGeneric(labelS, P.polynomialGeneric(labelS), labelS)))
              .addTitle(labels._1, 3, labels._2, 3)
          case Specific =>
            Render.mermaidCodeFence(Q.graphQSpecific((nodeA1, nodeA2))(P.graphPSpecific(labelS, P.polynomialSpecific)))
              .addTitle(labels._1, 3, labels._2, 3)

  given mooreTensoredStoreToTensoredMonomial[S1, S2, A1, B1, A2, B2](using
    P1: MermaidP[Store[S1, _]],
    P2: MermaidP[Store[S2, _]],
    Q1: MermaidQ[Interface[A1, B1, _]],
    Q2: MermaidQ[Interface[A2, B2, _]],
  ): Mermaid[PolyMap[Store[S1, _] ⊗ Store[S2, _], Interface[A1, B1, _] ⊗ Interface[A2, B2, _], _]] =
    new Mermaid[PolyMap[Store[S1, _] ⊗ Store[S2, _], Interface[A1, B1, _] ⊗ Interface[A2, B2, _], _]]:
      private val labelS1 = "S<sub>1</sub>"
      private val labelS2 = "S<sub>2</sub>"
      private val labelA1 = "A<sub>1</sub>"
      private val labelA2 = "A<sub>2</sub>"
      private val labelB1 = "B<sub>1</sub>"
      private val labelB2 = "B<sub>2</sub>"
      private val nodeS1 = "S1"
      private val nodeS2 = "S2"
      private val nodeA1 = "A1"
      private val nodeA2 = "A2"
      private val nodeB1 = "B1"
      private val nodeB2 = "B2"
      def showGraph(graphFmt: Format): String =
        graphFmt match
          case Cardinal =>
            Render.mermaidCodeFence(Render.tensored(Q1.graphQCardinal((nodeA1, nodeB1))(P1.graphPCardinal(nodeS1, P1.polynomialCardinal)), Q2.graphQCardinal((nodeA2, nodeB2))(P2.graphPCardinal(nodeS2, P2.polynomialCardinal))))
          case Generic =>
            Render.mermaidCodeFence(Render.tensored(Q1.graphQGeneric((nodeA1, nodeB1), (labelA1, labelB1))(P1.graphPGeneric(nodeS1, P1.polynomialGeneric(labelS1), labelS1)), Q2.graphQGeneric((nodeA2, nodeB2), (labelA2, labelB2))(P2.graphPGeneric(nodeS2, P2.polynomialGeneric(labelS2), labelS2))))
          case Specific =>
            Render.mermaidCodeFence(Render.tensored(Q1.graphQSpecific((nodeA1, nodeB1))(P1.graphPSpecific(nodeS1, P1.polynomialSpecific)), Q2.graphQSpecific((nodeA2, nodeB2))(P2.graphPSpecific(nodeS2, P2.polynomialSpecific))))
      def showGraphCustom[Y](graphFmt: Format, labels: ((String, String), (String, String))): String =
        ???
      def showTitle(titleFmt: Format): String =
        ???
      def showTitleCustom[Y](labels: ((String, String), (String, String))): String =
        ???
      def showTitleHtml(titleFmt: Format): String =
        ???
      def showTitleHtmlCustom[Y](labels: ((String, String), (String, String))): String =
        ???
      def showTitledGraph(titleFmt: Format, graphFmt: Format): String =
        graphFmt match
          case Cardinal =>
            Render.mermaidCodeFence(Render.tensored(Q1.graphQCardinal((nodeA1, nodeB1))(P1.graphPCardinal(nodeS1, P1.polynomialCardinal)), Q2.graphQCardinal((nodeA2, nodeB2))(P2.graphPCardinal(nodeS2, P2.polynomialCardinal))))
              .addTitle(Render.tensor(P1.polynomialCardinal, P2.polynomialCardinal), 3, Render.tensor(Q1.polynomialCardinal, Q2.polynomialCardinal), 3)
          case Generic =>
            Render.mermaidCodeFence(Render.tensored(Q1.graphQGeneric((nodeA1, nodeB1), (labelA1, labelB1))(P1.graphPGeneric(nodeS1, P1.polynomialGeneric(labelS1), labelS1)), Q2.graphQGeneric((nodeA2, nodeB2), (labelA2, labelB2))(P2.graphPGeneric(nodeS2, P2.polynomialGeneric(labelS2), labelS2))))
              .addTitle(Render.tensor(P1.polynomialGeneric(labelS1), P2.polynomialGeneric(labelS2)), 3, Render.tensor(Q1.polynomialGeneric((labelA1, labelB1)), Q2.polynomialGeneric((labelA2, labelB2))), 3)
          case Specific =>
            Render.mermaidCodeFence(Render.tensored(Q1.graphQSpecific((nodeA1, nodeB1))(P1.graphPSpecific(nodeS1, P1.polynomialSpecific)), Q2.graphQSpecific((nodeA2, nodeB2))(P2.graphPSpecific(nodeS2, P2.polynomialSpecific))))
              .addTitle(Render.tensor(P1.polynomialSpecific, P2.polynomialSpecific), 3, Render.tensor(Q1.polynomialSpecific, Q2.polynomialSpecific), 3)
      def showTitledGraphCustom[Y](graphFmt: Format, labels: ((String, String), (String, String))): String =
        ???

  given mooreTensoredMonomialToTensoredMonomial[A1, B1, A2, B2, C1, D1, C2, D2](using
    P1: MermaidP[Interface[A1, B1, _]],
    P2: MermaidP[Interface[A2, B2, _]],
    Q1: MermaidQ[Interface[C1, D1, _]],
    Q2: MermaidQ[Interface[C2, D2, _]],
  ): Mermaid[PolyMap[Interface[A1, B1, _] ⊗ Interface[A2, B2, _], Interface[C1, D1, _] ⊗ Interface[C2, D2, _], _]] =
    new Mermaid[PolyMap[Interface[A1, B1, _] ⊗ Interface[A2, B2, _], Interface[C1, D1, _] ⊗ Interface[C2, D2, _], _]]:
      private val labelA1 = "A<sub>1</sub>"
      private val labelA2 = "A<sub>2</sub>"
      private val labelB1 = "B<sub>1</sub>"
      private val labelB2 = "B<sub>2</sub>"
      private val labelC1 = "C<sub>1</sub>"
      private val labelC2 = "C<sub>2</sub>"
      private val labelD1 = "D<sub>1</sub>"
      private val labelD2 = "D<sub>2</sub>"
      private val nodeA1 = "A1"
      private val nodeA2 = "A2"
      private val nodeB1 = "B1"
      private val nodeB2 = "B2"
      private val nodeC1 = "C1"
      private val nodeC2 = "C2"
      private val nodeD1 = "D1"
      private val nodeD2 = "D2"
      def showGraph(graphFmt: Format): String =
        graphFmt match
          case Cardinal =>
            Render.mermaidCodeFence(Render.tensored(
              Q1.graphQCardinal((nodeA1, nodeB1))(P1.graphPCardinal(nodeB1, P1.polynomialCardinal)),
              Q2.graphQCardinal((nodeA2, nodeB2))(P2.graphPCardinal(nodeB2, P2.polynomialCardinal))))
          case Generic =>
            Render.mermaidCodeFence(Render.tensored(
              Q1.graphQGeneric((nodeC1, nodeD1), (labelC1, labelD1))(P1.graphPGeneric(nodeB1, P1.polynomialGeneric((labelA1, labelB1)), (labelA1, labelB1))), 
              Q2.graphQGeneric((nodeC2, nodeD2), (labelC2, labelD2))(P2.graphPGeneric(nodeB2, P2.polynomialGeneric((labelA2, labelB2)), (labelA2, labelB2)))))
          case Specific =>
            Render.mermaidCodeFence(Render.tensored(
              Q1.graphQSpecific((nodeA1, nodeB1))(P1.graphPSpecific(nodeB1, P1.polynomialSpecific)), 
              Q2.graphQSpecific((nodeA2, nodeB2))(P2.graphPSpecific(nodeB2, P2.polynomialSpecific))))
      def showGraphCustom[Y](graphFmt: Format, labels: ((String, String), (String, String))): String =
        ???
      def showTitle(titleFmt: Format): String =
        ???
      def showTitleCustom[Y](labels: ((String, String), (String, String))): String =
        ???
      def showTitleHtml(titleFmt: Format): String =
        ???
      def showTitleHtmlCustom[Y](labels: ((String, String), (String, String))): String =
        ???
      def showTitledGraph(titleFmt: Format, graphFmt: Format): String =
        graphFmt match
          case Cardinal =>
            showGraph(titleFmt)
              .addTitle(
                Render.tensor(P1.polynomialCardinal, P2.polynomialCardinal), 4,
                Render.tensor(Q1.polynomialCardinal, Q2.polynomialCardinal), 4
              )
          case Generic =>
            showGraph(titleFmt)
              .addTitle(
                Render.tensor(P1.polynomialGeneric((labelA1, labelB1)), P2.polynomialGeneric((labelA2, labelB2))), 4,
                Render.tensor(Q1.polynomialGeneric((labelC1, labelD1)), Q2.polynomialGeneric((labelC2, labelD2))), 4
              )
          case Specific =>
            showGraph(titleFmt)
              .addTitle(
                Render.tensor(P1.polynomialSpecific, P2.polynomialSpecific), 4,
                Render.tensor(Q1.polynomialSpecific, Q2.polynomialSpecific), 4
              )
      def showTitledGraphCustom[Y](graphFmt: Format, labels: ((String, String), (String, String))): String =
        ???


  given mooreTensoredMonomialToMonomial[A, B, C](using
    P1: MermaidP[Interface[(A, B), C, _]],
    P2: MermaidP[Interface[C, B, _]],
    Q: MermaidQ[Interface[A, C, _]],
  ): Mermaid[PolyMap[(Interface[(A, B), C, _] ⊗ Interface[C, B, _]), Interface[A, C, _], _]] =
    new Mermaid[PolyMap[(Interface[(A, B), C, _] ⊗ Interface[C, B, _]), Interface[A, C, _], _]]:
      private val labelA = "A"
      private val labelB = "B"
      private val labelC = "C"
      private val nodeA = "A"
      private val nodeC = "C"
      private val nodeP1 = "P1"
      private val nodeP2 = "P2"
      private val split1 = "Split1"

      def showGraph(graphFmt: Format): String =
        graphFmt match
          case Cardinal =>
            Render.mermaidCodeFence(Render.tensored(
              Q.graphQCardinal((nodeA, nodeC))(P1.graphPCardinal(nodeP1, P1.polynomialCardinal)),
              Q.graphQCardinal((nodeA, nodeC))(P2.graphPCardinal(nodeP2, P2.polynomialCardinal))))
          case Generic =>
            Render.mermaidCodeFence(Q.graphQGeneric((nodeA, nodeC), (labelA, labelC))(Render.tensored(
              P1.graphPGeneric(nodeP1, P1.polynomialGeneric((labelA+labelB, labelC)), (labelA+labelB, labelC)).split(s"${nodeP1}---")(0) + s"$nodeP1---$split1[ ]:::point\nend\n$split1---${nodeP2}---|${labelB}|${nodeP1}",
              P2.graphPGeneric(nodeP2, P2.polynomialGeneric((labelC, labelB)), (labelC, labelB)).replaceAll(s"A_${nodeP2}:::point---${nodeP2}", "").split(s"${nodeP2}---")(0) + s"end\n$split1"
            )))
          case Specific =>
            Render.mermaidCodeFence(Render.tensored(
              Q.graphQSpecific((nodeA, nodeC))(P1.graphPSpecific(nodeP1, P1.polynomialSpecific)), 
              Q.graphQSpecific((nodeA, nodeC))(P2.graphPSpecific(nodeP2, P2.polynomialSpecific))))
      def showGraphCustom[Y](graphFmt: Format, labels: ((String, String), String)): String =
        ???
      def showTitle(titleFmt: Format): String =
        ???
      def showTitleCustom[Y](labels: ((String, String), String)): String =
        ???
      def showTitleHtml(titleFmt: Format): String =
        ???
      def showTitleHtmlCustom[Y](labels: ((String, String), String)): String =
        ???
      def showTitledGraph(titleFmt: Format, graphFmt: Format): String =
        graphFmt match
          case Cardinal =>
            showGraph(titleFmt)
              .addTitle(
                Render.tensor(P1.polynomialCardinal, P2.polynomialCardinal), 4,
                Q.polynomialCardinal, 4
              )
          case Generic =>
            showGraph(titleFmt)
              .addTitle(
                Render.tensor(P1.polynomialGeneric((labelA+labelB, labelC)), P2.polynomialGeneric((labelC, labelB))), 4,
                Q.polynomialGeneric((labelA, labelC)), 4
              )
          case Specific =>
            showGraph(titleFmt)
              .addTitle(
                Render.tensor(P1.polynomialSpecific, P2.polynomialSpecific), 4,
                Q.polynomialSpecific, 4
              )
      def showTitledGraphCustom[Y](graphFmt: Format, labels: ((String, String), String)): String =
        ???


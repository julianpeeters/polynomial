package polynomial.mermaid.instances

import polynomial.mermaid.Mermaid
import polynomial.mermaid.render.p.MermaidP
import polynomial.mermaid.render.q.MermaidQ
import polynomial.mermaid.render.Format.{Cardinal, Generic, Specific}
import polynomial.mermaid.render.Format
import polynomial.mermaid.render.Render
import polynomial.morphism.PolyMap
import polynomial.`object`.Monomial.{Interface, Store}
import polynomial.product.×

given mooreTensoredStoreToTensoredMonomial[S1, S2, A1, B1, A2, B2](using
  P1: MermaidP[Store[S1, _]],
  P2: MermaidP[Store[S2, _]],
  Q1: MermaidQ[Interface[A1, B1, _]],
  Q2: MermaidQ[Interface[A2, B2, _]],
): Mermaid[PolyMap[Store[S1, _] × Store[S2, _], Interface[A1, B1, _] × Interface[A2, B2, _], _]] =
  new Mermaid[PolyMap[Store[S1, _] × Store[S2, _], Interface[A1, B1, _] × Interface[A2, B2, _], _]]:
    private val labelS1 = "S<sub>1</sub>"
    private val labelS2 = "S<sub>2</sub>"
    private val labelA1 = "A<sub>1</sub>"
    private val labelA2 = "A<sub>2</sub>"
    private val labelB1 = "B<sub>1</sub>"
    private val labelB2 = "B<sub>2</sub>"
    private val nodeS1 = "S1"
    private val nodeS2 = "S2"
    private val nodeA1 = "A1:::hidden"
    private val nodeA2 = "A2:::hidden"
    private val nodeB1 = "B1:::hidden"
    private val nodeB2 = "B2:::hidden"

    // private val nodeA = "A<sub>1</sub>∨A<sub>2</sub>"

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
    // def showTitledGraph(titleFmt: Format, graphFmt: Format): String =
    //   graphFmt match
    //     case Cardinal =>
    //       Render.mermaidCodeFence(Render.tensored(Q1.graphQCardinal((nodeA1, nodeB1))(P1.graphPCardinal(nodeS1, P1.polynomialCardinal)), Q2.graphQCardinal((nodeA2, nodeB2))(P2.graphPCardinal(nodeS2, P2.polynomialCardinal))))
    //         .addTitle(Render.tensor(P1.polynomialCardinal, P2.polynomialCardinal), 3, Render.tensor(Q1.polynomialCardinal, Q2.polynomialCardinal), 3)
    //     case Generic =>
    //       Render.mermaidCodeFence(Render.tensored(Q1.graphQGeneric((nodeA1, nodeB1), (labelA1, labelB1))(P1.graphPGeneric(nodeS1, P1.polynomialGeneric(labelS1), labelS1)), Q2.graphQGeneric((nodeA2, nodeB2), (labelA2, labelB2))(P2.graphPGeneric(nodeS2, P2.polynomialGeneric(labelS2), labelS2))))
    //         .addTitle(Render.tensor(P1.polynomialGeneric(labelS1), P2.polynomialGeneric(labelS2)), 3, Render.tensor(Q1.polynomialGeneric((labelA1, labelB1)), Q2.polynomialGeneric((labelA2, labelB2))), 3)
    //     case Specific =>
    //       Render.mermaidCodeFence(Render.tensored(Q1.graphQSpecific((nodeA1, nodeB1))(P1.graphPSpecific(nodeS1, P1.polynomialSpecific)), Q2.graphQSpecific((nodeA2, nodeB2))(P2.graphPSpecific(nodeS2, P2.polynomialSpecific))))
    //         .addTitle(Render.tensor(P1.polynomialSpecific, P2.polynomialSpecific), 3, Render.tensor(Q1.polynomialSpecific, Q2.polynomialSpecific), 3)
    // def showTitledGraphCustom[Y](graphFmt: Format, labels: ((String, String), (String, String))): String =
    //   ???
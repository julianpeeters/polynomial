package polynomial.mermaid

import polynomial.morphism.{PolyMap, ~>}
import polynomial.`object`.{Monomial, Store}
import polynomial.product.⊗
import scala.reflect.ClassTag

trait Mermaid[P[_]]:
  def showGeneric: String
  def showSpecific: String

object Mermaid:




  





  trait MermaidP[P[_]]:
    def showGeneric: String
    def showSpecific: String

  object MermaidP:

    given [S](using S: ClassTag[S]): MermaidP[Store[S, _]] =
      new MermaidP[Store[S, _]]:
        def showGeneric: String = "S"
        def showSpecific: String = s"S[${S.toString}]"

    // given [A, B](using A: ClassTag[A], B: ClassTag[B]): MermaidP[Monomial[A, B, _]] =
    //   new MermaidP[Monomial[A, B, _]]:
    //     def showGeneric: String = "MermaidPMono[ ]:::empty"
    //     def showSpecific: String = "MermaidPMono[ ]:::empty"

  trait MermaidQ[Q[_]]:
    def showGeneric: String => String
    def showSpecific: String => String

  object MermaidQ:

    given [A, B](using A: ClassTag[A], B: ClassTag[B]): MermaidQ[Monomial[A, B, _]] =
      new MermaidQ[Monomial[A, B, _]]:
        def showGeneric: String => String =
          p =>
            s"A:::hidden-->|A|${p}-->|B|B:::hidden;"
        def showSpecific: String => String =
          p =>
            s"A[${A.toString}]:::hidden-->|${A.toString}|${p}-->|${B.toString}|B[${B.toString}]:::hidden;"

  // given [S](using S: ClassTag[S]): Mermaid[Store[S, _]] =
  //   new Mermaid[Store[S, _]]:
  //     def showGeneric: String = "S"
  //     def showSpecific: String = S.toString

  // given [A, B](using A: ClassTag[A], B: ClassTag[B], P: MermaidP[]): Mermaid[Monomial[A, B, _]] =
  //   new Mermaid[Monomial[A, B, _]]:
  //     def showGeneric: String => String =  s => s"A:::hidden-->|A|${s}-->|B|B:::hidden;"
  //     def showSpecific: String => String = _ => s"A:::hidden-->|A|${s}-->|B|B:::hidden;"

  given mooreStoreToMono[S, A, B](using
    S: ClassTag[S],
    A: ClassTag[A],
    B: ClassTag[B],
    P: MermaidP[Store[S, _]],
    Q: MermaidQ[Monomial[A, B, _]]
  ): Mermaid[PolyMap[Store[S, _], Monomial[A, B, _], _]] =
    new Mermaid[PolyMap[Store[S, _], Monomial[A, B, _], _]]:

      def showGeneric: String =
        s"""|```mermaid
            |graph LR;
            |  ${Q.showGeneric(P.showGeneric)}
            |```""".stripMargin

      def showSpecific: String =
        s"""|```mermaid
            |graph LR;
            |  ${typeName[Option[String]]}
            |
            |  ${Q.showGeneric(P.showGeneric)}
            |```""".stripMargin

  given mealyStoreToMono[S, A, B](using
    S: ClassTag[S],
    A: ClassTag[A],
    B: ClassTag[B]
  ): Mermaid[PolyMap[Store[S, _], Monomial[A, A => B, _], _]] =
    new Mermaid[PolyMap[Store[S, _], Monomial[A, A => B, _], _]]:

      def showGeneric: String =
        s"""|```mermaid
            |graph LR;
            |  A:::hidden-->|A|S-->|B|B:::hidden;
            |```""".stripMargin

      def showSpecific: String =
        s"""|```mermaid
            |graph LR;
            |  A[${A.toString}]:::hidden-->|${A.toString}|S[${S.toString}]-->|${A.toString} => ${B.toString}|B[${A.toString} => ${B.toString}]:::hidden;
            |```""".stripMargin

  // given moore2[A, B, C](using
  //   // S: ClassTag[S],
  //   A: ClassTag[A],
  //   B: ClassTag[B],
  //   C: ClassTag[C]
  // ): Mermaid[(Monomial[(A, B), C, _] ⊗ Monomial[C, B, _]) ~> Monomial[A, C, _]] =
  //   new Mermaid[(Monomial[(A, B), C, _] ⊗ Monomial[C, B, _]) ~> Monomial[A, C, _]]:
  //     def showGeneric: String = "???"
  //     def showSpecific: String = "???"
  //           // s"""|```mermaid
  //           //     |graph LR;
  //           //     |  A[${A.toString}]:::hidden-->|${A.toString}|S-->|${A.toString} => ${B.toString}|B[${A.toString} => ${B.toString}]:::hidden;
  //           //     |```""".stripMargin

  given mealyTensoredMonoToMono[A, C](using
    A: ClassTag[A],
    C: ClassTag[C],
  ): Mermaid[(Monomial[(A, A => C), C, _] ⊗ Monomial[C, A => C, _]) ~> Monomial[A, A => C, _]] =
    new Mermaid[(Monomial[(A, A => C), C, _] ⊗ Monomial[C, A => C, _]) ~> Monomial[A, A => C, _]]:
      def showGeneric: String =
        s"""|```mermiad
            |graph LR;
            |  A:::hidden ----|A|P[ ]:::hole
            |  subgraph System[ ]
            |      P & Q[ ]:::hole ---|C|Split[ ]:::empty
            |      Q ---|B|P
            |  end
            |  Split --- E:::hidden
            |  classDef empty width:0px,height:0px;
            |  classDef hole fill:background;
            |```""".stripMargin
      def showSpecific: String =
        s"""|```mermaid
            |graph LR;
            |  A:::hidden ----|${A.toString}|P[ ]:::hole
            |  subgraph System[ ]
            |      P & Q[ ]:::hole ---|${C.toString}|Split[ ]:::empty
            |      Q ---|${A.toString} => ${C.toString}|P
            |  end
            |  Split --- E:::hidden
            |  classDef empty width:0px,height:0px;
            |  classDef hole fill:background;
            |```""".stripMargin
             
package polynomial.mermaid

import polynomial.morphism.PolyMap
import polynomial.`object`.{Monomial, Store}
import scala.reflect.ClassTag

trait Mermaid[P[_]]:
    def showGeneric: String
    def showSpecific: String

object Mermaid:

  given moore[S, A, B](using
    S: ClassTag[S],
    A: ClassTag[A],
    B: ClassTag[B]
  ): Mermaid[PolyMap[Store[S, _], Monomial[A, B, _], _]] =
    new Mermaid[PolyMap[Store[S, _], Monomial[A, B, _], _]]:

      def showGeneric: String =
        s"""|```mermaid
            |graph LR;
            |  A:::hidden-->|A|S-->|B|B:::hidden;
            |```""".stripMargin

      def showSpecific: String =
        s"""|```mermaid
            |graph LR;
            |  A[${A.toString}]:::hidden-->|${A.toString}|S[${S.toString}]-->|${B.toString}|B[${B.toString}]:::hidden;
            |```""".stripMargin

  given mealy[S, A, B](using
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
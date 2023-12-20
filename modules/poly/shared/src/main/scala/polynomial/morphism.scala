package polynomial

import polynomial.`object`.{Binomial, Monomial, Store}
import polynomial.product.⊗

object morphism:

  type ~>[P[_], Q[_]] = PolyMap[P, Q, _]

  abstract class PolyMap[P[_], Q[_], Y]:
    def φ: PolyMap.Phi[P, Q, Y]
    def `φ#`: PolyMap.PhiSharp[P, Q, Y]

  object PolyMap:

    type Phi[P[_], Q[_], Y] = (P[Y], Q[Y]) match
      case (PolyMap[Store[s, _], Binomial[a1, b1, a2, b2, _], Y], Binomial[a3, b3, a4, b4, Y]) => (s => b3, s => b4)
      case (PolyMap[Store[s, _], Monomial[a1, b1, _], Y], Monomial[a2, b2, Y])                 => s => b2
      case (Binomial[a1, b1, a2, b2, Y], Binomial[a3, b3, a4, b4, Y])                          => (b1 => b3, b2 => b4)
      case (Monomial[a1, b1, Y], Monomial[a2, b2, Y])                                          => b1 => b2
      case (Store[s, Y], Binomial[a1, b1, a2, b2, Y])                                          => (s => b1, s => b2)
      case (Store[s, Y], Monomial[a, b, Y])                                                    => s => b

    type PhiSharp[P[_], Q[_], Y] = (P[Y], Q[Y]) match
      case (PolyMap[Store[s, _], Binomial[a1, b1, a2, b2, _], Y], Binomial[a3, b3, a4, b4, Y]) => ((s, a3) => s, (s, a4) => s)
      case (PolyMap[Store[s, _], Monomial[a1, b1, _], Y], Monomial[a2, b2, Y])                 => (s, a2) => s
      case (Binomial[a1, b1, a2, b2, Y], Binomial[a3, b3, a4, b4, Y])                          => ((b1, a3) => b3, (b2, a4) => b4)
      case (Monomial[a1, b1, Y], Monomial[a2, b2, Y])                                          => Function2[b1, a2, a1]
      case (Store[s, Y], Binomial[a1, b1, a2, b2, Y])                                          => (Function2[s, a1, s], Function2[s, a2, s])
      case (Store[s, Y], Monomial[a, b, Y])                                                    => Function2[s, a, s]

    def apply[P[_], Q[_], Y](
      phi: Phi[P, Q, Y],
      phiSharp: PhiSharp[P, Q, Y]
    ): PolyMap[P, Q, Y] =
      new PolyMap[P, Q, Y]:
        def φ: Phi[P, Q, Y] = phi
        def `φ#`: PhiSharp[P, Q, Y] = phiSharp

    extension [S, A1, B1, Y] (p: PolyMap[Store[S, _], Monomial[A1, B1, _], Y])
      @scala.annotation.targetName("andThenStoreMonoToMono")
      def andThen[A2, B2](
        w: PolyMap[Monomial[A1, B1, _], Monomial[A2, B2, _], Y]
      ): PolyMap[PolyMap[Store[S, _], Monomial[A1, B1, _], _], Monomial[A2, B2, _], Y] =
        new PolyMap[PolyMap[Store[S, _], Monomial[A1, B1, _], _], Monomial[A2, B2, _], Y]:
          def φ: Phi[PolyMap[Store[S, _], Monomial[A1, B1, _], _], Monomial[A2, B2, _], Y] =
            p.φ.andThen(w.φ)
          def `φ#`: PhiSharp[PolyMap[Store[S, _], Monomial[A1, B1, _], _], Monomial[A2, B2, _], Y] =
            (s, a) => p.`φ#`(s, w.`φ#`(p.φ(s), a))

    extension [S, A1, B1, A2, B2, Y] (p: PolyMap[Store[S, _], Binomial[A1, B1, A2, B2, _], Y])
      @scala.annotation.targetName("andThenStoreBiToBi")
      def andThen(
        w: PolyMap[Binomial[A1, B1, A2, B2, _], Binomial[A1, A1 => B1, A2, A2 => B2, _], Y]
      ): PolyMap[PolyMap[Store[S, _], Binomial[A1, B1, A2, B2, _], _], Binomial[A1, A1 => B1, A2, A2 => B2, _], Y] =
        new PolyMap[PolyMap[Store[S, _], Binomial[A1, B1, A2, B2, _], _], Binomial[A1, A1 => B1, A2, A2 => B2, _], Y]:
          def φ: Phi[PolyMap[Store[S, _], Binomial[A1, B1, A2, B2, _], _], Binomial[A1, A1 => B1, A2, A2 => B2, _], Y] =
            (p.φ._1.andThen(w.φ._1), p.φ._2.andThen(w.φ._2))
          def `φ#`: PhiSharp[PolyMap[Store[S, _], Binomial[A1, B1, A2, B2, _], _], Binomial[A1, A1 => B1, A2, A2 => B2, _], Y] =
            ((s, a) => p.`φ#`._1(s, a), (s, a) => p.`φ#`._2(s, a))

    extension [S1, S2, A1, B1, A2, B2, A3, B3, A4, B4, Y] (p: PolyMap[(Store[S1, _] ⊗ Store[S2, _]), (Binomial[A1, B1, A2, B2, _] ⊗ Binomial[A3, B3, A4, B4, _]), Y])
      @scala.annotation.targetName("andThenTensorStoreTensorBiToBi")
      def andThen(
        w: PolyMap[Binomial[A1, B1, A2, B2, _] ⊗ Binomial[A3, B3, A4, B4, _], Binomial[A1, A1 => B3, A2, A2 => B4, _], Y],
        f: S2 => A3,
        g: S1 => A4,
        h: B1 => A3,
        i: B2 => A4
      ): PolyMap[PolyMap[(Store[S1, _] ⊗ Store[S2, _]), (Binomial[A1, B1, A2, B2, _] ⊗ Binomial[A3, B3, A4, B4, _]), _], Binomial[A1, A1 => B3, A2, A2 => B4, _], Y] =
        new PolyMap[PolyMap[(Store[S1, _] ⊗ Store[S2, _]), (Binomial[A1, B1, A2, B2, _] ⊗ Binomial[A3, B3, A4, B4, _]), _], Binomial[A1, A1 => B3, A2, A2 => B4, _], Y]:
          def φ: Phi[PolyMap[(Store[S1, _] ⊗ Store[S2, _]), (Binomial[A1, B1, A2, B2, _] ⊗ Binomial[A3, B3, A4, B4, _]), _], Binomial[A1, A1 => B3, A2, A2 => B4, _], Y] =
            (
              s => a1 => p.φ._1(p.`φ#`._1(s, (a1, h(p.φ._1(s)._1))))._2,
              s => a2 => p.φ._2(p.`φ#`._2(s, (a2, i(p.φ._2(s)._1))))._2
            )
          def `φ#`: PhiSharp[PolyMap[(Store[S1, _] ⊗ Store[S2, _]), (Binomial[A1, B1, A2, B2, _] ⊗ Binomial[A3, B3, A4, B4, _]), _], Binomial[A1, A1 => B3, A2, A2 => B4, _], Y] =
            (
              (s, a1) => p.`φ#`._1(s, (a1, f(s._2))),
              (s, a2) =>  p.`φ#`._2(s, (a2, g(s._1)))
            )
    
    extension [S, A1, B1, A2, B2, Y] (p: PolyMap[Store[S, _], Binomial[A1, B1, A2, B2, _], Y])

      def swapInterfaceDir: PolyMap[Store[S, _], Binomial[A1, B2, A2, B1, _], Y] =
        new PolyMap[Store[S, _], Binomial[A1, B2, A2, B1, _], Y]:
          def φ: Phi[(Store[S, _]), (Binomial[A1, B2, A2, B1, _]), Y] =
            p.φ.swap
          def `φ#`: PhiSharp[(Store[S, _]), (Binomial[A1, B2, A2, B1, _]), Y] =
            p.`φ#`

      def swapInterfacePos: PolyMap[Store[S, _], Binomial[A2, B1, A1, B2, _], Y] =
        new PolyMap[Store[S, _], Binomial[A2, B1, A1, B2, _], Y]:
          def φ: Phi[(Store[S, _]), (Binomial[A2, B1, A1, B2, _]), Y] =
            p.φ
          def `φ#`: PhiSharp[(Store[S, _]), (Binomial[A2, B1, A1, B2, _]), Y] =
            p.`φ#`.swap

      def swapModes: PolyMap[Store[S, _], Binomial[A2, B2, A1, B1, _], Y] =
        new PolyMap[Store[S, _], Binomial[A2, B2, A1, B1, _], Y]:
          def φ: Phi[(Store[S, _]), (Binomial[A2, B2, A1, B1, _]), Y] =
            p.φ.swap
          def `φ#`: PhiSharp[(Store[S, _]), (Binomial[A2, B2, A1, B1, _]), Y] =
            p.`φ#`.swap
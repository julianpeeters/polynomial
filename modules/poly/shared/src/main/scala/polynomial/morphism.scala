package polynomial

import polynomial.`object`.{Binomial, Monomial, Store}
import polynomial.product.{Tensor, ⊗}

object morphism:

  type ~>[P[_], Q[_]] = PolyMap[P, Q, _]

  abstract class PolyMap[P[_], Q[_], Y]:
    def φ: PolyMap.Phi[P, Q, Y]
    def `φ#`: PolyMap.PhiSharp[P, Q, Y]

  object PolyMap:

    type Phi[P[_], Q[_], Y] = (P[Y], Q[Y]) match
      case (Binomial[a1, b1, a2, b2, Y], Binomial[a3, b3, a4, b4, Y]) => (b1 => b3, b2 => b4)
      case (Binomial[a1, b1, a2, b2, Y], Monomial[a3, b3, Y])         => (b1 => b3, b2 => b3) 
      case (Monomial[a1, b1, Y], Binomial[a3, b3, a4, b4, Y])         => (b1 => b3, b1 => b4)
      case (Monomial[a1, b1, Y], Monomial[a2, b2, Y])                 => b1 => b2
      case (PolyMap[p, q, Y], Binomial[a3, b3, a4, b4, Y])            => Phi[p, Binomial[a3, b3, a4, b4, _], Y]
      case (PolyMap[p, q, Y], Monomial[a2, b2, Y])                    => Phi[p, Monomial[a2, b2, _], Y]
      case (PolyMap[o, p, Y], Tensor[q, r, Y])                        => Phi[o, Tensor[q, r, _], Y]
      case (Store[s, Y], Binomial[a1, b1, a2, b2, Y])                 => (s => b1, s => b2)
      case (Store[s, Y], Monomial[a, b, Y])                           => s => b
      case (Tensor[p, q, Y], Monomial[a1, b1, Y])                     => Phi[Tensor.DayConvolution[p, q, _], Monomial[a1, b1, _], Y]
      case (Tensor[p, q, Y], Binomial[a1, b1, a2, b2, Y])             => Phi[Tensor.DayConvolution[p, q, _], Binomial[a1, b1, a2, b2, _], Y]
      case (Tensor[o, p, Y], Tensor[q, r, Y])                         => Phi[Tensor.DayConvolution[o, p, _], Tensor.DayConvolution[q, r, _], Y]
      
    type PhiSharp[P[_], Q[_], Y] = (P[Y], Q[Y]) match
      case (Binomial[a1, b1, a2, b2, Y], Binomial[a3, b3, a4, b4, Y]) => ((b1, a3) => a1, (b2, a4) => a2)
      case (Monomial[a1, b1, Y], Monomial[a2, b2, Y])                 => (b1, a2) => a1
      case (PolyMap[p, q, Y], Binomial[a3, b3, a4, b4, Y])            => PhiSharp[p, Binomial[a3, b3, a4, b4, _], Y]
      case (PolyMap[p, q, Y], Monomial[a2, b2, Y])                    => PhiSharp[p, Monomial[a2, b2, _], Y]
      case (PolyMap[o, p, Y], Tensor[q, r, Y])                        => PhiSharp[o, Tensor[q, r, _], Y]
      case (Store[s, Y], Binomial[a1, b1, a2, b2, Y])                 => ((s, a1) => s, (s, a2) => s)
      case (Store[s, Y], Monomial[a, b, Y])                           => (s, a) => s
      case (Tensor[p, q, Y], Binomial[a1, b1, a2, b2, Y])             => PhiSharp[Tensor.DayConvolution[p, q, _], Binomial[a1, b1, a2, b2, _], Y]
      case (Tensor[p, q, Y], Monomial[a1, b1, Y])                     => PhiSharp[Tensor.DayConvolution[p, q, _], Monomial[a1, b1, _], Y]
      case (Tensor[o, p, Y], Tensor[q, r, Y])                         => PhiSharp[Tensor.DayConvolution[o, p, _], Tensor.DayConvolution[q, r, _], Y]

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

    extension [S1, S2, A1, B1, A2, B2, Y] (p: PolyMap[Store[S1, _] ⊗ Store[S2, _], Monomial[A1, B1, _] ⊗ Monomial[A2, B2, _], Y])
      @scala.annotation.targetName("andThenTensoredStoreTensoredMonoToMonof")
      def andThen(
        w: PolyMap[Monomial[A1, B1, _] ⊗ Monomial[A2, B2, _], Monomial[A1, A1 => B2, _], Y]
      ): PolyMap[PolyMap[Store[S1, _] ⊗ Store[S2, _], Monomial[A1, B1, _] ⊗ Monomial[A2, B2, _], _], Monomial[A1, A1 => B2, _], Y] =
        new PolyMap[PolyMap[Store[S1, _] ⊗ Store[S2, _], Monomial[A1, B1, _] ⊗ Monomial[A2, B2, _], _], Monomial[A1, A1 => B2, _], Y]:
          def φ: Phi[PolyMap[Store[S1, _] ⊗ Store[S2, _], Monomial[A1, B1, _] ⊗ Monomial[A2, B2, _], _], Monomial[A1, A1 => B2, _], Y] =
            p.φ.andThen(w.φ)
          def `φ#`: PhiSharp[PolyMap[Store[S1, _] ⊗ Store[S2, _], Monomial[A1, B1, _] ⊗ Monomial[A2, B2, _], _], Monomial[A1, A1 => B2, _], Y] =
            (s, a) => p.`φ#`(s, w.`φ#`(p.φ(s), a))

    extension [S1, S2, A1, B1, A2, B2, Y] (p: PolyMap[Store[S1, _] ⊗ Store[S2, _], Monomial[A1, B1, _] ⊗ Monomial[A2, B2, _], Y])
      @scala.annotation.targetName("andThenTensoredStoreTensoredMonoToMono")
      def andThen(
        w: PolyMap[Monomial[A1, B1, _] ⊗ Monomial[A2, B2, _], Monomial[A1, B2, _], Y]
      ): PolyMap[PolyMap[Store[S1, _] ⊗ Store[S2, _], Monomial[A1, B1, _] ⊗ Monomial[A2, B2, _], _], Monomial[A1, B2, _], Y] =
        new PolyMap[PolyMap[Store[S1, _] ⊗ Store[S2, _], Monomial[A1, B1, _] ⊗ Monomial[A2, B2, _], _], Monomial[A1, B2, _], Y]:
          def φ: Phi[PolyMap[Store[S1, _] ⊗ Store[S2, _], Monomial[A1, B1, _] ⊗ Monomial[A2, B2, _], _], Monomial[A1, B2, _], Y] =
            p.φ.andThen(w.φ)
          def `φ#`: PhiSharp[PolyMap[Store[S1, _] ⊗ Store[S2, _], Monomial[A1, B1, _] ⊗ Monomial[A2, B2, _], _], Monomial[A1, B2, _], Y] =
            (s, a) => p.`φ#`(s, w.`φ#`(p.φ(s), a))

    extension [S1, S2, A, B, C, Y] (p: PolyMap[(Store[S1, _] ⊗ Store[S2, _]), (Monomial[(A, B), C, _] ⊗ Monomial[C, B, _]), Y])
      @scala.annotation.targetName("andThenTensoredStoreTensoredMonoToMono2")
      def andThen(
        w: PolyMap[(Monomial[(A, B), C, _] ⊗ Monomial[C, B, _]),  Monomial[A, C, _], Y]
      ): PolyMap[(Store[S1, _] ⊗ Store[S2, _]) ~> (Monomial[(A, B), C, _] ⊗ Monomial[C, B, _]), Monomial[A, C, _], Y] =
        new PolyMap[(Store[S1, _] ⊗ Store[S2, _]) ~> (Monomial[(A, B), C, _] ⊗ Monomial[C, B, _]), Monomial[A, C, _], Y]:
          def φ: Phi[(Store[S1, _] ⊗ Store[S2, _]) ~> (Monomial[(A, B), C, _] ⊗ Monomial[C, B, _]), Monomial[A, C, _], Y] =
            p.φ.andThen(w.φ)
          def `φ#`: PhiSharp[(Store[S1, _] ⊗ Store[S2, _]) ~> (Monomial[(A, B), C, _] ⊗ Monomial[C, B, _]), Monomial[A, C, _], Y] =
            (s, a) => p.`φ#`(s, w.`φ#`(p.φ(s), a))


    extension [S1, S2, A, B, C, Y] (p: PolyMap[(Store[S1, _] ⊗ Store[S2, _]), (Monomial[(A, B), C, _] ⊗ Monomial[C, B, _]), Y])
      @scala.annotation.targetName("andThenTensoredStoreTensoredMonoToMono3")
      def andThen(
        w: PolyMap[(Monomial[(A, B), C, _] ⊗ Monomial[C, B, _]),  Monomial[A, A => C, _], Y]
      ): PolyMap[(Store[S1, _] ⊗ Store[S2, _]) ~> (Monomial[(A, B), C, _] ⊗ Monomial[C, B, _]), Monomial[A, A => C, _], Y] =
        new PolyMap[(Store[S1, _] ⊗ Store[S2, _]) ~> (Monomial[(A, B), C, _] ⊗ Monomial[C, B, _]), Monomial[A, A => C, _], Y]:
          def φ: Phi[(Store[S1, _] ⊗ Store[S2, _]) ~> (Monomial[(A, B), C, _] ⊗ Monomial[C, B, _]), Monomial[A, A => C, _], Y] =
            p.φ.andThen(w.φ)
          def `φ#`: PhiSharp[(Store[S1, _] ⊗ Store[S2, _]) ~> (Monomial[(A, B), C, _] ⊗ Monomial[C, B, _]), Monomial[A, A => C, _], Y] =
            (s, a) => p.`φ#`(s, w.`φ#`(p.φ(s), a))            

    extension [S1, S2, A1, B1, A2, B2, Y] (p: PolyMap[Store[S1, _] ⊗ Store[S2, _], Monomial[A1, B1, _] ⊗ Monomial[A2, B2, _], Y])
      @scala.annotation.targetName("andThenTensoredStoreTensoredMonoToTensoredMono")
      def andThen[A3, B3, A4, B4](
        w: PolyMap[Monomial[A1, B1, _] ⊗ Monomial[A2, B2, _], Monomial[A3, B3, _] ⊗ Monomial[A4, B4, _], Y]
      ): PolyMap[PolyMap[Store[S1, _] ⊗ Store[S2, _], Monomial[A1, B1, _] ⊗ Monomial[A2, B2, _], _], Monomial[A3, B3, _] ⊗ Monomial[A4, B4, _], Y] =
        new PolyMap[PolyMap[Store[S1, _] ⊗ Store[S2, _], Monomial[A1, B1, _] ⊗ Monomial[A2, B2, _], _], Monomial[A3, B3, _] ⊗ Monomial[A4, B4, _], Y]:
          def φ: Phi[PolyMap[Store[S1, _] ⊗ Store[S2, _], Monomial[A1, B1, _] ⊗ Monomial[A2, B2, _], _], Monomial[A3, B3, _] ⊗ Monomial[A4, B4, _], Y] =
            p.φ.andThen(w.φ)
          def `φ#`: PhiSharp[PolyMap[Store[S1, _] ⊗ Store[S2, _], Monomial[A1, B1, _] ⊗ Monomial[A2, B2, _], _], Monomial[A3, B3, _] ⊗ Monomial[A4, B4, _], Y] =
            (s, a) => p.`φ#`(s, w.`φ#`(p.φ(s), a))

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
              (s: (S1, S2), a1: A1) => p.`φ#`._1(s, (a1, f(s._2))),
              (s: (S1, S2), a2: A2) => p.`φ#`._2(s, (a2, g(s._1)))
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
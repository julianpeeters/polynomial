package polynomial.morphism.methods

import polynomial.morphism.{PolyMap, ~>}
import polynomial.`object`.{Binomial, Monomial}
import polynomial.product.⊗

object andThen:

  extension [S, A1, B1, Y] (p: PolyMap[Monomial.Store[S, _], Monomial.Interface[A1, B1, _], Y])
    @scala.annotation.targetName("andThenStoreMonoToMono")
    def andThen[A2, B2](
      w: PolyMap[Monomial.Interface[A1, B1, _], Monomial.Interface[A2, B2, _], Y]
    ): PolyMap[PolyMap[Monomial.Store[S, _], Monomial.Interface[A1, B1, _], _], Monomial.Interface[A2, B2, _], Y] =
      new PolyMap[PolyMap[Monomial.Store[S, _], Monomial.Interface[A1, B1, _], _], Monomial.Interface[A2, B2, _], Y]:
        def φ: PolyMap.Phi[PolyMap[Monomial.Store[S, _], Monomial.Interface[A1, B1, _], _], Monomial.Interface[A2, B2, _], Y] =
          p.φ.andThen(w.φ)
        def `φ#`: PolyMap.PhiSharp[PolyMap[Monomial.Store[S, _], Monomial.Interface[A1, B1, _], _], Monomial.Interface[A2, B2, _], Y] =
          (s, a) => p.`φ#`(s, w.`φ#`(p.φ(s), a))

  extension [S, A1, B1, A2, B2, Y] (p: PolyMap[Monomial.Store[S, _], Binomial.Interface[A1, B1, A2, B2, _], Y])
    @scala.annotation.targetName("andThenStoreBiToBi")
    def andThen(
      w: PolyMap[Binomial.Interface[A1, B1, A2, B2, _], Binomial.Interface[A1, A1 => B1, A2, A2 => B2, _], Y]
    ): PolyMap[PolyMap[Monomial.Store[S, _], Binomial.Interface[A1, B1, A2, B2, _], _], Binomial.Interface[A1, A1 => B1, A2, A2 => B2, _], Y] =
      new PolyMap[PolyMap[Monomial.Store[S, _], Binomial.Interface[A1, B1, A2, B2, _], _], Binomial.Interface[A1, A1 => B1, A2, A2 => B2, _], Y]:
        def φ: PolyMap.Phi[PolyMap[Monomial.Store[S, _], Binomial.Interface[A1, B1, A2, B2, _], _], Binomial.Interface[A1, A1 => B1, A2, A2 => B2, _], Y] =
          (p.φ._1.andThen(w.φ._1), p.φ._2.andThen(w.φ._2))
        def `φ#`: PolyMap.PhiSharp[PolyMap[Monomial.Store[S, _], Binomial.Interface[A1, B1, A2, B2, _], _], Binomial.Interface[A1, A1 => B1, A2, A2 => B2, _], Y] =
          ((s, a) => p.`φ#`._1(s, a), (s, a) => p.`φ#`._2(s, a))    

  extension [S1, S2, A1, B1, A2, B2, Y] (p: PolyMap[Monomial.Store[S1, _] ⊗ Monomial.Store[S2, _], Monomial.Interface[A1, B1, _] ⊗ Monomial.Interface[A2, B2, _], Y])
    @scala.annotation.targetName("andThenTensoredStoreTensoredMonoToMonof")
    def andThen(
      w: PolyMap[Monomial.Interface[A1, B1, _] ⊗ Monomial.Interface[A2, B2, _], Monomial.Interface[A1, A1 => B2, _], Y]
    ): PolyMap[PolyMap[Monomial.Store[S1, _] ⊗ Monomial.Store[S2, _], Monomial.Interface[A1, B1, _] ⊗ Monomial.Interface[A2, B2, _], _], Monomial.Interface[A1, A1 => B2, _], Y] =
      new PolyMap[PolyMap[Monomial.Store[S1, _] ⊗ Monomial.Store[S2, _], Monomial.Interface[A1, B1, _] ⊗ Monomial.Interface[A2, B2, _], _], Monomial.Interface[A1, A1 => B2, _], Y]:
        def φ: PolyMap.Phi[PolyMap[Monomial.Store[S1, _] ⊗ Monomial.Store[S2, _], Monomial.Interface[A1, B1, _] ⊗ Monomial.Interface[A2, B2, _], _], Monomial.Interface[A1, A1 => B2, _], Y] =
          p.φ.andThen(w.φ)
        def `φ#`: PolyMap.PhiSharp[PolyMap[Monomial.Store[S1, _] ⊗ Monomial.Store[S2, _], Monomial.Interface[A1, B1, _] ⊗ Monomial.Interface[A2, B2, _], _], Monomial.Interface[A1, A1 => B2, _], Y] =
          (s, a) => p.`φ#`(s, w.`φ#`(p.φ(s), a))

  extension [S1, S2, A1, B1, A2, B2, Y] (p: PolyMap[Monomial.Store[S1, _] ⊗ Monomial.Store[S2, _], Monomial.Interface[A1, B1, _] ⊗ Monomial.Interface[A2, B2, _], Y])
    @scala.annotation.targetName("andThenTensoredStoreTensoredMonoToMono")
    def andThen(
      w: PolyMap[Monomial.Interface[A1, B1, _] ⊗ Monomial.Interface[A2, B2, _], Monomial.Interface[A1, B2, _], Y]
    ): PolyMap[PolyMap[Monomial.Store[S1, _] ⊗ Monomial.Store[S2, _], Monomial.Interface[A1, B1, _] ⊗ Monomial.Interface[A2, B2, _], _], Monomial.Interface[A1, B2, _], Y] =
      new PolyMap[PolyMap[Monomial.Store[S1, _] ⊗ Monomial.Store[S2, _], Monomial.Interface[A1, B1, _] ⊗ Monomial.Interface[A2, B2, _], _], Monomial.Interface[A1, B2, _], Y]:
        def φ: PolyMap.Phi[PolyMap[Monomial.Store[S1, _] ⊗ Monomial.Store[S2, _], Monomial.Interface[A1, B1, _] ⊗ Monomial.Interface[A2, B2, _], _], Monomial.Interface[A1, B2, _], Y] =
          p.φ.andThen(w.φ)
        def `φ#`: PolyMap.PhiSharp[PolyMap[Monomial.Store[S1, _] ⊗ Monomial.Store[S2, _], Monomial.Interface[A1, B1, _] ⊗ Monomial.Interface[A2, B2, _], _], Monomial.Interface[A1, B2, _], Y] =
          (s, a) => p.`φ#`(s, w.`φ#`(p.φ(s), a))

  extension [S1, S2, A, B, C, Y] (p: PolyMap[(Monomial.Store[S1, _] ⊗ Monomial.Store[S2, _]), (Monomial.Interface[(A, B), C, _] ⊗ Monomial.Interface[C, B, _]), Y])
    @scala.annotation.targetName("andThenTensoredStoreTensoredMonoToMono2")
    def andThen(
      w: PolyMap[(Monomial.Interface[(A, B), C, _] ⊗ Monomial.Interface[C, B, _]),  Monomial.Interface[A, C, _], Y]
    ): PolyMap[(Monomial.Store[S1, _] ⊗ Monomial.Store[S2, _]) ~> (Monomial.Interface[(A, B), C, _] ⊗ Monomial.Interface[C, B, _]), Monomial.Interface[A, C, _], Y] =
      new PolyMap[(Monomial.Store[S1, _] ⊗ Monomial.Store[S2, _]) ~> (Monomial.Interface[(A, B), C, _] ⊗ Monomial.Interface[C, B, _]), Monomial.Interface[A, C, _], Y]:
        def φ: PolyMap.Phi[(Monomial.Store[S1, _] ⊗ Monomial.Store[S2, _]) ~> (Monomial.Interface[(A, B), C, _] ⊗ Monomial.Interface[C, B, _]), Monomial.Interface[A, C, _], Y] =
          p.φ.andThen(w.φ)
        def `φ#`: PolyMap.PhiSharp[(Monomial.Store[S1, _] ⊗ Monomial.Store[S2, _]) ~> (Monomial.Interface[(A, B), C, _] ⊗ Monomial.Interface[C, B, _]), Monomial.Interface[A, C, _], Y] =
          (s, a) => p.`φ#`(s, w.`φ#`(p.φ(s), a))

  extension [S1, S2, A, B, C, Y] (p: PolyMap[(Monomial.Store[S1, _] ⊗ Monomial.Store[S2, _]), (Monomial.Interface[(A, B), C, _] ⊗ Monomial.Interface[C, B, _]), Y])
    @scala.annotation.targetName("andThenTensoredStoreTensoredMonoToMono3")
    def andThen(
      w: PolyMap[(Monomial.Interface[(A, B), C, _] ⊗ Monomial.Interface[C, B, _]),  Monomial.Interface[A, A => C, _], Y]
    ): PolyMap[(Monomial.Store[S1, _] ⊗ Monomial.Store[S2, _]) ~> (Monomial.Interface[(A, B), C, _] ⊗ Monomial.Interface[C, B, _]), Monomial.Interface[A, A => C, _], Y] =
      new PolyMap[(Monomial.Store[S1, _] ⊗ Monomial.Store[S2, _]) ~> (Monomial.Interface[(A, B), C, _] ⊗ Monomial.Interface[C, B, _]), Monomial.Interface[A, A => C, _], Y]:
        def φ: PolyMap.Phi[(Monomial.Store[S1, _] ⊗ Monomial.Store[S2, _]) ~> (Monomial.Interface[(A, B), C, _] ⊗ Monomial.Interface[C, B, _]), Monomial.Interface[A, A => C, _], Y] =
          p.φ.andThen(w.φ)
        def `φ#`: PolyMap.PhiSharp[(Monomial.Store[S1, _] ⊗ Monomial.Store[S2, _]) ~> (Monomial.Interface[(A, B), C, _] ⊗ Monomial.Interface[C, B, _]), Monomial.Interface[A, A => C, _], Y] =
          (s, a) => p.`φ#`(s, w.`φ#`(p.φ(s), a))            

  extension [S1, S2, A1, B1, A2, B2, Y] (p: PolyMap[Monomial.Store[S1, _] ⊗ Monomial.Store[S2, _], Monomial.Interface[A1, B1, _] ⊗ Monomial.Interface[A2, B2, _], Y])
    @scala.annotation.targetName("andThenTensoredStoreTensoredMonoToTensoredMono")
    def andThen[A3, B3, A4, B4](
      w: PolyMap[Monomial.Interface[A1, B1, _] ⊗ Monomial.Interface[A2, B2, _], Monomial.Interface[A3, B3, _] ⊗ Monomial.Interface[A4, B4, _], Y]
    ): PolyMap[PolyMap[Monomial.Store[S1, _] ⊗ Monomial.Store[S2, _], Monomial.Interface[A1, B1, _] ⊗ Monomial.Interface[A2, B2, _], _], Monomial.Interface[A3, B3, _] ⊗ Monomial.Interface[A4, B4, _], Y] =
      new PolyMap[PolyMap[Monomial.Store[S1, _] ⊗ Monomial.Store[S2, _], Monomial.Interface[A1, B1, _] ⊗ Monomial.Interface[A2, B2, _], _], Monomial.Interface[A3, B3, _] ⊗ Monomial.Interface[A4, B4, _], Y]:
        def φ: PolyMap.Phi[PolyMap[Monomial.Store[S1, _] ⊗ Monomial.Store[S2, _], Monomial.Interface[A1, B1, _] ⊗ Monomial.Interface[A2, B2, _], _], Monomial.Interface[A3, B3, _] ⊗ Monomial.Interface[A4, B4, _], Y] =
          p.φ.andThen(w.φ)
        def `φ#`: PolyMap.PhiSharp[PolyMap[Monomial.Store[S1, _] ⊗ Monomial.Store[S2, _], Monomial.Interface[A1, B1, _] ⊗ Monomial.Interface[A2, B2, _], _], Monomial.Interface[A3, B3, _] ⊗ Monomial.Interface[A4, B4, _], Y] =
          (s, a) => p.`φ#`(s, w.`φ#`(p.φ(s), a))

  extension [S1, S2, A1, B1, A2, B2, A3, B3, A4, B4, Y] (p: PolyMap[(Monomial.Store[S1, _] ⊗ Monomial.Store[S2, _]), (Binomial.Interface[A1, B1, A2, B2, _] ⊗ Binomial.Interface[A3, B3, A4, B4, _]), Y])
    @scala.annotation.targetName("andThenTensorStoreTensorBiToBi")
    def andThen(
      w: PolyMap[Binomial.Interface[A1, B1, A2, B2, _] ⊗ Binomial.Interface[A3, B3, A4, B4, _], Binomial.Interface[A1, A1 => B3, A2, A2 => B4, _], Y],
      f: S2 => A3,
      g: S1 => A4,
      h: B1 => A3,
      i: B2 => A4
    ): PolyMap[PolyMap[(Monomial.Store[S1, _] ⊗ Monomial.Store[S2, _]), (Binomial.Interface[A1, B1, A2, B2, _] ⊗ Binomial.Interface[A3, B3, A4, B4, _]), _], Binomial.Interface[A1, A1 => B3, A2, A2 => B4, _], Y] =
      new PolyMap[PolyMap[(Monomial.Store[S1, _] ⊗ Monomial.Store[S2, _]), (Binomial.Interface[A1, B1, A2, B2, _] ⊗ Binomial.Interface[A3, B3, A4, B4, _]), _], Binomial.Interface[A1, A1 => B3, A2, A2 => B4, _], Y]:
        def φ: PolyMap.Phi[PolyMap[(Monomial.Store[S1, _] ⊗ Monomial.Store[S2, _]), (Binomial.Interface[A1, B1, A2, B2, _] ⊗ Binomial.Interface[A3, B3, A4, B4, _]), _], Binomial.Interface[A1, A1 => B3, A2, A2 => B4, _], Y] =
          (
            s => a1 => p.φ._1(p.`φ#`._1(s, (a1, h(p.φ._1(s)._1))))._2,
            s => a2 => p.φ._2(p.`φ#`._2(s, (a2, i(p.φ._2(s)._1))))._2
          )
        def `φ#`: PolyMap.PhiSharp[PolyMap[(Monomial.Store[S1, _] ⊗ Monomial.Store[S2, _]), (Binomial.Interface[A1, B1, A2, B2, _] ⊗ Binomial.Interface[A3, B3, A4, B4, _]), _], Binomial.Interface[A1, A1 => B3, A2, A2 => B4, _], Y] =
          (
            (s: (S1, S2), a1: A1) => p.`φ#`._1(s, (a1, f(s._2))),
            (s: (S1, S2), a2: A2) => p.`φ#`._2(s, (a2, g(s._1)))
          )
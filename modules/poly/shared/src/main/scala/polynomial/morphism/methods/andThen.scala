package polynomial.morphism.methods

import polynomial.morphism.{PolyMap, ~>}
import polynomial.`object`.{Binomial, Monomial, Store}
import polynomial.product.⊗

object andThen:

  extension [S, A1, B1, Y] (p: PolyMap[Store[S, _], Monomial[A1, B1, _], Y])
    @scala.annotation.targetName("andThenStoreMonoToMono")
    def andThen[A2, B2](
      w: PolyMap[Monomial[A1, B1, _], Monomial[A2, B2, _], Y]
    ): PolyMap[PolyMap[Store[S, _], Monomial[A1, B1, _], _], Monomial[A2, B2, _], Y] =
      new PolyMap[PolyMap[Store[S, _], Monomial[A1, B1, _], _], Monomial[A2, B2, _], Y]:
        def φ: PolyMap.Phi[PolyMap[Store[S, _], Monomial[A1, B1, _], _], Monomial[A2, B2, _], Y] =
          p.φ.andThen(w.φ)
        def `φ#`: PolyMap.PhiSharp[PolyMap[Store[S, _], Monomial[A1, B1, _], _], Monomial[A2, B2, _], Y] =
          (s, a) => p.`φ#`(s, w.`φ#`(p.φ(s), a))

  extension [S, A1, B1, A2, B2, Y] (p: PolyMap[Store[S, _], Binomial[A1, B1, A2, B2, _], Y])
    @scala.annotation.targetName("andThenStoreBiToBi")
    def andThen(
      w: PolyMap[Binomial[A1, B1, A2, B2, _], Binomial[A1, A1 => B1, A2, A2 => B2, _], Y]
    ): PolyMap[PolyMap[Store[S, _], Binomial[A1, B1, A2, B2, _], _], Binomial[A1, A1 => B1, A2, A2 => B2, _], Y] =
      new PolyMap[PolyMap[Store[S, _], Binomial[A1, B1, A2, B2, _], _], Binomial[A1, A1 => B1, A2, A2 => B2, _], Y]:
        def φ: PolyMap.Phi[PolyMap[Store[S, _], Binomial[A1, B1, A2, B2, _], _], Binomial[A1, A1 => B1, A2, A2 => B2, _], Y] =
          (p.φ._1.andThen(w.φ._1), p.φ._2.andThen(w.φ._2))
        def `φ#`: PolyMap.PhiSharp[PolyMap[Store[S, _], Binomial[A1, B1, A2, B2, _], _], Binomial[A1, A1 => B1, A2, A2 => B2, _], Y] =
          ((s, a) => p.`φ#`._1(s, a), (s, a) => p.`φ#`._2(s, a))    

  extension [S1, S2, A1, B1, A2, B2, Y] (p: PolyMap[Store[S1, _] ⊗ Store[S2, _], Monomial[A1, B1, _] ⊗ Monomial[A2, B2, _], Y])
    @scala.annotation.targetName("andThenTensoredStoreTensoredMonoToMonof")
    def andThen(
      w: PolyMap[Monomial[A1, B1, _] ⊗ Monomial[A2, B2, _], Monomial[A1, A1 => B2, _], Y]
    ): PolyMap[PolyMap[Store[S1, _] ⊗ Store[S2, _], Monomial[A1, B1, _] ⊗ Monomial[A2, B2, _], _], Monomial[A1, A1 => B2, _], Y] =
      new PolyMap[PolyMap[Store[S1, _] ⊗ Store[S2, _], Monomial[A1, B1, _] ⊗ Monomial[A2, B2, _], _], Monomial[A1, A1 => B2, _], Y]:
        def φ: PolyMap.Phi[PolyMap[Store[S1, _] ⊗ Store[S2, _], Monomial[A1, B1, _] ⊗ Monomial[A2, B2, _], _], Monomial[A1, A1 => B2, _], Y] =
          p.φ.andThen(w.φ)
        def `φ#`: PolyMap.PhiSharp[PolyMap[Store[S1, _] ⊗ Store[S2, _], Monomial[A1, B1, _] ⊗ Monomial[A2, B2, _], _], Monomial[A1, A1 => B2, _], Y] =
          (s, a) => p.`φ#`(s, w.`φ#`(p.φ(s), a))

  extension [S1, S2, A1, B1, A2, B2, Y] (p: PolyMap[Store[S1, _] ⊗ Store[S2, _], Monomial[A1, B1, _] ⊗ Monomial[A2, B2, _], Y])
    @scala.annotation.targetName("andThenTensoredStoreTensoredMonoToMono")
    def andThen(
      w: PolyMap[Monomial[A1, B1, _] ⊗ Monomial[A2, B2, _], Monomial[A1, B2, _], Y]
    ): PolyMap[PolyMap[Store[S1, _] ⊗ Store[S2, _], Monomial[A1, B1, _] ⊗ Monomial[A2, B2, _], _], Monomial[A1, B2, _], Y] =
      new PolyMap[PolyMap[Store[S1, _] ⊗ Store[S2, _], Monomial[A1, B1, _] ⊗ Monomial[A2, B2, _], _], Monomial[A1, B2, _], Y]:
        def φ: PolyMap.Phi[PolyMap[Store[S1, _] ⊗ Store[S2, _], Monomial[A1, B1, _] ⊗ Monomial[A2, B2, _], _], Monomial[A1, B2, _], Y] =
          p.φ.andThen(w.φ)
        def `φ#`: PolyMap.PhiSharp[PolyMap[Store[S1, _] ⊗ Store[S2, _], Monomial[A1, B1, _] ⊗ Monomial[A2, B2, _], _], Monomial[A1, B2, _], Y] =
          (s, a) => p.`φ#`(s, w.`φ#`(p.φ(s), a))

  extension [S1, S2, A, B, C, Y] (p: PolyMap[(Store[S1, _] ⊗ Store[S2, _]), (Monomial[(A, B), C, _] ⊗ Monomial[C, B, _]), Y])
    @scala.annotation.targetName("andThenTensoredStoreTensoredMonoToMono2")
    def andThen(
      w: PolyMap[(Monomial[(A, B), C, _] ⊗ Monomial[C, B, _]),  Monomial[A, C, _], Y]
    ): PolyMap[(Store[S1, _] ⊗ Store[S2, _]) ~> (Monomial[(A, B), C, _] ⊗ Monomial[C, B, _]), Monomial[A, C, _], Y] =
      new PolyMap[(Store[S1, _] ⊗ Store[S2, _]) ~> (Monomial[(A, B), C, _] ⊗ Monomial[C, B, _]), Monomial[A, C, _], Y]:
        def φ: PolyMap.Phi[(Store[S1, _] ⊗ Store[S2, _]) ~> (Monomial[(A, B), C, _] ⊗ Monomial[C, B, _]), Monomial[A, C, _], Y] =
          p.φ.andThen(w.φ)
        def `φ#`: PolyMap.PhiSharp[(Store[S1, _] ⊗ Store[S2, _]) ~> (Monomial[(A, B), C, _] ⊗ Monomial[C, B, _]), Monomial[A, C, _], Y] =
          (s, a) => p.`φ#`(s, w.`φ#`(p.φ(s), a))

  extension [S1, S2, A, B, C, Y] (p: PolyMap[(Store[S1, _] ⊗ Store[S2, _]), (Monomial[(A, B), C, _] ⊗ Monomial[C, B, _]), Y])
    @scala.annotation.targetName("andThenTensoredStoreTensoredMonoToMono3")
    def andThen(
      w: PolyMap[(Monomial[(A, B), C, _] ⊗ Monomial[C, B, _]),  Monomial[A, A => C, _], Y]
    ): PolyMap[(Store[S1, _] ⊗ Store[S2, _]) ~> (Monomial[(A, B), C, _] ⊗ Monomial[C, B, _]), Monomial[A, A => C, _], Y] =
      new PolyMap[(Store[S1, _] ⊗ Store[S2, _]) ~> (Monomial[(A, B), C, _] ⊗ Monomial[C, B, _]), Monomial[A, A => C, _], Y]:
        def φ: PolyMap.Phi[(Store[S1, _] ⊗ Store[S2, _]) ~> (Monomial[(A, B), C, _] ⊗ Monomial[C, B, _]), Monomial[A, A => C, _], Y] =
          p.φ.andThen(w.φ)
        def `φ#`: PolyMap.PhiSharp[(Store[S1, _] ⊗ Store[S2, _]) ~> (Monomial[(A, B), C, _] ⊗ Monomial[C, B, _]), Monomial[A, A => C, _], Y] =
          (s, a) => p.`φ#`(s, w.`φ#`(p.φ(s), a))            

  extension [S1, S2, A1, B1, A2, B2, Y] (p: PolyMap[Store[S1, _] ⊗ Store[S2, _], Monomial[A1, B1, _] ⊗ Monomial[A2, B2, _], Y])
    @scala.annotation.targetName("andThenTensoredStoreTensoredMonoToTensoredMono")
    def andThen[A3, B3, A4, B4](
      w: PolyMap[Monomial[A1, B1, _] ⊗ Monomial[A2, B2, _], Monomial[A3, B3, _] ⊗ Monomial[A4, B4, _], Y]
    ): PolyMap[PolyMap[Store[S1, _] ⊗ Store[S2, _], Monomial[A1, B1, _] ⊗ Monomial[A2, B2, _], _], Monomial[A3, B3, _] ⊗ Monomial[A4, B4, _], Y] =
      new PolyMap[PolyMap[Store[S1, _] ⊗ Store[S2, _], Monomial[A1, B1, _] ⊗ Monomial[A2, B2, _], _], Monomial[A3, B3, _] ⊗ Monomial[A4, B4, _], Y]:
        def φ: PolyMap.Phi[PolyMap[Store[S1, _] ⊗ Store[S2, _], Monomial[A1, B1, _] ⊗ Monomial[A2, B2, _], _], Monomial[A3, B3, _] ⊗ Monomial[A4, B4, _], Y] =
          p.φ.andThen(w.φ)
        def `φ#`: PolyMap.PhiSharp[PolyMap[Store[S1, _] ⊗ Store[S2, _], Monomial[A1, B1, _] ⊗ Monomial[A2, B2, _], _], Monomial[A3, B3, _] ⊗ Monomial[A4, B4, _], Y] =
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
        def φ: PolyMap.Phi[PolyMap[(Store[S1, _] ⊗ Store[S2, _]), (Binomial[A1, B1, A2, B2, _] ⊗ Binomial[A3, B3, A4, B4, _]), _], Binomial[A1, A1 => B3, A2, A2 => B4, _], Y] =
          (
            s => a1 => p.φ._1(p.`φ#`._1(s, (a1, h(p.φ._1(s)._1))))._2,
            s => a2 => p.φ._2(p.`φ#`._2(s, (a2, i(p.φ._2(s)._1))))._2
          )
        def `φ#`: PolyMap.PhiSharp[PolyMap[(Store[S1, _] ⊗ Store[S2, _]), (Binomial[A1, B1, A2, B2, _] ⊗ Binomial[A3, B3, A4, B4, _]), _], Binomial[A1, A1 => B3, A2, A2 => B4, _], Y] =
          (
            (s: (S1, S2), a1: A1) => p.`φ#`._1(s, (a1, f(s._2))),
            (s: (S1, S2), a2: A2) => p.`φ#`._2(s, (a2, g(s._1)))
          )
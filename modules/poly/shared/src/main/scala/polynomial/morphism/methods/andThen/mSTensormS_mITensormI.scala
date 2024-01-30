package polynomial.morphism.methods.andThen

import polynomial.morphism.{PolyMap, ~>}
import polynomial.`object`.Monomial
import polynomial.product.⊗

object mSTensormS_mITensormI:

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

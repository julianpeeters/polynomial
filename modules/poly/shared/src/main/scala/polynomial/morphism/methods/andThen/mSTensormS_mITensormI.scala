package polynomial.morphism.methods.andThen

import polynomial.morphism.{PolyMap, ~>}
import polynomial.`object`.Monomial.{Interface, Store}
import polynomial.product.⊗

object mSTensormS_mITensormI:

  extension [S1, S2, A1, B1, A2, B2, Y] (p: PolyMap[Store[S1, _] ⊗ Store[S2, _], Interface[A1, B1, _] ⊗ Interface[A2, B2, _], Y])
    @scala.annotation.targetName("andThenTensoredStoreTensoredMonoToMonof")
    def andThen(
      w: PolyMap[Interface[A1, B1, _] ⊗ Interface[A2, B2, _], Interface[A1, A1 => B2, _], Y]
    ): PolyMap[PolyMap[Store[S1, _] ⊗ Store[S2, _], Interface[A1, B1, _] ⊗ Interface[A2, B2, _], _], Interface[A1, A1 => B2, _], Y] =
      new PolyMap[PolyMap[Store[S1, _] ⊗ Store[S2, _], Interface[A1, B1, _] ⊗ Interface[A2, B2, _], _], Interface[A1, A1 => B2, _], Y]:
        def φ: PolyMap.Phi[PolyMap[Store[S1, _] ⊗ Store[S2, _], Interface[A1, B1, _] ⊗ Interface[A2, B2, _], _], Interface[A1, A1 => B2, _], Y] =
          p.φ.andThen(w.φ)
        def `φ#`: PolyMap.PhiSharp[PolyMap[Store[S1, _] ⊗ Store[S2, _], Interface[A1, B1, _] ⊗ Interface[A2, B2, _], _], Interface[A1, A1 => B2, _], Y] =
          (s, a) => p.`φ#`(s, w.`φ#`(p.φ(s), a))

  extension [S1, S2, A1, B1, A2, B2, Y] (p: PolyMap[Store[S1, _] ⊗ Store[S2, _], Interface[A1, B1, _] ⊗ Interface[A2, B2, _], Y])
    @scala.annotation.targetName("andThenTensoredStoreTensoredMonoToMono")
    def andThen(
      w: PolyMap[Interface[A1, B1, _] ⊗ Interface[A2, B2, _], Interface[A1, B2, _], Y]
    ): PolyMap[PolyMap[Store[S1, _] ⊗ Store[S2, _], Interface[A1, B1, _] ⊗ Interface[A2, B2, _], _], Interface[A1, B2, _], Y] =
      new PolyMap[PolyMap[Store[S1, _] ⊗ Store[S2, _], Interface[A1, B1, _] ⊗ Interface[A2, B2, _], _], Interface[A1, B2, _], Y]:
        def φ: PolyMap.Phi[PolyMap[Store[S1, _] ⊗ Store[S2, _], Interface[A1, B1, _] ⊗ Interface[A2, B2, _], _], Interface[A1, B2, _], Y] =
          p.φ.andThen(w.φ)
        def `φ#`: PolyMap.PhiSharp[PolyMap[Store[S1, _] ⊗ Store[S2, _], Interface[A1, B1, _] ⊗ Interface[A2, B2, _], _], Interface[A1, B2, _], Y] =
          (s, a) => p.`φ#`(s, w.`φ#`(p.φ(s), a))

  extension [S1, S2, A, B, C, Y] (p: PolyMap[(Store[S1, _] ⊗ Store[S2, _]), (Interface[(A, B), C, _] ⊗ Interface[C, B, _]), Y])
    @scala.annotation.targetName("andThenTensoredStoreTensoredMonoToMono2")
    def andThen(
      w: PolyMap[(Interface[(A, B), C, _] ⊗ Interface[C, B, _]),  Interface[A, C, _], Y]
    ): PolyMap[(Store[S1, _] ⊗ Store[S2, _]) ~> (Interface[(A, B), C, _] ⊗ Interface[C, B, _]), Interface[A, C, _], Y] =
      new PolyMap[(Store[S1, _] ⊗ Store[S2, _]) ~> (Interface[(A, B), C, _] ⊗ Interface[C, B, _]), Interface[A, C, _], Y]:
        def φ: PolyMap.Phi[(Store[S1, _] ⊗ Store[S2, _]) ~> (Interface[(A, B), C, _] ⊗ Interface[C, B, _]), Interface[A, C, _], Y] =
          p.φ.andThen(w.φ)
        def `φ#`: PolyMap.PhiSharp[(Store[S1, _] ⊗ Store[S2, _]) ~> (Interface[(A, B), C, _] ⊗ Interface[C, B, _]), Interface[A, C, _], Y] =
          (s, a) => p.`φ#`(s, w.`φ#`(p.φ(s), a))

  extension [S1, S2, A, B, C, Y] (p: PolyMap[(Store[S1, _] ⊗ Store[S2, _]), (Interface[(A, B), C, _] ⊗ Interface[C, B, _]), Y])
    @scala.annotation.targetName("andThenTensoredStoreTensoredMonoToMono3")
    def andThen(
      w: PolyMap[(Interface[(A, B), C, _] ⊗ Interface[C, B, _]),  Interface[A, A => C, _], Y]
    ): PolyMap[(Store[S1, _] ⊗ Store[S2, _]) ~> (Interface[(A, B), C, _] ⊗ Interface[C, B, _]), Interface[A, A => C, _], Y] =
      new PolyMap[(Store[S1, _] ⊗ Store[S2, _]) ~> (Interface[(A, B), C, _] ⊗ Interface[C, B, _]), Interface[A, A => C, _], Y]:
        def φ: PolyMap.Phi[(Store[S1, _] ⊗ Store[S2, _]) ~> (Interface[(A, B), C, _] ⊗ Interface[C, B, _]), Interface[A, A => C, _], Y] =
          p.φ.andThen(w.φ)
        def `φ#`: PolyMap.PhiSharp[(Store[S1, _] ⊗ Store[S2, _]) ~> (Interface[(A, B), C, _] ⊗ Interface[C, B, _]), Interface[A, A => C, _], Y] =
          (s, a) => p.`φ#`(s, w.`φ#`(p.φ(s), a))

  extension [S1, S2, A1, B1, A2, B2, Y] (p: PolyMap[Store[S1, _] ⊗ Store[S2, _], Interface[A1, B1, _] ⊗ Interface[A2, B2, _], Y])
    @scala.annotation.targetName("andThenTensoredStoreTensoredMonoToTensoredMono")
    def andThen[A3, B3, A4, B4](
      w: PolyMap[Interface[A1, B1, _] ⊗ Interface[A2, B2, _], Interface[A3, B3, _] ⊗ Interface[A4, B4, _], Y]
    ): PolyMap[PolyMap[Store[S1, _] ⊗ Store[S2, _], Interface[A1, B1, _] ⊗ Interface[A2, B2, _], _], Interface[A3, B3, _] ⊗ Interface[A4, B4, _], Y] =
      new PolyMap[PolyMap[Store[S1, _] ⊗ Store[S2, _], Interface[A1, B1, _] ⊗ Interface[A2, B2, _], _], Interface[A3, B3, _] ⊗ Interface[A4, B4, _], Y]:
        def φ: PolyMap.Phi[PolyMap[Store[S1, _] ⊗ Store[S2, _], Interface[A1, B1, _] ⊗ Interface[A2, B2, _], _], Interface[A3, B3, _] ⊗ Interface[A4, B4, _], Y] =
          p.φ.andThen(w.φ)
        def `φ#`: PolyMap.PhiSharp[PolyMap[Store[S1, _] ⊗ Store[S2, _], Interface[A1, B1, _] ⊗ Interface[A2, B2, _], _], Interface[A3, B3, _] ⊗ Interface[A4, B4, _], Y] =
          (s, a) => p.`φ#`(s, w.`φ#`(p.φ(s), a))

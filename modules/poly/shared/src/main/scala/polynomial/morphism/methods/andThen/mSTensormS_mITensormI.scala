package polynomial.morphism.methods.andThen

import polynomial.morphism.{PolyMap, ~>}
import polynomial.`object`.Mono
import polynomial.product.⊗

object mSTensormS_mITensormI:

  extension [S1, S2, A1, B1, A2, B2, Y] (p: PolyMap[Mono.Store[S1, _] ⊗ Mono.Store[S2, _], Mono.Interface[A1, B1, _] ⊗ Mono.Interface[A2, B2, _], Y])
    @scala.annotation.targetName("andThenTensoredStoreTensoredMonoToMonof")
    def andThen(
      w: PolyMap[Mono.Interface[A1, B1, _] ⊗ Mono.Interface[A2, B2, _], Mono.Interface[A1, A1 => B2, _], Y]
    ): PolyMap[PolyMap[Mono.Store[S1, _] ⊗ Mono.Store[S2, _], Mono.Interface[A1, B1, _] ⊗ Mono.Interface[A2, B2, _], _], Mono.Interface[A1, A1 => B2, _], Y] =
      new PolyMap[PolyMap[Mono.Store[S1, _] ⊗ Mono.Store[S2, _], Mono.Interface[A1, B1, _] ⊗ Mono.Interface[A2, B2, _], _], Mono.Interface[A1, A1 => B2, _], Y]:
        def φ: PolyMap.Phi[PolyMap[Mono.Store[S1, _] ⊗ Mono.Store[S2, _], Mono.Interface[A1, B1, _] ⊗ Mono.Interface[A2, B2, _], _], Mono.Interface[A1, A1 => B2, _], Y] =
          p.φ.andThen(w.φ)
        def `φ#`: PolyMap.PhiSharp[PolyMap[Mono.Store[S1, _] ⊗ Mono.Store[S2, _], Mono.Interface[A1, B1, _] ⊗ Mono.Interface[A2, B2, _], _], Mono.Interface[A1, A1 => B2, _], Y] =
          (s, a) => p.`φ#`(s, w.`φ#`(p.φ(s), a))

  extension [S1, S2, A1, B1, A2, B2, Y] (p: PolyMap[Mono.Store[S1, _] ⊗ Mono.Store[S2, _], Mono.Interface[A1, B1, _] ⊗ Mono.Interface[A2, B2, _], Y])
    @scala.annotation.targetName("andThenTensoredStoreTensoredMonoToMono")
    def andThen(
      w: PolyMap[Mono.Interface[A1, B1, _] ⊗ Mono.Interface[A2, B2, _], Mono.Interface[A1, B2, _], Y]
    ): PolyMap[PolyMap[Mono.Store[S1, _] ⊗ Mono.Store[S2, _], Mono.Interface[A1, B1, _] ⊗ Mono.Interface[A2, B2, _], _], Mono.Interface[A1, B2, _], Y] =
      new PolyMap[PolyMap[Mono.Store[S1, _] ⊗ Mono.Store[S2, _], Mono.Interface[A1, B1, _] ⊗ Mono.Interface[A2, B2, _], _], Mono.Interface[A1, B2, _], Y]:
        def φ: PolyMap.Phi[PolyMap[Mono.Store[S1, _] ⊗ Mono.Store[S2, _], Mono.Interface[A1, B1, _] ⊗ Mono.Interface[A2, B2, _], _], Mono.Interface[A1, B2, _], Y] =
          p.φ.andThen(w.φ)
        def `φ#`: PolyMap.PhiSharp[PolyMap[Mono.Store[S1, _] ⊗ Mono.Store[S2, _], Mono.Interface[A1, B1, _] ⊗ Mono.Interface[A2, B2, _], _], Mono.Interface[A1, B2, _], Y] =
          (s, a) => p.`φ#`(s, w.`φ#`(p.φ(s), a))

  extension [S1, S2, A, B, C, Y] (p: PolyMap[(Mono.Store[S1, _] ⊗ Mono.Store[S2, _]), (Mono.Interface[(A, B), C, _] ⊗ Mono.Interface[C, B, _]), Y])
    @scala.annotation.targetName("andThenTensoredStoreTensoredMonoToMono2")
    def andThen(
      w: PolyMap[(Mono.Interface[(A, B), C, _] ⊗ Mono.Interface[C, B, _]),  Mono.Interface[A, C, _], Y]
    ): PolyMap[(Mono.Store[S1, _] ⊗ Mono.Store[S2, _]) ~> (Mono.Interface[(A, B), C, _] ⊗ Mono.Interface[C, B, _]), Mono.Interface[A, C, _], Y] =
      new PolyMap[(Mono.Store[S1, _] ⊗ Mono.Store[S2, _]) ~> (Mono.Interface[(A, B), C, _] ⊗ Mono.Interface[C, B, _]), Mono.Interface[A, C, _], Y]:
        def φ: PolyMap.Phi[(Mono.Store[S1, _] ⊗ Mono.Store[S2, _]) ~> (Mono.Interface[(A, B), C, _] ⊗ Mono.Interface[C, B, _]), Mono.Interface[A, C, _], Y] =
          p.φ.andThen(w.φ)
        def `φ#`: PolyMap.PhiSharp[(Mono.Store[S1, _] ⊗ Mono.Store[S2, _]) ~> (Mono.Interface[(A, B), C, _] ⊗ Mono.Interface[C, B, _]), Mono.Interface[A, C, _], Y] =
          (s, a) => p.`φ#`(s, w.`φ#`(p.φ(s), a))

  extension [S1, S2, A, B, C, Y] (p: PolyMap[(Mono.Store[S1, _] ⊗ Mono.Store[S2, _]), (Mono.Interface[(A, B), C, _] ⊗ Mono.Interface[C, B, _]), Y])
    @scala.annotation.targetName("andThenTensoredStoreTensoredMonoToMono3")
    def andThen(
      w: PolyMap[(Mono.Interface[(A, B), C, _] ⊗ Mono.Interface[C, B, _]),  Mono.Interface[A, A => C, _], Y]
    ): PolyMap[(Mono.Store[S1, _] ⊗ Mono.Store[S2, _]) ~> (Mono.Interface[(A, B), C, _] ⊗ Mono.Interface[C, B, _]), Mono.Interface[A, A => C, _], Y] =
      new PolyMap[(Mono.Store[S1, _] ⊗ Mono.Store[S2, _]) ~> (Mono.Interface[(A, B), C, _] ⊗ Mono.Interface[C, B, _]), Mono.Interface[A, A => C, _], Y]:
        def φ: PolyMap.Phi[(Mono.Store[S1, _] ⊗ Mono.Store[S2, _]) ~> (Mono.Interface[(A, B), C, _] ⊗ Mono.Interface[C, B, _]), Mono.Interface[A, A => C, _], Y] =
          p.φ.andThen(w.φ)
        def `φ#`: PolyMap.PhiSharp[(Mono.Store[S1, _] ⊗ Mono.Store[S2, _]) ~> (Mono.Interface[(A, B), C, _] ⊗ Mono.Interface[C, B, _]), Mono.Interface[A, A => C, _], Y] =
          (s, a) => p.`φ#`(s, w.`φ#`(p.φ(s), a))

  extension [S1, S2, A1, B1, A2, B2, Y] (p: PolyMap[Mono.Store[S1, _] ⊗ Mono.Store[S2, _], Mono.Interface[A1, B1, _] ⊗ Mono.Interface[A2, B2, _], Y])
    @scala.annotation.targetName("andThenTensoredStoreTensoredMonoToTensoredMono")
    def andThen[A3, B3, A4, B4](
      w: PolyMap[Mono.Interface[A1, B1, _] ⊗ Mono.Interface[A2, B2, _], Mono.Interface[A3, B3, _] ⊗ Mono.Interface[A4, B4, _], Y]
    ): PolyMap[PolyMap[Mono.Store[S1, _] ⊗ Mono.Store[S2, _], Mono.Interface[A1, B1, _] ⊗ Mono.Interface[A2, B2, _], _], Mono.Interface[A3, B3, _] ⊗ Mono.Interface[A4, B4, _], Y] =
      new PolyMap[PolyMap[Mono.Store[S1, _] ⊗ Mono.Store[S2, _], Mono.Interface[A1, B1, _] ⊗ Mono.Interface[A2, B2, _], _], Mono.Interface[A3, B3, _] ⊗ Mono.Interface[A4, B4, _], Y]:
        def φ: PolyMap.Phi[PolyMap[Mono.Store[S1, _] ⊗ Mono.Store[S2, _], Mono.Interface[A1, B1, _] ⊗ Mono.Interface[A2, B2, _], _], Mono.Interface[A3, B3, _] ⊗ Mono.Interface[A4, B4, _], Y] =
          p.φ.andThen(w.φ)
        def `φ#`: PolyMap.PhiSharp[PolyMap[Mono.Store[S1, _] ⊗ Mono.Store[S2, _], Mono.Interface[A1, B1, _] ⊗ Mono.Interface[A2, B2, _], _], Mono.Interface[A3, B3, _] ⊗ Mono.Interface[A4, B4, _], Y] =
          (s, a) => p.`φ#`(s, w.`φ#`(p.φ(s), a))

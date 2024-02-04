package polynomial.morphism.methods.andThen

import polynomial.morphism.PolyMap
import polynomial.`object`.Mono
import polynomial.product.⊗

object mSTensormSTensorms_mITensormITensormI:

  extension [S1, S2, S3, A1, B1, A2, B2, A3, B3, Y] (p: PolyMap[(Mono.Store[S1, _] ⊗ Mono.Store[S2, _] ⊗ Mono.Store[S3, _]), (Mono.Interface[A1, B1, _] ⊗ Mono.Interface[A2, B2, _] ⊗ Mono.Interface[A3, B3, _]), Y])
    @scala.annotation.targetName("andThenMsMsMstoMiMiMi")
    def andThen[I, O](
      w: PolyMap[(Mono.Interface[A1, B1, _] ⊗ Mono.Interface[A2, B2, _] ⊗ Mono.Interface[A3, B3, _]), Mono.Interface[I, I => O, _], Y],
    ): PolyMap[PolyMap[(Mono.Store[S1, _] ⊗ Mono.Store[S2, _] ⊗ Mono.Store[S3, _]), (Mono.Interface[A1, B1, _] ⊗ Mono.Interface[A2, B2, _] ⊗ Mono.Interface[A3, B3, _]), _], Mono.Interface[I, I => O, _], Y] =
      new PolyMap[PolyMap[(Mono.Store[S1, _] ⊗ Mono.Store[S2, _] ⊗ Mono.Store[S3, _]), (Mono.Interface[A1, B1, _] ⊗ Mono.Interface[A2, B2, _] ⊗ Mono.Interface[A3, B3, _]), _], Mono.Interface[I, I => O, _], Y]:
        def φ: PolyMap.Phi[PolyMap[(Mono.Store[S1, _] ⊗ Mono.Store[S2, _] ⊗ Mono.Store[S3, _]), (Mono.Interface[A1, B1, _] ⊗ Mono.Interface[A2, B2, _] ⊗ Mono.Interface[A3, B3, _]), _], Mono.Interface[I, I => O, _], Y] =
          p.φ.andThen(w.φ)
        def `φ#`: PolyMap.PhiSharp[PolyMap[(Mono.Store[S1, _] ⊗ Mono.Store[S2, _] ⊗ Mono.Store[S3, _]), (Mono.Interface[A1, B1, _] ⊗ Mono.Interface[A2, B2, _] ⊗ Mono.Interface[A3, B3, _]), _], Mono.Interface[I, I => O, _], Y] =
          (s, a) => p.`φ#`(s, w.`φ#`(p.φ(s), a))
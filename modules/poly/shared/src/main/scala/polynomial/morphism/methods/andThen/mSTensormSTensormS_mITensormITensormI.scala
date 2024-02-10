package polynomial.morphism.methods.andThen

import polynomial.morphism.PolyMap
import polynomial.`object`.Monomial.{Interface, Store}
import polynomial.product.⊗

object mSTensormSTensorms_mITensormITensormI:

  extension [S1, S2, S3, A1, B1, A2, B2, A3, B3, Y] (p: PolyMap[(Store[S1, _] ⊗ Store[S2, _] ⊗ Store[S3, _]), (Interface[A1, B1, _] ⊗ Interface[A2, B2, _] ⊗ Interface[A3, B3, _]), Y])
    @scala.annotation.targetName("andThenMsMsMstoMiMiMi")
    def andThen[I, O](
      w: PolyMap[(Interface[A1, B1, _] ⊗ Interface[A2, B2, _] ⊗ Interface[A3, B3, _]), Interface[I, I => O, _], Y],
    ): PolyMap[PolyMap[(Store[S1, _] ⊗ Store[S2, _] ⊗ Store[S3, _]), (Interface[A1, B1, _] ⊗ Interface[A2, B2, _] ⊗ Interface[A3, B3, _]), _], Interface[I, I => O, _], Y] =
      new PolyMap[PolyMap[(Store[S1, _] ⊗ Store[S2, _] ⊗ Store[S3, _]), (Interface[A1, B1, _] ⊗ Interface[A2, B2, _] ⊗ Interface[A3, B3, _]), _], Interface[I, I => O, _], Y]:
        def φ: PolyMap.Phi[PolyMap[(Store[S1, _] ⊗ Store[S2, _] ⊗ Store[S3, _]), (Interface[A1, B1, _] ⊗ Interface[A2, B2, _] ⊗ Interface[A3, B3, _]), _], Interface[I, I => O, _], Y] =
          p.φ.andThen(w.φ)
        def `φ#`: PolyMap.PhiSharp[PolyMap[(Store[S1, _] ⊗ Store[S2, _] ⊗ Store[S3, _]), (Interface[A1, B1, _] ⊗ Interface[A2, B2, _] ⊗ Interface[A3, B3, _]), _], Interface[I, I => O, _], Y] =
          (s, a) => p.`φ#`(s, w.`φ#`(p.φ(s), a))
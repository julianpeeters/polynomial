package polynomial.morphism.methods.andThen

import polynomial.morphism.PolyMap
import polynomial.`object`.Monomial
import polynomial.product.⊗

object mSTensormSTensorms_mITensormITensormI:

  extension [S1, S2, S3, A1, B1, A2, B2, A3, B3, Y] (p: PolyMap[(Monomial.Store[S1, _] ⊗ Monomial.Store[S2, _] ⊗ Monomial.Store[S3, _]), (Monomial.Interface[A1, B1, _] ⊗ Monomial.Interface[A2, B2, _] ⊗ Monomial.Interface[A3, B3, _]), Y])
    @scala.annotation.targetName("andThenMsMsMstoMiMiMi")
    def andThen[I, O](
      w: PolyMap[(Monomial.Interface[A1, B1, _] ⊗ Monomial.Interface[A2, B2, _] ⊗ Monomial.Interface[A3, B3, _]), Monomial.Interface[I, I => O, _], Y],
    ): PolyMap[PolyMap[(Monomial.Store[S1, _] ⊗ Monomial.Store[S2, _] ⊗ Monomial.Store[S3, _]), (Monomial.Interface[A1, B1, _] ⊗ Monomial.Interface[A2, B2, _] ⊗ Monomial.Interface[A3, B3, _]), _], Monomial.Interface[I, I => O, _], Y] =
      new PolyMap[PolyMap[(Monomial.Store[S1, _] ⊗ Monomial.Store[S2, _] ⊗ Monomial.Store[S3, _]), (Monomial.Interface[A1, B1, _] ⊗ Monomial.Interface[A2, B2, _] ⊗ Monomial.Interface[A3, B3, _]), _], Monomial.Interface[I, I => O, _], Y]:
        def φ: PolyMap.Phi[PolyMap[(Monomial.Store[S1, _] ⊗ Monomial.Store[S2, _] ⊗ Monomial.Store[S3, _]), (Monomial.Interface[A1, B1, _] ⊗ Monomial.Interface[A2, B2, _] ⊗ Monomial.Interface[A3, B3, _]), _], Monomial.Interface[I, I => O, _], Y] =
          p.φ.andThen(w.φ)
        def `φ#`: PolyMap.PhiSharp[PolyMap[(Monomial.Store[S1, _] ⊗ Monomial.Store[S2, _] ⊗ Monomial.Store[S3, _]), (Monomial.Interface[A1, B1, _] ⊗ Monomial.Interface[A2, B2, _] ⊗ Monomial.Interface[A3, B3, _]), _], Monomial.Interface[I, I => O, _], Y] =
          (s, a) => p.`φ#`(s, w.`φ#`(p.φ(s), a))
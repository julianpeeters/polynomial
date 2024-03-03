package polynomial.morphism.methods.andThen

import polynomial.morphism.PolyMap
import polynomial.`object`.Binomial.BiInterface
import polynomial.`object`.Monomial.Store
import polynomial.product.⊗

object mSTensormS_bITensorbI:

  extension [S1, S2, A1, B1, A2, B2, A3, B3, A4, B4, Y] (p: PolyMap[(Store[S1, _] ⊗ Store[S2, _]), (BiInterface[A1, B1, A2, B2, _] ⊗ BiInterface[A3, B3, A4, B4, _]), Y])
    @scala.annotation.targetName("andThenTensorStoreTensorBiToBi")
    def andThen(
      w: PolyMap[BiInterface[A1, B1, A2, B2, _] ⊗ BiInterface[A3, B3, A4, B4, _], BiInterface[A1, A1 => B3, A2, A2 => B4, _], Y],
    ): PolyMap[PolyMap[(Store[S1, _] ⊗ Store[S2, _]), (BiInterface[A1, B1, A2, B2, _] ⊗ BiInterface[A3, B3, A4, B4, _]), _], BiInterface[A1, A1 => B3, A2, A2 => B4, _], Y] =
      new PolyMap[PolyMap[(Store[S1, _] ⊗ Store[S2, _]), (BiInterface[A1, B1, A2, B2, _] ⊗ BiInterface[A3, B3, A4, B4, _]), _], BiInterface[A1, A1 => B3, A2, A2 => B4, _], Y]:
        def φ: PolyMap.Phi[PolyMap[(Store[S1, _] ⊗ Store[S2, _]), (BiInterface[A1, B1, A2, B2, _] ⊗ BiInterface[A3, B3, A4, B4, _]), _], BiInterface[A1, A1 => B3, A2, A2 => B4, _], Y] =
          (
            p.φ._1.andThen(w.φ._1),
            p.φ._2.andThen(w.φ._2),
          )
        def `φ#`: PolyMap.PhiSharp[PolyMap[(Store[S1, _] ⊗ Store[S2, _]), (BiInterface[A1, B1, A2, B2, _] ⊗ BiInterface[A3, B3, A4, B4, _]), _], BiInterface[A1, A1 => B3, A2, A2 => B4, _], Y] =
          (
            (s, a) => p.`φ#`._1(s, w.`φ#`._1(p.φ._1(s), a)),
            (s, a) => p.`φ#`._2(s, w.`φ#`._2(p.φ._2(s), a)),
          )

package polynomial.morphism.methods.andThen

import polynomial.morphism.PolyMap
import polynomial.`object`.Binomial.BiInterface
import polynomial.`object`.Monomial.Store

object mS_bI:

  extension [S, A1, B1, A2, B2, Y] (p: PolyMap[Store[S, _], BiInterface[A1, B1, A2, B2, _], Y])
    @scala.annotation.targetName("andThenStoreBiToBi")
    def andThen(
      w: PolyMap[BiInterface[A1, B1, A2, B2, _], BiInterface[A1, A1 => B1, A2, A2 => B2, _], Y]
    ): PolyMap[PolyMap[Store[S, _], BiInterface[A1, B1, A2, B2, _], _], BiInterface[A1, A1 => B1, A2, A2 => B2, _], Y] =
      new PolyMap[PolyMap[Store[S, _], BiInterface[A1, B1, A2, B2, _], _], BiInterface[A1, A1 => B1, A2, A2 => B2, _], Y]:
        def φ: PolyMap.Phi[PolyMap[Store[S, _], BiInterface[A1, B1, A2, B2, _], _], BiInterface[A1, A1 => B1, A2, A2 => B2, _], Y] =
          (p.φ._1.andThen(w.φ._1), p.φ._2.andThen(w.φ._2))
        def `φ#`: PolyMap.PhiSharp[PolyMap[Store[S, _], BiInterface[A1, B1, A2, B2, _], _], BiInterface[A1, A1 => B1, A2, A2 => B2, _], Y] =
          ((s, a) => p.`φ#`._1(s, a), (s, a) => p.`φ#`._2(s, a))  

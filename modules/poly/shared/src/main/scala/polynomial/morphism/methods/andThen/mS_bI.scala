package polynomial.morphism.methods.andThen

import polynomial.morphism.PolyMap
import polynomial.`object`.{Binomial, Monomial}

object mS_bI:

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

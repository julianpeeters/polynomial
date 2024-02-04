package polynomial.morphism.methods.andThen

import polynomial.morphism.PolyMap
import polynomial.`object`.{Bi, Mono}

object mS_bI:

  extension [S, A1, B1, A2, B2, Y] (p: PolyMap[Mono.Store[S, _], Bi.Interface[A1, B1, A2, B2, _], Y])
    @scala.annotation.targetName("andThenStoreBiToBi")
    def andThen(
      w: PolyMap[Bi.Interface[A1, B1, A2, B2, _], Bi.Interface[A1, A1 => B1, A2, A2 => B2, _], Y]
    ): PolyMap[PolyMap[Mono.Store[S, _], Bi.Interface[A1, B1, A2, B2, _], _], Bi.Interface[A1, A1 => B1, A2, A2 => B2, _], Y] =
      new PolyMap[PolyMap[Mono.Store[S, _], Bi.Interface[A1, B1, A2, B2, _], _], Bi.Interface[A1, A1 => B1, A2, A2 => B2, _], Y]:
        def φ: PolyMap.Phi[PolyMap[Mono.Store[S, _], Bi.Interface[A1, B1, A2, B2, _], _], Bi.Interface[A1, A1 => B1, A2, A2 => B2, _], Y] =
          (p.φ._1.andThen(w.φ._1), p.φ._2.andThen(w.φ._2))
        def `φ#`: PolyMap.PhiSharp[PolyMap[Mono.Store[S, _], Bi.Interface[A1, B1, A2, B2, _], _], Bi.Interface[A1, A1 => B1, A2, A2 => B2, _], Y] =
          ((s, a) => p.`φ#`._1(s, a), (s, a) => p.`φ#`._2(s, a))  

package polynomial

import polynomial.functor.{Dir, Pos}

object morphism:

  type ~>[P[_], Q[_]] = PolyMap[P, Q, _]

  abstract class PolyMap[P[_], Q[_], Y]:
    def φ: PolyMap.Phi[Pos[P, Y], Pos[Q, Y]]
    def `φ#`: PolyMap.PhiSharp[Pos[P, Y], Dir[Q, Y], Dir[P, Y]]

  object PolyMap:

    type Phi[PosPs, PosQs] = (PosPs, PosQs) match
      case ((s1, s2), (b1, b2)) => ((s1, s2) => b1, (s1, s2) => b2)
      case ((s1, s2), b)        => (s1, s2) => b
      case (s, (b1, b2))        => (s => b1, s => b2)
      case (s, b)               => s => b

    type PhiSharp[PosP, DirQ, DirP] = (PosP, DirQ, DirP) match
      case (s1, (a1, a2), s2) => ((s1, a1) => s2, (s1, a2) => s2)
      case (s1, a, s2)        => (s1, a) => s2
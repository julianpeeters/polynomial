package polynomial

import polynomial.functor.{Dir, Pos}
import polynomial.`object`.{Monomial, Store}
 
object morphism:

  type ~>[P[_], Q[_]] = PolyMap[P, Q, _]

  abstract class PolyMap[P[_], Q[_], Y]:
    def φ: PolyMap.Phi[Pos[P, Y], Pos[Q, Y]]
    def `φ#`: PolyMap.PhiSharp[Pos[P, Y], Dir[Q, Y], Dir[P, Y]]

  object PolyMap:

    def apply[P[_], Q[_], Y](
      phi: PolyMap.Phi[Pos[P, Y], Pos[Q, Y]],
      phiSharp: PolyMap.PhiSharp[Pos[P, Y], Dir[Q, Y], Dir[P, Y]]
    ): PolyMap[P, Q, Y] =
      new PolyMap[P, Q, Y]:
        def φ: Phi[Pos[P, Y], Pos[Q, Y]] = phi
        def `φ#`: PhiSharp[Pos[P, Y], Dir[Q, Y], Dir[P, Y]] = phiSharp
    
    extension [S, A, B, Y] (p: PolyMap[Store[S, _], Monomial[A, B, _], Y])
      def andThen[Q[_]](w: PolyMap[Monomial[A, B, _], Q, Y]): PolyMap[PolyMap[Store[S, _], Monomial[A, B, _], _], Q, Y] =
        new PolyMap[PolyMap[Store[S, _], Monomial[A, B, _], _], Q, Y]:
          def φ: Phi[Pos[PolyMap[Store[S, _], Monomial[A, B, _], _], Y], Pos[Q, Y]] =
            p.φ.andThen(w.φ)
          def `φ#`: PhiSharp[Pos[PolyMap[Store[S, _], Monomial[A, B, _], _], Y], Dir[Q, Y], Dir[PolyMap[Store[S, _], Monomial[A, B, _], _], Y]] =
            (s, a) => p.`φ#`(s, w.`φ#`(p.φ(s), a))

    type Phi[PosPs, PosQs] = (PosPs, PosQs) match
      case (s, b)               => s => b
      case ((s1, s2), (b1, b2)) => ((s1, s2) => b1, (s1, s2) => b2)
      case ((s1, s2), b)        => (s1, s2) => b
      case (s, (b1, b2))        => (s => b1, s => b2)
      case (s, a => b)          => s => a => b

    type PhiSharp[PosP, DirQ, DirP] = (PosP, DirQ, DirP) match
      case (s1, a, s2)        => (s1, a) => s2
      case (s1, (a1, a2), s2) => ((s1, a1) => s2, (s1, a2) => s2)
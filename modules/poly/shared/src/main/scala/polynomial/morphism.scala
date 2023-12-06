package polynomial

import polynomial.`object`.{Binomial, Monomial, Store}
 
object morphism:

  type ~>[P[_], Q[_]] = PolyMap[P, Q, _]

  abstract class PolyMap[P[_], Q[_], Y]:
    def φ: PolyMap.Phi[P, Q, Y]
    def `φ#`: PolyMap.PhiSharp[P, Q, Y]

  object PolyMap:

    def apply[P[_], Q[_], Y](
      phi: PolyMap.Phi[P, Q, Y],
      phiSharp: PolyMap.PhiSharp[P, Q, Y]
    ): PolyMap[P, Q, Y] =
      new PolyMap[P, Q, Y]:
        def φ: Phi[P, Q, Y] = phi
        def `φ#`: PhiSharp[P, Q, Y] = phiSharp
    
    extension [S, A1, B1, Y] (p: PolyMap[Store[S, _], Monomial[A1, B1, _], Y])
      def andThen[A2, B2](w: PolyMap[Monomial[A1, B1, _], Monomial[A2, B2, _], Y]): PolyMap[PolyMap[Store[S, _], Monomial[A1, B1, _], _], Monomial[A2, B2, _], Y] =
        new PolyMap[PolyMap[Store[S, _], Monomial[A1, B1, _], _], Monomial[A2, B2, _], Y]:
          def φ: PolyMap.Phi[PolyMap[Store[S, _], Monomial[A1, B1, _], _], Monomial[A2, B2, _], Y] =
            p.φ.andThen(w.φ)
          def `φ#`: PhiSharp[PolyMap[Store[S, _], Monomial[A1, B1, _], _], Monomial[A2, B2, _], Y] =
            (s, a) => p.`φ#`(s, w.`φ#`(p.φ(s), a))

    type Phi[P[_], Q[_], Y] = (P[Y], Q[Y]) match
      case (PolyMap[Store[s, _], Monomial[a1, b1, _], Y], Monomial[a2, b2, Y]) => s => b2
      case (Monomial[a1, b1, Y], Monomial[a2, b2, Y])                          => b1 => b2
      case (Store[s, Y], Monomial[a, b, Y])                                    => s => b
      case (Store[s, Y], Binomial[a1, b1, a2, b2, Y])                          => (s => b1, s => b2)

    type PhiSharp[P[_], Q[_], Y] = (P[Y], Q[Y]) match
      case (PolyMap[Store[s, _], Monomial[a1, b1, _], Y], Monomial[a2, b2, Y]) => (s, a2) => s
      case (Monomial[a1, b1, Y], Monomial[a2, b2, Y])                          => (b1, a2) => a1
      case (Store[s, Y], Monomial[a, b, Y])                                    => (s, a) => s
      case (Store[s, Y], Binomial[a1, b1, a2, b2, Y])                          => ((s, a1) => s, (s, a2) => s)

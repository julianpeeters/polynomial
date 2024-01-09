package polynomial.product

import polynomial.`object`.{Binomial, Monomial, Store}
import polynomial.morphism.PolyMap

type ⊗[P[_], Q[_]] = Tensor[P, Q, _]

abstract class Tensor[P[_], Q[_], Y]
object Tensor:
  
  type DayConvolution[P[_], Q[_], Y] = (P[Y], Q[Y]) match
    case (Binomial[a1, b1, a2, b2, Y], Binomial[a3, b3, a4, b4, Y])  => Binomial[(a1, a3), (b1, b3), (a2, a4), (b2, b4), Y]
    case (Monomial[a1, b1, Y], Monomial[a2, b2, Y])                  => Monomial[(a1, a2), (b1, b2), Y]
    case (PolyMap[o, p, Y], PolyMap[q, r, Y])                        => PolyMap[DayConvolution[o, q, _], DayConvolution[p, r, _], Y]
    case (Store[s1, Y], Store[s2, Y])                                => Store[(s1, s2), Y]





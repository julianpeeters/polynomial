package polynomial.product

import polynomial.`object`.{Binomial, Monomial}
import polynomial.morphism.PolyMap

type ⊗[P[_], Q[_]] = Tensor[P, Q, _]

abstract class Tensor[P[_], Q[_], Y]
object Tensor:
  
  type DayConvolution[P[_], Q[_], Y] = (P[Y], Q[Y]) match
    case (Binomial.Interface[a1, b1, a2, b2, Y], Binomial.Interface[a3, b3, a4, b4, Y]) => Binomial.Interface[(a1, a3), (b1, b3), (a2, a4), (b2, b4), Y]
    case (Monomial.Interface[a1, b1, Y], Monomial.Interface[a2, b2, Y])                 => Monomial.Interface[(a1, a2), (b1, b2), Y]
    case (Monomial.Store[s1, Y], Monomial.Store[s2, Y])                                 => Monomial.Store[(s1, s2), Y]
    case (PolyMap[o, p, Y], PolyMap[q, r, Y])                                           => PolyMap[DayConvolution[o, q, _], DayConvolution[p, r, _], Y]
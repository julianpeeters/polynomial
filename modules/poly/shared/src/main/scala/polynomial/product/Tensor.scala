package polynomial.product

import polynomial.morphism.PolyMap
import polynomial.`object`.Binomial.BiInterface
import polynomial.`object`.Monomial.{Interface, Store}

type ⊗[P[_], Q[_]] = Tensor[P, Q, _]

abstract class Tensor[P[_], Q[_], Y]
object Tensor:
  
  type DayConvolution[P[_], Q[_], Y] = (P[Y], Q[Y]) match
    case (BiInterface[a1, b1, a2, b2, Y], BiInterface[a3, b3, a4, b4, Y]) => BiInterface[(a1, a3), (b1, b3), (a2, a4), (b2, b4), Y]
    case (Interface[a1, b1, Y], BiInterface[a2, b2, a3, b3, Y])           => BiInterface[(a1, a2), (b1, b2), (a1, a3), (b1, b3), Y]
    case (Interface[a1, b1, Y], Interface[a2, b2, Y])                     => Interface[(a1, a2), (b1, b2), Y]
    case (Store[s1, Y], Store[s2, Y])                                     => Store[(s1, s2), Y]
    case (PolyMap[o, p, Y], PolyMap[q, r, Y])                             => PolyMap[DayConvolution[o, q, _], DayConvolution[p, r, _], Y]
    case (Tensor[o, p, Y], BiInterface[a1, b1, a2, b2, Y])                => DayConvolution[DayConvolution[o, p, _], BiInterface[a1, b1, a2, b2, _], Y]
    case (Tensor[o, p, Y], Interface[a1, b1, Y])                          => DayConvolution[DayConvolution[o, p, _], Interface[a1, b1, _], Y]
    case (Tensor[o, p, Y], Store[s1, Y])                                  => DayConvolution[DayConvolution[o, p, _], Store[s1, _], Y]
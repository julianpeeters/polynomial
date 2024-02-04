package polynomial.product

import polynomial.morphism.PolyMap
import polynomial.`object`.{Bi, Mono}

type ⊗[P[_], Q[_]] = Tensor[P, Q, _]

abstract class Tensor[P[_], Q[_], Y]
object Tensor:
  
  type DayConvolution[P[_], Q[_], Y] = (P[Y], Q[Y]) match
    case (Bi.Interface[a1, b1, a2, b2, Y], Bi.Interface[a3, b3, a4, b4, Y]) => Bi.Interface[(a1, a3), (b1, b3), (a2, a4), (b2, b4), Y]
    case (Mono.Interface[a1, b1, Y], Bi.Interface[a2, b2, a3, b3, Y])         => Bi.Interface[(a1, a2), (b1, b2), (a1, a3), (b1, b3), Y]
    case (Mono.Interface[a1, b1, Y], Mono.Interface[a2, b2, Y])                 => Mono.Interface[(a1, a2), (b1, b2), Y]
    case (Mono.Store[s1, Y], Mono.Store[s2, Y])                                 => Mono.Store[(s1, s2), Y]
    case (PolyMap[o, p, Y], PolyMap[q, r, Y])                                           => PolyMap[DayConvolution[o, q, _], DayConvolution[p, r, _], Y]
    case (Tensor[o, p, Y], Bi.Interface[a1, b1, a2, b2, Y])                       => DayConvolution[DayConvolution[o, p, _], Bi.Interface[a1, b1, a2, b2, _], Y]
    case (Tensor[o, p, Y], Mono.Interface[a1, b1, Y])                               => DayConvolution[DayConvolution[o, p, _], Mono.Interface[a1, b1, _], Y]
    case (Tensor[o, p, Y], Mono.Store[s1, Y])                                       => DayConvolution[DayConvolution[o, p, _], Mono.Store[s1, _], Y]
package polynomial.product

import polynomial.`object`.Mono

type ◁[P[_], Q[_]] = Composition[P, Q, _]

abstract class Composition[P[_], Q[_], Y]
object Composition:

  type Decompose[P[_], Q[_], Y] = (P[Y], Q[Y]) match
    case (Mono.Interface[a1, b1, Y], Mono.Interface[a2, b2, Y]) => (b1, b2)
    case (Mono.Store[s1, Y], Mono.Store[s2, Y])                 => (s1, s2)

  type DecomposeSharp[P[_], Q[_], Y] = (P[Y], Q[Y]) match
    case (Mono.Interface[a1, b1, Y], Mono.Interface[a2, b2, Y]) => (a1, a2)
    case (Mono.Store[s1, Y], Mono.Store[s2, Y])                 => (s1, s2)
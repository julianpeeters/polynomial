package polynomial.product

import polynomial.`object`.Monomial.{Interface, Store}

type ×[P[_], Q[_]] = Cartesian[P, Q, _]

abstract class Cartesian[P[_], Q[_], Y]
object Cartesian:

  type And[P[_], Q[_], Y] = (P[Y], Q[Y]) match
    case (Interface[a1, b1, Y], Interface[a2, b2, Y]) => Interface[Either[a1, a2], (b1, b2), Y]
    case (Interface[a1, b1, Y], Store[s, Y])          => Interface[Either[a1, s], (b1, s), Y]
    case (Store[s, Y], Interface[a2, b2, Y])          => Interface[Either[s, a2], (s, b2), Y]
    case (Store[s1, Y], Store[s2, Y])                 => Interface[Either[s1, s2], (s1, s2), Y]
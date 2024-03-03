package polynomial.product

import polynomial.`object`.Monomial.Interface

type ×[P[_], Q[_]] = Cartesian[P, Q, _]

abstract class Cartesian[P[_], Q[_], Y]
object Cartesian:

  type And[P[_], Q[_], Y] = (P[Y], Q[Y]) match
    case (Interface[a1, b1, Y], Interface[a2, b2, Y]) => (b1, b2)

  type AndSharp[P[_], Q[_], Y] = (P[Y], Q[Y]) match
    case (Interface[a1, b1, Y], Interface[a2, b2, Y]) => Either[a1, a2]

package polynomial

import polynomial.morphism.PolyMap
import polynomial.`object`.{Monomial, Binomial, Trinomial, Store}

type Dir[P[_], Y] = P[Y] match
  case PolyMap[p, q, Y]                     => Dir[p, Y]
  case Monomial[a1, b1, Y]                  => a1
  case Binomial[a1, b1, a2, b2, Y]          => (a1, a2)
  case Trinomial[a1, b1, a2, b2, a3, b3, Y] => (a1, a2, a3)
  case Store[s, Y]                          => s

type Pos[P[_], Y] = P[Y] match
  case PolyMap[p, q, Y]                     => Pos[p, Y]
  case Monomial[a1, b1, Y]                  => b1
  case Binomial[a1, b1, a2, b2, Y]          => (b1, b2)
  case Trinomial[a1, b1, a2, b2, a3, b3, Y] => (b1, b2, b3)
  case Store[s, Y]                          => s


package polynomial

import polynomial.`object`.{Monomial, Store}
import polynomial.morphism.PolyMap

type ⊗[P[_], Q[_]] = Tensor[P, Q, _]

type Tensor[P[_], Q[_], A] = (P[A], Q[A]) match
  case (Store[s1, _] ⊗ Store[s2, _])                                                                  => Store[(s1, s2), A]
  case (Store[s1, A], Store[s2, A])                                                                   => Store[(s1, s2), A]
  case (Monomial[a1, b1, A], Monomial[a2, b2, A])                                                     => Monomial[(a1, a2), (b1, b2), A]
  case (PolyMap[Store[s1, _], Monomial[a1, b1, _], A], PolyMap[Store[s2, _], Monomial[a2, b2, _], A]) => PolyMap[Store[(s1, s2), _], Monomial[(a1, a2), (b1, b2), _], A]
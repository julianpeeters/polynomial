package polynomial.morphism

import polynomial.`object`.{Binomial, Monomial}
import polynomial.product.{Tensor, ⊗}

type ~>[P[_], Q[_]] = PolyMap[P, Q, _]

abstract class PolyMap[P[_], Q[_], Y]:
  def φ: PolyMap.Phi[P, Q, Y]
  def `φ#`: PolyMap.PhiSharp[P, Q, Y]

object PolyMap:

  export polynomial.morphism.methods.andThen.*
  export polynomial.morphism.methods.swap.*

  def apply[P[_], Q[_], Y](
    phi: Phi[P, Q, Y],
    phiSharp: PhiSharp[P, Q, Y]
  ): PolyMap[P, Q, Y] =
    new PolyMap[P, Q, Y]:
      def φ: Phi[P, Q, Y] = phi
      def `φ#`: PhiSharp[P, Q, Y] = phiSharp

  type Phi[P[_], Q[_], Y] = (P[Y], Q[Y]) match
    case (Binomial.Interface[a1, b1, a2, b2, Y], Binomial.Interface[a3, b3, a4, b4, Y]) => (b1 => b3, b2 => b4)
    case (Binomial.Interface[a1, b1, a2, b2, Y], Monomial.Interface[a3, b3, Y])         => (b1 => b3, b2 => b3) 
    case (Monomial.Interface[a1, b1, Y], Binomial.Interface[a3, b3, a4, b4, Y])         => (b1 => b3, b1 => b4)
    case (Monomial.Interface[a1, b1, Y], Monomial.Interface[a2, b2, Y])                 => b1 => b2
    case (PolyMap[p, q, Y], Binomial.Interface[a3, b3, a4, b4, Y])                      => Phi[p, Binomial.Interface[a3, b3, a4, b4, _], Y]
    case (PolyMap[p, q, Y], Monomial.Interface[a2, b2, Y])                              => Phi[p, Monomial.Interface[a2, b2, _], Y]
    case (PolyMap[o, p, Y], Tensor[q, r, Y])                                            => Phi[o, Tensor[q, r, _], Y]
    case (Monomial.Store[s, Y], Binomial.Interface[a1, b1, a2, b2, Y])                  => (s => b1, s => b2)
    case (Monomial.Store[s, Y], Monomial.Interface[a, b, Y])                            => s => b
    case (Tensor[p, q, Y], Monomial.Interface[a1, b1, Y])                               => Phi[Tensor.DayConvolution[p, q, _], Monomial.Interface[a1, b1, _], Y]
    case (Tensor[p, q, Y], Binomial.Interface[a1, b1, a2, b2, Y])                       => Phi[Tensor.DayConvolution[p, q, _], Binomial.Interface[a1, b1, a2, b2, _], Y]
    case (Tensor[o, p, Y], Tensor[q, r, Y])                                             => Phi[Tensor.DayConvolution[o, p, _], Tensor.DayConvolution[q, r, _], Y]
    
  type PhiSharp[P[_], Q[_], Y] = (P[Y], Q[Y]) match
    case (Binomial.Interface[a1, b1, a2, b2, Y], Binomial.Interface[a3, b3, a4, b4, Y]) => ((b1, a3) => a1, (b2, a4) => a2)
    case (Binomial.Interface[a1, b1, a2, b2, Y], Monomial.Interface[a3, b3, Y])         => ((b1, a3) => a1, (b2, a3) => a2)
    case (Monomial.Interface[a1, b1, Y], Binomial.Interface[a2, b2, a3, b3, Y])         => ((b1, a2) => a1, (b1, a3) => a1)
    case (Monomial.Interface[a1, b1, Y], Monomial.Interface[a2, b2, Y])                 => (b1, a2) => a1
    case (Monomial.Store[s, Y], Binomial.Interface[a1, b1, a2, b2, Y])                  => ((s, a1) => s, (s, a2) => s)
    case (Monomial.Store[s, Y], Monomial.Interface[a, b, Y])                            => (s, a) => s
    case (PolyMap[p, q, Y], Binomial.Interface[a3, b3, a4, b4, Y])                      => PhiSharp[p, Binomial.Interface[a3, b3, a4, b4, _], Y]
    case (PolyMap[p, q, Y], Monomial.Interface[a2, b2, Y])                              => PhiSharp[p, Monomial.Interface[a2, b2, _], Y]
    case (PolyMap[o, p, Y], Tensor[q, r, Y])                                            => PhiSharp[o, Tensor[q, r, _], Y]
    case (Tensor[p, q, Y], Binomial.Interface[a1, b1, a2, b2, Y])                       => PhiSharp[Tensor.DayConvolution[p, q, _], Binomial.Interface[a1, b1, a2, b2, _], Y]
    case (Tensor[p, q, Y], Monomial.Interface[a1, b1, Y])                               => PhiSharp[Tensor.DayConvolution[p, q, _], Monomial.Interface[a1, b1, _], Y]
    case (Tensor[o, p, Y], Tensor[q, r, Y])                                             => PhiSharp[Tensor.DayConvolution[o, p, _], Tensor.DayConvolution[q, r, _], Y]
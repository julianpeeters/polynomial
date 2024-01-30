package polynomial.morphism

import cats.Id
import polynomial.`object`.{Binomial, Monomial}
import polynomial.product.{Tensor, Composition, ⊗}

type ~>[P[_], Q[_]] = PolyMap[P, Q, _]

abstract class PolyMap[P[_], Q[_], Y]:
  def φ: PolyMap.Phi[P, Q, Y]
  def `φ#`: PolyMap.PhiSharp[P, Q, Y]

object PolyMap:

  export polynomial.morphism.methods.andThen.mS_bI.*
  export polynomial.morphism.methods.andThen.mS_mI.*
  export polynomial.morphism.methods.andThen.mSTensormS_bITensorbI.*
  export polynomial.morphism.methods.andThen.mSTensormS_mITensormI.*
  export polynomial.morphism.methods.andThen.mSTensormSTensorms_mITensormITensormI.*
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
    // case (Monomial.Store[s, Y], Monomial.Interface[a, b, Y])                            => s => b
    case (Monomial.Store[s, Y], Monomial.Interface[a, b, Y])                            => Tambara[s, b]
    case (Monomial.Store[s, Y], Composition[p, q, Y])                                   => (Phi[Monomial.Store[s, _], p, Y], Phi[p, q, Y])
    case (Tensor[p, q, Y], Monomial.Store[s1, Y])                                       => Phi[Tensor.DayConvolution[p, q, _], Monomial.Store[s1, _], Y]
    case (Tensor[p, q, Y], Monomial.Interface[a1, b1, Y])                               => Phi[Tensor.DayConvolution[p, q, _], Monomial.Interface[a1, b1, _], Y]
    case (Tensor[p, q, Y], Binomial.Interface[a1, b1, a2, b2, Y])                       => Phi[Tensor.DayConvolution[p, q, _], Binomial.Interface[a1, b1, a2, b2, _], Y]
    case (Tensor[o, p, Y], Tensor[q, r, Y])                                             => Phi[Tensor.DayConvolution[o, p, _], Tensor.DayConvolution[q, r, _], Y]

  type PhiSharp[P[_], Q[_], Y] = (P[Y], Q[Y]) match
    case (Binomial.Interface[a1, b1, a2, b2, Y], Binomial.Interface[a3, b3, a4, b4, Y]) => ((b1, a3) => a1, (b2, a4) => a2)
    case (Binomial.Interface[a1, b1, a2, b2, Y], Monomial.Interface[a3, b3, Y])         => ((b1, a3) => a1, (b2, a3) => a2)
    case (Monomial.Interface[a1, b1, Y], Binomial.Interface[a2, b2, a3, b3, Y])         => ((b1, a2) => a1, (b1, a3) => a1)
    case (Monomial.Interface[a1, b1, Y], Monomial.Interface[a2, b2, Y])                 => (b1, a2) => a1
    case (Monomial.Store[s, Y], Binomial.Interface[a1, b1, a2, b2, Y])                  => ((s, a1) => s, (s, a2) => s)
    // case (Monomial.Store[s, Y], Monomial.Interface[a, b, Y])                            => (s, a) => s
    case (Monomial.Store[s, Y], Monomial.Interface[a, b, Y])                            => TambaraSharp[s, a]
    case (Monomial.Store[s, Y], Composition[p, q, Y])                                   => (s, Composition.DecomposeSharp[p, q, Y]) => s
    case (PolyMap[p, q, Y], Binomial.Interface[a3, b3, a4, b4, Y])                      => PhiSharp[p, Binomial.Interface[a3, b3, a4, b4, _], Y]
    case (PolyMap[p, q, Y], Monomial.Interface[a2, b2, Y])                              => PhiSharp[p, Monomial.Interface[a2, b2, _], Y]
    case (PolyMap[o, p, Y], Tensor[q, r, Y])                                            => PhiSharp[o, Tensor[q, r, _], Y]
    case (Tensor[p, q, Y], Binomial.Interface[a1, b1, a2, b2, Y])                       => PhiSharp[Tensor.DayConvolution[p, q, _], Binomial.Interface[a1, b1, a2, b2, _], Y]
    case (Tensor[p, q, Y], Monomial.Interface[a1, b1, Y])                               => PhiSharp[Tensor.DayConvolution[p, q, _], Monomial.Interface[a1, b1, _], Y]
    case (Tensor[p, q, Y], Monomial.Store[s1, Y])                                       => PhiSharp[Tensor.DayConvolution[p, q, _], Monomial.Store[s1, _], Y]
    case (Tensor[o, p, Y], Tensor[q, r, Y])                                             => PhiSharp[Tensor.DayConvolution[o, p, _], Tensor.DayConvolution[q, r, _], Y]

  type Tambara[S, B] = (S, B) match
    // case ((c, t), B) => S => (c, B)
    // case (Either[c, t], B) => S => Either[t, B]
    case (Id[s], Id[b]) => s => b
    case (Option[s], Id[b]) => Option[s] => b
    case (Option[s], Option[b]) => Option[s] => Option[b]
    // case (S, B) => S => B


  type TambaraSharp[S, A] = (S, A) match
    // case ((c, t), A) => (S, (A, c)) => S
    // case (Option[s], A) => (S, Option[A]) => Option[s]
    // case (Either[c, t], A) => (S, Either[t, A]) => t
    case (Id[s], A) => (s, A) => s 
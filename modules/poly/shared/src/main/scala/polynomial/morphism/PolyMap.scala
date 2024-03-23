package polynomial.morphism

import cats.Id
import polynomial.`object`.Binomial.BiInterface
import polynomial.`object`.Monomial.{Interface, Store, StoreF}
import polynomial.product.{Cartesian, Composition, Tensor}

type ~>[P[_], Q[_]] = PolyMap[P, Q, _]

abstract class PolyMap[P[_], Q[_], Y]:
  def φ: PolyMap.Phi[P, Q, Y]
  def `φ#`: PolyMap.PhiSharp[P, Q, Y]

object PolyMap:

  def apply[P[_], Q[_], Y](
    phi: Phi[P, Q, Y],
    phiSharp: PhiSharp[P, Q, Y]
  ): PolyMap[P, Q, Y] =
    new PolyMap[P, Q, Y]:
      def φ: Phi[P, Q, Y] = phi
      def `φ#`: PhiSharp[P, Q, Y] = phiSharp

  type Phi[P[_], Q[_], Y] = (Eval[P, Y], Eval[Q, Y]) match
    case (BiInterface[a1, b1, a2, b2, Y], BiInterface[a3, b3, a4, b4, Y]) => (b1 => b3, b2 => b4)
    case (BiInterface[a1, b1, a2, b2, Y], Interface[a3, b3, Y])           => (b1 => b3, b2 => b3)
    case (Interface[a1, b1, Y], BiInterface[a3, b3, a4, b4, Y])           => (b1 => b3, b1 => b4)
    case (Interface[a1, b1, Y], Interface[a2, b2, Y])                     => b1 => b2
    case (PolyMap[p, q, Y], BiInterface[a3, b3, a4, b4, Y])               => Phi[p, BiInterface[a3, b3, a4, b4, _], Y]
    case (PolyMap[p, q, Y], Interface[a2, b2, Y])                         => Phi[p, Interface[a2, b2, _], Y]
    case (PolyMap[o, p, Y], Tensor[q, r, Y])                              => Phi[o, Tensor[q, r, _], Y]
    case (Store[s, Y], BiInterface[a1, b1, a2, b2, Y])                    => (s => b1, s => b2)
    case (Store[s, Y], Interface[a, b, Y])                                => s => b
    case (StoreF[f, s, Y], Interface[a, b, Y])                            => s => b
    case (Store[s, Y], Composition[p, q, Y])                              => (Phi[Store[s, _], p, Y], Phi[p, q, Y])

  type PhiSharp[P[_], Q[_], Y] = (Eval[P, Y], Eval[Q, Y]) match
    case (BiInterface[a1, b1, a2, b2, Y], BiInterface[a3, b3, a4, b4, Y]) => ((b1, a3) => a1, (b2, a4) => a2)
    case (BiInterface[a1, b1, a2, b2, Y], Interface[a3, b3, Y])           => ((b1, a3) => a1, (b2, a3) => a2)
    case (Interface[a1, b1, Y], BiInterface[a2, b2, a3, b3, Y])           => ((b1, a2) => a1, (b1, a3) => a1)
    case (Interface[a1, b1, Y], Interface[a2, b2, Y])                     => (b1, a2) => a1
    case (Store[s, Y], BiInterface[a1, b1, a2, b2, Y])                    => ((s, a1) => s, (s, a2) => s)
    case (Store[s, Y], Interface[a, b, Y])                                => (s, a) => s
    case (StoreF[f, s, Y], Interface[a, b, Y])                            => (s, a) => f[s]
    case (Store[s, Y], Composition[p, q, Y])                              => (s, Composition.DecomposeSharp[p, q, Y]) => s
    case (PolyMap[p, q, Y], BiInterface[a3, b3, a4, b4, Y])               => PhiSharp[p, BiInterface[a3, b3, a4, b4, _], Y]
    case (PolyMap[p, q, Y], Interface[a2, b2, Y])                         => PhiSharp[p, Interface[a2, b2, _], Y]

  type Eval[P[_], Y] = P[Y] match
    case BiInterface[a1, b1, a2, b2, Y] => BiInterface[a1, b1, a2, b2, Y]
    case Cartesian[p, q, Y]             => Cartesian.And[Eval[p, _], Eval[q, _], Y]
    case Composition[p, q, Y]           => Composition[p, q, Y]
    case Interface[a, b, Y]             => Interface[a, b, Y]
    case PolyMap[p, q, Y]               => PolyMap[p, q, Y]
    case Store[s, Y]                    => Store[s, Y]
    case StoreF[f, s, Y]                => StoreF[f, s, Y]
    case Tensor[p, q, Y]                => Tensor.DayConvolution[Eval[p, _], Eval[q, _], Y]

  export polynomial.morphism.methods.andThen.mS_bI.*
  export polynomial.morphism.methods.andThen.mS_mI.*
  export polynomial.morphism.methods.andThen.mS_mICartesianmI.*
  export polynomial.morphism.methods.andThen.mSTensormS_bITensorbI.*
  export polynomial.morphism.methods.andThen.mSTensormS_mITensormI.*
  export polynomial.morphism.methods.andThen.mSTensormSTensorms_mITensormITensormI.*
  export polynomial.morphism.methods.swap.*
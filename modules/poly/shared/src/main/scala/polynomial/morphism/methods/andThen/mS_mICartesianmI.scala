package polynomial.morphism.methods.andThen

import cats.Id
import polynomial.morphism.PolyMap
import polynomial.`object`.Monomial.{Interface, Store}
import polynomial.product.×

object mS_mICartesianmI:

  extension [S, A1, B1, A2, B2, Y] (p: PolyMap[Store[Id[S], _], Interface[A1, B1, _] × Interface[A2, B2, _], Y])
    @scala.annotation.targetName("andThenMealifiedStoreToCartesianMonoMono")
    def andThen(
      w: PolyMap[Interface[A1, B1, _] × Interface[A2, B2, _], Interface[Either[A1, A2], Either[A1, A2] => (B1, B2), _], Y]
    ): PolyMap[PolyMap[Store[Id[S], _], Interface[A1, B1, _] × Interface[A2, B2, _], _], Interface[Either[A1, A2], Either[A1, A2] => (B1, B2), _], Y] =
      new PolyMap[PolyMap[Store[Id[S], _], Interface[A1, B1, _] × Interface[A2, B2, _], _], Interface[Either[A1, A2], Either[A1, A2] => (B1, B2), _], Y]:
        def φ: PolyMap.Phi[PolyMap[Store[Id[S], _], Interface[A1, B1, _] × Interface[A2, B2, _], _], Interface[Either[A1, A2], Either[A1, A2] => (B1, B2), _], Y] =
          p.φ.andThen(w.φ)
        def `φ#`: PolyMap.PhiSharp[PolyMap[Store[Id[S], _], Interface[A1, B1, _] × Interface[A2, B2, _], _], Interface[Either[A1, A2], Either[A1, A2] => (B1, B2), _], Y] =
          (s, a) => p.`φ#`(s, w.`φ#`(p.φ(s), a))
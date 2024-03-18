package polynomial.morphism.methods.andThen

import cats.Id
import polynomial.morphism.PolyMap
import polynomial.`object`.Monomial.{Interface, Store}
import cats.FlatMap

object mS_mI:

  extension [S, A1, B1, Y] (p: PolyMap[Store[Id[S], _], Interface[Id[A1], Id[B1], _], Y])
    @scala.annotation.targetName("andThenStoreMonoToMono")
    def andThen[A2, B2](
      w: PolyMap[Interface[Id[A1], Id[B1], _], Interface[Id[A2], Id[B2], _], Y]
    ): PolyMap[PolyMap[Store[Id[S], _], Interface[Id[A1], Id[B1], _], _], Interface[Id[A2], Id[B2], _], Y] =
      new PolyMap[PolyMap[Store[Id[S], _], Interface[Id[A1], Id[B1], _], _], Interface[Id[A2], Id[B2], _], Y]:
        def φ: PolyMap.Phi[PolyMap[Store[Id[S], _], Interface[Id[A1], Id[B1], _], _], Interface[Id[A2], Id[B2], _], Y] =
          p.φ.andThen(w.φ)
        def `φ#`: PolyMap.PhiSharp[PolyMap[Store[Id[S], _], Interface[Id[A1], Id[B1], _], _], Interface[Id[A2], Id[B2], _], Y] =
          (s, a) => p.`φ#`(s, w.`φ#`(p.φ(s), a))

  extension [F[_]: FlatMap, S, A, B, Y] (p: PolyMap[Store[F[S], _], Interface[A, F[B], _], Y])
    @scala.annotation.targetName("andThenStoreMonoToMonoFPrism")
    def andThen(
      w: PolyMap[Interface[A, Id[B], _], Interface[A, A => F[B], _], Y]
    ): PolyMap[PolyMap[Store[F[S], _], Interface[A, Id[B], _], _], Interface[A, A => F[B], _], Y] =
      new PolyMap[PolyMap[Store[F[S], _], Interface[A, Id[B], _], _], Interface[A, A => F[B], _], Y]:
        def φ: PolyMap.Phi[PolyMap[Store[F[S], _], Interface[A, Id[B], _], _], Interface[A, A => F[B], _], Y] =
          s => a2 => FlatMap[F].flatMap(p.φ(s))(b1 => w.φ(b1)(a2))
        def `φ#`: PolyMap.PhiSharp[PolyMap[Store[F[S], _], Interface[A, Id[B], _], _], Interface[A, A => F[B], _], Y] =
          (s, a) => FlatMap[F].flatMap(p.φ(s))(b1 => p.`φ#`(s, w.`φ#`(b1, a)))
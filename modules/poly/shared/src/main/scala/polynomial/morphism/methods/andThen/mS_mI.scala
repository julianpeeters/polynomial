package polynomial.morphism.methods.andThen

import cats.Id
import polynomial.morphism.PolyMap
import polynomial.`object`.Mono

object mS_mI:

  extension [S, A1, B1, Y] (p: PolyMap[Mono.Store[Id[S], _], Mono.Interface[Id[A1], Id[B1], _], Y])
    @scala.annotation.targetName("andThenStoreMonoToMono")
    def andThen[A2, B2](
      w: PolyMap[Mono.Interface[Id[A1], Id[B1], _], Mono.Interface[Id[A2], Id[B2], _], Y]
    ): PolyMap[PolyMap[Mono.Store[Id[S], _], Mono.Interface[Id[A1], Id[B1], _], _], Mono.Interface[Id[A2], Id[B2], _], Y] =
      new PolyMap[PolyMap[Mono.Store[Id[S], _], Mono.Interface[Id[A1], Id[B1], _], _], Mono.Interface[Id[A2], Id[B2], _], Y]:
        def φ: PolyMap.Phi[PolyMap[Mono.Store[Id[S], _], Mono.Interface[Id[A1], Id[B1], _], _], Mono.Interface[Id[A2], Id[B2], _], Y] =
          p.φ.andThen(w.φ)
        def `φ#`: PolyMap.PhiSharp[PolyMap[Mono.Store[Id[S], _], Mono.Interface[Id[A1], Id[B1], _], _], Mono.Interface[Id[A2], Id[B2], _], Y] =
          (s, a) => p.`φ#`(s, w.`φ#`(p.φ(s), a))

  extension [S, A1, B1, Y] (p: PolyMap[Mono.Store[Option[S], _], Mono.Interface[A1, Option[B1], _], Y])
    @scala.annotation.targetName("andThenStoreMonoToMonoOptionPrism")
    def andThen(
      w: PolyMap[Mono.Interface[A1, Id[B1], _], Mono.Interface[A1, A1 => Option[B1], _], Y]
    ): PolyMap[PolyMap[Mono.Store[Option[S], _], Mono.Interface[A1, Id[B1], _], _], Mono.Interface[A1, A1 => Option[B1], _], Y] =
      new PolyMap[PolyMap[Mono.Store[Option[S], _], Mono.Interface[A1, Id[B1], _], _], Mono.Interface[A1, A1 => Option[B1], _], Y]:
        def φ: PolyMap.Phi[PolyMap[Mono.Store[Option[S], _], Mono.Interface[A1, Id[B1], _], _], Mono.Interface[A1, A1 => Option[B1], _], Y] =
          s => a2 => p.φ(s).flatMap(b1 => w.φ(b1)(a2))
        def `φ#`: PolyMap.PhiSharp[PolyMap[Mono.Store[Option[S], _], Mono.Interface[A1, Id[B1], _], _], Mono.Interface[A1, A1 => Option[B1], _], Y] =
          (s, a) => p.φ(s).flatMap(b1 => p.`φ#`(s, w.`φ#`(b1, a)))
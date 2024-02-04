package polynomial.morphism.methods

import polynomial.morphism.PolyMap
import polynomial.`object`.{Bi, Mono}

object swap:

  extension [S, A1, B1, A2, B2, Y] (p: PolyMap[Mono.Store[S, _], Bi.Interface[A1, B1, A2, B2, _], Y])

    def swapInterfaceDir: PolyMap[Mono.Store[S, _], Bi.Interface[A1, B2, A2, B1, _], Y] =
      new PolyMap[Mono.Store[S, _], Bi.Interface[A1, B2, A2, B1, _], Y]:
        def φ: PolyMap.Phi[(Mono.Store[S, _]), (Bi.Interface[A1, B2, A2, B1, _]), Y] =
          p.φ.swap
        def `φ#`: PolyMap.PhiSharp[(Mono.Store[S, _]), (Bi.Interface[A1, B2, A2, B1, _]), Y] =
          p.`φ#`

    def swapInterfacePos: PolyMap[Mono.Store[S, _], Bi.Interface[A2, B1, A1, B2, _], Y] =
      new PolyMap[Mono.Store[S, _], Bi.Interface[A2, B1, A1, B2, _], Y]:
        def φ: PolyMap.Phi[(Mono.Store[S, _]), (Bi.Interface[A2, B1, A1, B2, _]), Y] =
          p.φ
        def `φ#`: PolyMap.PhiSharp[(Mono.Store[S, _]), (Bi.Interface[A2, B1, A1, B2, _]), Y] =
          p.`φ#`.swap

    def swapModes: PolyMap[Mono.Store[S, _], Bi.Interface[A2, B2, A1, B1, _], Y] =
      new PolyMap[Mono.Store[S, _], Bi.Interface[A2, B2, A1, B1, _], Y]:
        def φ: PolyMap.Phi[(Mono.Store[S, _]), (Bi.Interface[A2, B2, A1, B1, _]), Y] =
          p.φ.swap
        def `φ#`: PolyMap.PhiSharp[(Mono.Store[S, _]), (Bi.Interface[A2, B2, A1, B1, _]), Y] =
          p.`φ#`.swap
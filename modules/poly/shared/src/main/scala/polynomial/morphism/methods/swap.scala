package polynomial.morphism.methods

import polynomial.morphism.PolyMap
import polynomial.`object`.Binomial.BiInterface
import polynomial.`object`.Monomial.Store
object swap:

  extension [S, A1, B1, A2, B2, Y] (p: PolyMap[Store[S, _], BiInterface[A1, B1, A2, B2, _], Y])

    def swapInterfaceDir: PolyMap[Store[S, _], BiInterface[A1, B2, A2, B1, _], Y] =
      new PolyMap[Store[S, _], BiInterface[A1, B2, A2, B1, _], Y]:
        def φ: PolyMap.Phi[(Store[S, _]), (BiInterface[A1, B2, A2, B1, _]), Y] =
          p.φ.swap
        def `φ#`: PolyMap.PhiSharp[(Store[S, _]), (BiInterface[A1, B2, A2, B1, _]), Y] =
          p.`φ#`

    def swapInterfacePos: PolyMap[Store[S, _], BiInterface[A2, B1, A1, B2, _], Y] =
      new PolyMap[Store[S, _], BiInterface[A2, B1, A1, B2, _], Y]:
        def φ: PolyMap.Phi[(Store[S, _]), (BiInterface[A2, B1, A1, B2, _]), Y] =
          p.φ
        def `φ#`: PolyMap.PhiSharp[(Store[S, _]), (BiInterface[A2, B1, A1, B2, _]), Y] =
          p.`φ#`.swap

    def swapModes: PolyMap[Store[S, _], BiInterface[A2, B2, A1, B1, _], Y] =
      new PolyMap[Store[S, _], BiInterface[A2, B2, A1, B1, _], Y]:
        def φ: PolyMap.Phi[(Store[S, _]), (BiInterface[A2, B2, A1, B1, _]), Y] =
          p.φ.swap
        def `φ#`: PolyMap.PhiSharp[(Store[S, _]), (BiInterface[A2, B2, A1, B1, _]), Y] =
          p.`φ#`.swap
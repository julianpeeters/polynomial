package polynomial.morphism.methods

import polynomial.morphism.PolyMap
import polynomial.`object`.{Binomial, Monomial}

object swap:

  extension [S, A1, B1, A2, B2, Y] (p: PolyMap[Monomial.Store[S, _], Binomial.Interface[A1, B1, A2, B2, _], Y])

    def swapInterfaceDir: PolyMap[Monomial.Store[S, _], Binomial.Interface[A1, B2, A2, B1, _], Y] =
      new PolyMap[Monomial.Store[S, _], Binomial.Interface[A1, B2, A2, B1, _], Y]:
        def φ: PolyMap.Phi[(Monomial.Store[S, _]), (Binomial.Interface[A1, B2, A2, B1, _]), Y] =
          p.φ.swap
        def `φ#`: PolyMap.PhiSharp[(Monomial.Store[S, _]), (Binomial.Interface[A1, B2, A2, B1, _]), Y] =
          p.`φ#`

    def swapInterfacePos: PolyMap[Monomial.Store[S, _], Binomial.Interface[A2, B1, A1, B2, _], Y] =
      new PolyMap[Monomial.Store[S, _], Binomial.Interface[A2, B1, A1, B2, _], Y]:
        def φ: PolyMap.Phi[(Monomial.Store[S, _]), (Binomial.Interface[A2, B1, A1, B2, _]), Y] =
          p.φ
        def `φ#`: PolyMap.PhiSharp[(Monomial.Store[S, _]), (Binomial.Interface[A2, B1, A1, B2, _]), Y] =
          p.`φ#`.swap

    def swapModes: PolyMap[Monomial.Store[S, _], Binomial.Interface[A2, B2, A1, B1, _], Y] =
      new PolyMap[Monomial.Store[S, _], Binomial.Interface[A2, B2, A1, B1, _], Y]:
        def φ: PolyMap.Phi[(Monomial.Store[S, _]), (Binomial.Interface[A2, B2, A1, B1, _]), Y] =
          p.φ.swap
        def `φ#`: PolyMap.PhiSharp[(Monomial.Store[S, _]), (Binomial.Interface[A2, B2, A1, B1, _]), Y] =
          p.`φ#`.swap
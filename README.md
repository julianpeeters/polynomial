# polynomial

Based on the polynomial functors described in [Niu and Spivak](https://topos.site/poly-book.pdf)

### Add the dependency:
 - libarary for Scala 3 (JS, JVM, and Native platforms)
 - depends on cats 2.10 (for the `Store` monomial)
 
```scala
"com.julianpeeters" %% "polynomial" % "0.0.0"
```

### Modules
 - [`polynomial`](#polynomial-1): objects, morphisms, products

#### `polynomial`

The `polynomial` library provides the following implementation of poly:
 - objects: built-in ADTs representing `Monomial`, `Binomial`, `Trinomial` functors
 - morphisms: `PolyMap`, a natural transformation between polynomial functors
 - products: `Tensor`, a type match on functors, yielding their Day convolution

```scala
import polynomial.`object`.Monomial
import polynomial.morphism.~>
import polynomial.product.⊗

type Plant[Y]      = Monomial[(Byte, Byte => Char), Char, Y]
type Controller[Y] = Monomial[Char, Byte => Char, Y]
type System[Y]     = Monomial[Byte, Byte => Char, Y]

type ω[Y] = ((Plant ⊗ Controller) ~> System)[Y]
```
<small>(Note: example adapted from the poly book, by [Niu and Spivak](https://topos.site/poly-book.pdf))</small>

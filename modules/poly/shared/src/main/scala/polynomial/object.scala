package polynomial

object `object`:

  // 0
  type Initial[Y] = Monomial[Nothing, Nothing, Y]

  // 1
  type Terminal[Y] = Monomial[Nothing, Unit, Y]

  // `Syˢ`
  case class Store[S, Y](yᵉˣᵖ: S => Y, coeff: S) // isomorphic to `cats.data.Store[S, Y]`

  // Byᴬ
  case class Monomial[A, B, Y](yᵉˣᵖ: A => Y, coeff: B)

  // B₁yᴬ¹ + B₂yᴬ²
  sealed trait Binomial[+A1, +B1, +A2, +B2, Y]
  object Binomial:
    case class Term1[A1, B1, Y](yᵉˣᵖ: A1 => Y, coeff: B1) extends Binomial[A1, B1, Nothing, Nothing, Y]
    case class Term2[A2, B2, Y](yᵉˣᵖ: A2 => Y, coeff: B2) extends Binomial[Nothing, Nothing, A2, B2, Y]

  // B₁yᴬ¹ + B₂yᴬ² + B₃yᴬ³
  sealed trait Trinomial[+A1, +B1, +A2, +B2, +A3, +B3, Y]
  object Trinomial:
    case class Term1[A1, B1, Y](yᵉˣᵖ: A1 => Y, coeff: B1) extends Trinomial[A1, B1, Nothing, Nothing, Nothing, Nothing, Y]
    case class Term2[A2, B2, Y](yᵉˣᵖ: A2 => Y, coeff: B2) extends Trinomial[Nothing, Nothing, A2, B2, Nothing, Nothing, Y]
    case class Term3[A3, B3, Y](yᵉˣᵖ: A3 => Y, coeff: B3) extends Trinomial[Nothing, Nothing, Nothing, Nothing, A3, B3, Y]
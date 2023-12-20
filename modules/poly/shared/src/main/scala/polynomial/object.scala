package polynomial

object `object`:

  type Store[S, A] = cats.data.Store[S, A]

  case class Monomial[A, B, Y](yᵉˣᵖ: A => Y, coeff: B)

  sealed trait Binomial[+A1, +B1, +A2, +B2, Y]
  object Binomial:
    case class Term1[A1, B1, Y](yᵉˣᵖ: A1 => Y, coeff: B1) extends Binomial[A1, B1, Nothing, Nothing, Y]
    case class Term2[A2, B2, Y](yᵉˣᵖ: A2 => Y, coeff: B2) extends Binomial[Nothing, Nothing, A2, B2, Y]

  sealed trait Trinomial[+A1, +B1, +A2, +B2, +A3, +B3, Y]
  object Trinomial:
    case class Term1[A1, B1, Y](yᵉˣᵖ: A1 => Y, coeff: B1) extends Trinomial[A1, B1, Nothing, Nothing, Nothing, Nothing, Y]
    case class Term2[A2, B2, Y](yᵉˣᵖ: A2 => Y, coeff: B2) extends Trinomial[Nothing, Nothing, A2, B2, Nothing, Nothing, Y]
    case class Term3[A3, B3, Y](yᵉˣᵖ: A3 => Y, coeff: B3) extends Trinomial[Nothing, Nothing, Nothing, Nothing, A3, B3, Y]
package polynomial

object `object`:

  type Store[S, A] = cats.data.Store[S, A]

  case class Monomial[A, B, Y](input: A => Y, output: B)

  sealed trait Binomial[+A1, +B1, +A2, +B2, Y]
  object Binomial:
    case class Mode1[A1, B1, Y](input: A1 => Y, output: B1) extends Binomial[A1, B1, Nothing, Nothing, Y]
    case class Mode2[A2, B2, Y](input: A2 => Y, output: B2) extends Binomial[Nothing, Nothing, A2, B2, Y]

  sealed trait Trinomial[+A1, +B1, +A2, +B2, +A3, +B3, Y]
  object Trinomial:
    case class Mode1[A1, B1, Y](input: A1 => Y, output: B1) extends Trinomial[A1, B1, Nothing, Nothing, Nothing, Nothing, Y]
    case class Mode2[A2, B2, Y](input: A2 => Y, output: B2) extends Trinomial[Nothing, Nothing, A2, B2, Nothing, Nothing, Y]
    case class Mode3[A3, B3, Y](input: A3 => Y, output: B3) extends Trinomial[Nothing, Nothing, Nothing, Nothing, A3, B3, Y]
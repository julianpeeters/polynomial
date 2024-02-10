package polynomial

object `object`:

  // Byᴬ
  sealed trait Monomial[A, B, Y]
  object Monomial:
    case class Interface[A, B, Y](yᵉˣᵖ: A => Y, coeff: B) extends Monomial[A, B, Y]
    case class Store[S, Y](yᵉˣᵖ: S => Y, coeff: S) extends Monomial[S, S, Y]

  // B₁yᴬ¹ + B₂yᴬ²
  sealed trait Binomial[+A1, +B1, +A2, +B2, Y]
  object Binomial:
    sealed trait BiInterface[+A1, +B1, +A2, +B2, Y] extends Binomial[A1, B1, A2, B2, Y]
    object BiInterface:
      case class Term1[A1, B1, Y](yᵉˣᵖ: A1 => Y, coeff: B1) extends BiInterface[A1, B1, Nothing, Nothing, Y]
      case class Term2[A2, B2, Y](yᵉˣᵖ: A2 => Y, coeff: B2) extends BiInterface[Nothing, Nothing, A2, B2, Y]
    sealed trait BiStore[+S1, +S2, Y] extends Binomial[S1, S1, S2, S2, Y]
    object BiStore:
      case class Term1[S1, Y](yᵉˣᵖ: S1 => Y, coeff: S1) extends BiStore[S1, Nothing, Y]
      case class Term2[S2, Y](yᵉˣᵖ: S2 => Y, coeff: S2) extends BiStore[Nothing, S2, Y]

  // B₁yᴬ¹ + B₂yᴬ² + B₃yᴬ³
  sealed trait Trinomial[+A1, +B1, +A2, +B2, +A3, +B3, Y]
  object Trinomial:
    sealed trait TriInterface[+A1, +B1, +A2, +B2, +A3, +B3, Y] extends Trinomial[A1, B1, A2, B2, A3, B3, Y]
    object TriInterface:
      case class Term1[A1, B1, Y](yᵉˣᵖ: A1 => Y, coeff: B1) extends TriInterface[A1, B1, Nothing, Nothing, Nothing, Nothing, Y]
      case class Term2[A2, B2, Y](yᵉˣᵖ: A2 => Y, coeff: B2) extends TriInterface[Nothing, Nothing, A2, B2, Nothing, Nothing, Y]
      case class Term3[A3, B3, Y](yᵉˣᵖ: A3 => Y, coeff: B3) extends TriInterface[Nothing, Nothing, Nothing, Nothing, A3, B3, Y]
    sealed trait TriStore[+S1, +S2, +S3, Y] extends Trinomial[S1, S1, S2, S2, S3, S3, Y]
    object TriStore:
      case class Term1[S1, Y](yᵉˣᵖ: S1 => Y, coeff: S1) extends TriStore[S1, Nothing, Nothing, Y]
      case class Term2[S2, Y](yᵉˣᵖ: S2 => Y, coeff: S2) extends TriStore[Nothing, S2, Nothing, Y]
      case class Term3[S3, Y](yᵉˣᵖ: S3 => Y, coeff: S3) extends TriStore[Nothing, Nothing, S3, Y]
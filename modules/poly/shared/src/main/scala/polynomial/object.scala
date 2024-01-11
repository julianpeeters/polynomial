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
    sealed trait Interface[+A1, +B1, +A2, +B2, Y] extends Binomial[A1, B1, A2, B2, Y]
    object Interface:
      case class Term1[A1, B1, Y](yᵉˣᵖ: A1 => Y, coeff: B1) extends Binomial.Interface[A1, B1, Nothing, Nothing, Y]
      case class Term2[A2, B2, Y](yᵉˣᵖ: A2 => Y, coeff: B2) extends Binomial.Interface[Nothing, Nothing, A2, B2, Y]
    sealed trait Store[+S1, +S2, Y] extends Binomial[S1, S1, S2, S2, Y]
    object Store:
      case class Term1[S1, Y](yᵉˣᵖ: S1 => Y, coeff: S1) extends Binomial.Store[S1, Nothing, Y]
      case class Term2[S2, Y](yᵉˣᵖ: S2 => Y, coeff: S2) extends Binomial.Store[Nothing, S2, Y]

  // B₁yᴬ¹ + B₂yᴬ² + B₃yᴬ³
  sealed trait Trinomial[+A1, +B1, +A2, +B2, +A3, +B3, Y]
  object Trinomial:
    sealed trait Interface[+A1, +B1, +A2, +B2, +A3, +B3, Y] extends Trinomial[A1, B1, A2, B2, A3, B3, Y]
    object Interface:
      case class Term1[A1, B1, Y](yᵉˣᵖ: A1 => Y, coeff: B1) extends Trinomial[A1, B1, Nothing, Nothing, Nothing, Nothing, Y]
      case class Term2[A2, B2, Y](yᵉˣᵖ: A2 => Y, coeff: B2) extends Trinomial[Nothing, Nothing, A2, B2, Nothing, Nothing, Y]
      case class Term3[A3, B3, Y](yᵉˣᵖ: A3 => Y, coeff: B3) extends Trinomial[Nothing, Nothing, Nothing, Nothing, A3, B3, Y]
    sealed trait Store[+S1, +S2, +S3, Y] extends Trinomial[S1, S1, S2, S2, S3, S3, Y]
    object Store:
      case class Term1[S1, Y](yᵉˣᵖ: S1 => Y, coeff: S1) extends Store[S1, Nothing, Nothing, Y]
      case class Term2[S2, Y](yᵉˣᵖ: S2 => Y, coeff: S2) extends Store[Nothing, S2, Nothing, Y]
      case class Term3[S3, Y](yᵉˣᵖ: S3 => Y, coeff: S3) extends Store[Nothing, Nothing, S3, Y]
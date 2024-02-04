package polynomial

object `object`:

  // Byᴬ
  sealed trait Mono[A, B, Y]
  object Mono:
    case class Interface[A, B, Y](yᵉˣᵖ: A => Y, coeff: B) extends Mono[A, B, Y]
    case class Store[S, Y](yᵉˣᵖ: S => Y, coeff: S) extends Mono[S, S, Y]

  // B₁yᴬ¹ + B₂yᴬ²
  sealed trait Bi[+A1, +B1, +A2, +B2, Y]
  object Bi:
    sealed trait Interface[+A1, +B1, +A2, +B2, Y] extends Bi[A1, B1, A2, B2, Y]
    object Interface:
      case class Term1[A1, B1, Y](yᵉˣᵖ: A1 => Y, coeff: B1) extends Bi.Interface[A1, B1, Nothing, Nothing, Y]
      case class Term2[A2, B2, Y](yᵉˣᵖ: A2 => Y, coeff: B2) extends Bi.Interface[Nothing, Nothing, A2, B2, Y]
    sealed trait Store[+S1, +S2, Y] extends Bi[S1, S1, S2, S2, Y]
    object Store:
      case class Term1[S1, Y](yᵉˣᵖ: S1 => Y, coeff: S1) extends Bi.Store[S1, Nothing, Y]
      case class Term2[S2, Y](yᵉˣᵖ: S2 => Y, coeff: S2) extends Bi.Store[Nothing, S2, Y]

  // B₁yᴬ¹ + B₂yᴬ² + B₃yᴬ³
  sealed trait Tri[+A1, +B1, +A2, +B2, +A3, +B3, Y]
  object Tri:
    sealed trait Interface[+A1, +B1, +A2, +B2, +A3, +B3, Y] extends Tri[A1, B1, A2, B2, A3, B3, Y]
    object Interface:
      case class Term1[A1, B1, Y](yᵉˣᵖ: A1 => Y, coeff: B1) extends Tri[A1, B1, Nothing, Nothing, Nothing, Nothing, Y]
      case class Term2[A2, B2, Y](yᵉˣᵖ: A2 => Y, coeff: B2) extends Tri[Nothing, Nothing, A2, B2, Nothing, Nothing, Y]
      case class Term3[A3, B3, Y](yᵉˣᵖ: A3 => Y, coeff: B3) extends Tri[Nothing, Nothing, Nothing, Nothing, A3, B3, Y]
    sealed trait Store[+S1, +S2, +S3, Y] extends Tri[S1, S1, S2, S2, S3, S3, Y]
    object Store:
      case class Term1[S1, Y](yᵉˣᵖ: S1 => Y, coeff: S1) extends Store[S1, Nothing, Nothing, Y]
      case class Term2[S2, Y](yᵉˣᵖ: S2 => Y, coeff: S2) extends Store[Nothing, S2, Nothing, Y]
      case class Term3[S3, Y](yᵉˣᵖ: S3 => Y, coeff: S3) extends Store[Nothing, Nothing, S3, Y]
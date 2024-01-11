package polynomial.mermaid.render

sealed trait Format
object Format:
  case object Cardinal extends Format
  case object Generic extends Format
  case object Specific extends Format

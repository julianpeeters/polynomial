package polynomial.mermaid

import scala.quoted.{Type, Expr, Quotes, ToExpr, quotes}//QuoteContext}

def tpeNmeMacro[A: Type](using Quotes) = {
  import quotes.reflect.* // Import `Tree`, `TypeRepr`, `Symbol`, `Position`, .....

  val name = TypeRepr.of[A].classSymbol.get.name  // TypeRepr.of[A].toString()//typeSymbol    //Type.of[A]  //Type.show[A]
  Expr(name)
}
inline def typeName[A] = ${tpeNmeMacro[A]}

package polynomial.mermaid.render

import typesize.SizeOf.{Cardinality, Finite, NonFinite, TooBigToCompute}

extension (s: String)
  def addTitle(
    p: String,
    pLength: Int, 
    q: String,
    qLength: Int
  ): String =
    val sepA = ";"
    val title = Render.title(p, pLength, q, qLength)
    val Array(start, end) = s.split(sepA, 2)
    start + sepA + title + end

object Render:

  def cardinality(c: Cardinality): String =
    c match
      case Finite(i) => if i == 1 then " " else i.toString
      case NonFinite => "Not a finite set"
      case TooBigToCompute => "Not feasible to calculate"

  def classDef(name: String, args: List[String]) =
    s"classDef $name ${args.mkString(", ")};"

  def line(c: Cardinality): String =
    c match
      case Finite(i) => if i == 1 then "~~~" else "---"
      case NonFinite => "---"
      case TooBigToCompute => "---"

  def polyMap(labelP: String, labelQ: String): String =
    s"$labelP ~> $labelQ"
    // s"$labelP → $labelQ"

  def monomial(
    coefficient: String,
    variable: String,
    exponent: String
  ): String =
    s"${coefficient}${variable}${Font.superscript(exponent)}"    

  def binomial(
    coefficient1: String,
    variable1: String,
    exponent1: String,
    coefficient2: String,
    variable2: String,
    exponent2: String
  ): String =
    s"${monomial(coefficient1, variable1, exponent1)} + ${monomial(coefficient2, variable2, exponent2)}"    

  def y: String =
    "y"
    // "𝑦"

  def title(
    p: String,
    pLength: Int, 
    q: String,
    qLength: Int
  ): String =
    s"\n  TitleStart[ ]:::hidden${"~".repeat(pLength)}TitleBody[${polyMap(p, q)}]:::title${"~".repeat(qLength)}TitleEnd[ ]:::hidden"

  def mermaidCodeFence[P[_], Q[_]](graph: String): String =
    s"""|```mermaid
        |graph LR;
        |  $graph
        |
        |${classDef("empty", List("fill:background"))}
        |${classDef("point", List("width:0px", "height:0px"))}
        |${classDef("title", List("stroke-width:0px", "fill:background"))}
        |```""".stripMargin


  def missingCustomLabel: String = "Please add custom labels"



    
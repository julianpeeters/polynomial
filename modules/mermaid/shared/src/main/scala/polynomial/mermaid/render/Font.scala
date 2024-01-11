package polynomial.mermaid.render

object Font:

  def courier(text: String): String =
    s"""<span style="font-family:Courier">$text</span>"""
    // s"""$text"""

  def superscript(text: String): String =
    s"<sup>$text</sup>"
    // s"^$text"
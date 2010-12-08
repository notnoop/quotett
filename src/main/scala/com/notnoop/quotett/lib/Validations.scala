
package com.notnoop.quotett.lib

import net.liftweb._
import http._
import util._
import common._

import java.net.URL
import scala.xml.Text

object Validations {
  def valHttpURL(ident: FieldIdentifier): String => List[FieldError] = s => {
    val url = if (s.contains("://")) s else ("http://" + s)

    def isValidURL =
      try { new URL(url); true }
      catch { case e => false }

    val errorText =
      if (!isValidURL) Some(Text("Not a valid URL"))
      else if (!url.startsWith("http")) Some(Text("Not an Http URL"))
      else None

    errorText.toList map (FieldError(ident, _))
  }
}

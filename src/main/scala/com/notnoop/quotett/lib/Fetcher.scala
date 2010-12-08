package com.notnoop.quotett.lib

import dispatch._
import Http._

import scala.xml.PrettyPrinter

class Fetcher {

  def contentOf(url: String) = {
    val http = new Http
    val t = http(url as_str)
    t
  }

  def textOf(url: String) = {
    val http = new Http
    val req = :/("viewtext.org") / "api" / "text"
    val rquery = req <<? Map("url" -> url, "format" -> "xml",
      "rl" -> "false")

    new PrettyPrinter(1000, 4).formatNodes(http(rquery <> { _ \ "Content"} ))
  }
}

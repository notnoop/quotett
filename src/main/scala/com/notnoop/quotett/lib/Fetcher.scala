package com.notnoop.quotett.lib

import dispatch._
import Http._

class Fetcher {

  def contentOf(url: String) = {
    val http = new Http
    val t = http(url as_str)
    t
  }

  def textOf(url: String) = {
    val http = new Http
    val req = :/("viewtext.org") / "api" / "text"
    val rquery = req <<? Map("url" -> url, "format" -> "json",
      "rl" -> "false")

    import json.JsHttp._
    http(rquery ># { 'content ! str })
  }
}

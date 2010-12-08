package com.notnoop.quotett.snippet

import xml.{Text, NodeSeq}
import net.liftweb.util.Helpers._
import net.liftweb.http.S
import net.liftweb.http.LiftScreen
import net.liftweb.http.SHtml._
import net.liftweb.common._

import com.notnoop.quotett.model.Citation
import com.notnoop.quotett.lib.Fetcher

object CitationScreen extends LiftScreen {
  object citation extends ScreenVar(Citation.create)

  val fetcher = new Fetcher

  _register(() => citation.is)

  def finish() {
    //citation.save
    try {
      citation.htmlSource(fetcher.contentOf(citation.sourceURL))
      //citation.strippedSource(fetcher.textOf(citation.sourceURL))
    } catch {
      case _ => S.error("Couldn't fetch url")
      return
    }

    S.notice("Citation is saved")
  }
}

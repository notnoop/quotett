package com.notnoop.quotett.snippet

import xml.{Text, NodeSeq}
import net.liftweb.util.Helpers._
import net.liftweb.http.S
import net.liftweb.http.LiftScreen
import net.liftweb.http.SHtml._
import net.liftweb.common._

import com.notnoop.quotett.model.Citation

object CitationScreen extends LiftScreen {
  object citation extends ScreenVar(Citation.create)

  _register(() => citation.is)

  def finish() {
    S.notice("Saved")
  }

}

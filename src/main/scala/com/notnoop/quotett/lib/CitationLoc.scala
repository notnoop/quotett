package com.notnoop.quotett.lib

import _root_.net.liftweb._
import common._
import util._
import Helpers._
import http._
import mapper._
import sitemap._
import Loc._

import scala.xml.{Text, NodeSeq}

import com.notnoop.quotett.model.Citation

case class CitationLoc(c: Citation)

object CitationsLoc extends Loc[CitationLoc] {

  override def name = "Citation"

  override def defaultValue = None

  override def params = List()

  override val text = new Loc.LinkText({a: CitationLoc => Text("Citation " + a.c.id)})

  override val link = new Loc.Link[CitationLoc](List("view"), false) {
    override def createLink(in: CitationLoc) = Full(Text("/citation/" + in.c.id))
  }

  override def rewrite = Full({
    case RewriteRequest(ParsePath("citation" :: citationId :: Nil, _, _, _),
      _, _) if Citation.findAll(By(Citation.id, citationId.toLong)).nonEmpty =>
        val citation = Citation.findAll(By(Citation.id, citationId.toLong))(0)
        (RewriteResponse("view" :: Nil), CitationLoc(citation))
    })

  override def snippets: SnippetTest = {
    case ("citation", e) => displayRecord(e.open_!) _
  }

  def displayRecord(e: CitationLoc)(in: NodeSeq): NodeSeq = {
    <div>
      <h2>Citing {e.c.quotation}</h2>
      <p>Original cite: <a href={e.c.sourceURL}>{e.c.sourceURL}</a></p>
      <p>Original Text:</p>
      {scala.xml.Unparsed(e.c.strippedSource.toString)}
    </div>
  }
}


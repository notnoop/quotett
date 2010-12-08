/*
 * Copyright 2010, Mahmood Ali.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are
 * met:
 *
 *   * Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *   * Redistributions in binary form must reproduce the above
 *     copyright notice, this list of conditions and the following disclaimer
 *     in the documentation and/or other materials provided with the
 *     distribution.
 *   * Neither the name of Mahmood Ali. nor the names of its
 *     contributors may be used to endorse or promote products derived from
 *     this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
 * OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
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


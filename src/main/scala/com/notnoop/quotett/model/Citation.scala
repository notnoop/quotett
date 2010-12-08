



package com.notnoop.quotett.model

import _root_.net.liftweb.mapper._
import _root_.net.liftweb.util._
import _root_.net.liftweb.common._

import _root_.net.liftweb.http.S

import com.notnoop.quotett.lib.Validations

class Citation extends LongKeyedMapper[Citation] with IdPK {

  def getSingleton = Citation

  object sourceURL extends MappedString(this, 255) {
    override def displayName = S ? "Source URL"
    override def required_? = true

    override def validations =
      valMinLen(1, "Required") _ ::
      Validations.valHttpURL(this) ::
      super.validations
  }

  object quotation extends MappedTextarea(this, 1000) {
    override def displayName = S ? "Cited Text"

    override def required_? = true
    override def validations = valMinLen(1, "Required") _ :: super.validations
  }

  // Populated
  object htmlSource extends MappedText(this)
  object strippedSource extends MappedText(this)
}

object Citation extends Citation with LongKeyedMetaMapper[Citation] {
  override def formFields(c: Citation) = c.quotation :: c.sourceURL :: Nil
}

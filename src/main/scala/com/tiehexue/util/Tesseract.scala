package com.tiehexue.util

import scala.sys.process._

object Tesseract {
  def getText(fileName: String) = {

    val result = ((s"tesseract ${fileName} stdout -l chi_sim+eng --psm 6 digits") !!)
    result.replaceAll("[ |\\n]", "")
  }
}

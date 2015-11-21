package com.shekhargulati.youtube_dl

trait RegexHelper {

  def extractGroup(urlString: String, regex: String) = {
    val linkRe = regex.r
    linkRe.findFirstMatchIn(urlString).map(m => m.group(1))
  }

}

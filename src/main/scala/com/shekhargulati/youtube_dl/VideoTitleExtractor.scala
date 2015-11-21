package com.shekhargulati.youtube_dl

import org.apache.commons.lang3.{StringEscapeUtils, StringUtils}

trait VideoTitleExtractor extends RegexHelper {

  def extractTitle(html: String): String = {
    val titleGroup: Option[String] = extractGroup(html, "<meta name=\"title\" content=(.*)")
    titleGroup
      .map(title => title.replaceFirst("<meta name=\"title\" content=", "").trim)
      .map(title => StringUtils.strip(title, "\">"))
      .map(title => StringEscapeUtils.unescapeHtml4(title))
      .get
  }

}

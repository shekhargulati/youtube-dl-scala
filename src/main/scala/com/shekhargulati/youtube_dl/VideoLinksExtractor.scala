package com.shekhargulati.youtube_dl

import java.net.URLDecoder

import org.apache.commons.lang3.StringEscapeUtils

trait VideoLinksExtractor extends RegexHelper {

  def extractVideoLinks(html: String): List[VideoDownloadInfo] = {
    val urlEncodeRe = "\"url_encoded_fmt_stream_map\":\"([^\"]*)\"".r
    val urlEncodeFmtStream = urlEncodeRe.findFirstMatchIn(html).map(m => m.group(1))
    val encodeRe = "url=(.*)".r
    val sline = urlEncodeFmtStream.flatMap(s => encodeRe.findFirstMatchIn(s)).map(m => m.group(1)).get
    val urlEncodeVideos = extractUrlEncodedVideos(sline).toList
    if (urlEncodeVideos.isEmpty) {
      throw new RuntimeException("Empty video download list")
    }
    urlEncodeVideos
  }

  def extractUrlEncodedVideos(sline: String) = {
    var urls = collection.mutable.ListBuffer[VideoDownloadInfo]()
    sline.split("=").foreach(us => {
      val urlString: String = StringEscapeUtils.unescapeJava(us)
      val urlFull: String = URLDecoder.decode(urlString, "UTF-8")
      val url = extractGroup(urlString, "([^&,]*)[&,]").map(u => URLDecoder.decode(u, "UTF-8"))
      val itag = extractGroup(urlFull, "itag=(\\d+)")
      val sig = extractGroup(urlFull, "&signature=([^&,]*)")
        .orElse(
          extractGroup(urlFull, "sig=([^&,]*)")
            .orElse(
              extractGroup(urlFull, "[&,]s=([^&,]*)").map(ss => new DecryptSignature(ss).decrypt())
            ))
      if (url.isDefined && itag.isDefined && sig.isDefined) {
        val newUrl = url.get + "&signature=" + sig.get
        val vd = itagMap.get(Integer.decode(itag.get))
        urls = urls :+ new VideoDownloadInfo(vd.get, newUrl)
      }
    })
    urls
  }


}

package com.shekhargulati.youtube_dl

import java.net.URL

import com.github.axet.wget.WGet

trait VideoInfoExtractor extends VideoTitleExtractor with VideoLinksExtractor {

  def extractVideoInfo(publicVideoUrl: String) = {
    val html: String = WGet.getHtml(new URL(publicVideoUrl))
    val urlEncodeVideos = extractVideoLinks(html)
    videoInfo(publicVideoUrl, html, urlEncodeVideos)
  }

  private[this] def videoInfo(videoUrl: String, html: String, urlEncodeVideos: List[VideoDownloadInfo]): YoutubeInfo = {
    val streamVideoUrls = urlEncodeVideos
      .filter(vd => vd.stream.isInstanceOf[StreamCombined])
      .sortWith((first, second) => first.stream.asInstanceOf[StreamCombined].videoQuality < second.stream.asInstanceOf[StreamCombined].videoQuality)
    streamVideoUrls.foreach(println)
    val videoToDownload = streamVideoUrls.head
    new YoutubeInfo(videoUrl, extractTitle(html), videoToDownload.videoStreamUrl, videoToDownload.stream)
  }

}

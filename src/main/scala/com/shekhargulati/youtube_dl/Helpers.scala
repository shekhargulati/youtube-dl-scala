package com.shekhargulati.youtube_dl

case class VideoDownloadInfo(stream: StreamInfo, videoStreamUrl: String)

sealed trait VideoInfo {
  val videoUrl: String
}

case class YoutubeInfo(val videoUrl: String, title: String, videoStreamUrl: String, streamInfo: StreamInfo) extends VideoInfo

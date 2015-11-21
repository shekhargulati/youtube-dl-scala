package com.shekhargulati.youtube_dl

import java.nio.file.{Files, Path, Paths}

import com.shekhargulati.youtube_dl.wget.OkHttpWget
import grizzled.slf4j.Logging

class YoutubeDownloader(val videoUrl: String) extends VideoDownloader with VideoInfoExtractor with Logging {

  require(validVideoUrl(videoUrl), "videoUrl can't be empty or null.")
  require(isYoutubeUrl(videoUrl), s"'$videoUrl' is not a valid Youtube url.")

  val outputVideoFilePath: OutputVideoFilePath = (outputDirPath, videoInfo) => {
    val fileName = s"${videoInfo.title}.${videoInfo.streamInfo.c.toString.toLowerCase}"
    val outputDir = outputDirPath.toAbsolutePath.toString
    val filePath = Paths.get(outputDir, fileName)
    Files.createFile(filePath).toFile
  }

  override def download(videoInfo: YoutubeInfo, outputDir: Path = Paths.get(System.getProperty("java.io.tmpdir")), outputFilePath: OutputVideoFilePath = outputVideoFilePath) = {
    val videoFile = outputFilePath(outputDir, videoInfo)
    OkHttpWget().downloadFile(videoInfo.videoStreamUrl, videoFile.toPath)
    info(s"Downloaded '${videoInfo.title}' to ${videoFile.toPath.toAbsolutePath}")
  }

  override def extractVideoInfo(): YoutubeInfo = {
    super.extractVideoInfo(videoUrl)
  }

  private[this] def isYoutubeUrl(videoUrl: String): Boolean = {
    Option(videoUrl).exists(url => url.contains("youtube.com"))
  }

  private[this] def validVideoUrl(videoUrl: String): Boolean = {
    Option(videoUrl).exists(_.trim.nonEmpty)
  }
}



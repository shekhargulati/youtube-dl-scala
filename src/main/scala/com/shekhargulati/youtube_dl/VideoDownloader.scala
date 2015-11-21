package com.shekhargulati.youtube_dl

import java.io.File
import java.nio.file.Path

trait VideoDownloader {

  type OutputVideoFilePath = (Path, YoutubeInfo) => File

  def download(videoInfo: YoutubeInfo, outputDir: Path, outputFilePath: OutputVideoFilePath)

  def extractVideoInfo(): VideoInfo

}

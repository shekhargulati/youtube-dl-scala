package com.shekhargulati.youtube_dl

import org.junit.Rule
import org.junit.rules.TemporaryFolder
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{FunSpec, Matchers}

@RunWith(classOf[JUnitRunner])
class YoutubeDownloaderTest extends FunSpec with Matchers {

  val videoUrl: String = "https://www.youtube.com/watch?v=NHopJHSlVo4"
  val title: String = "Derek Sivers: Keep your goals to yourself"

  val _tmpDirectory = new TemporaryFolder

  @Rule
  def temporaryDirectory = _tmpDirectory

  describe("YoutubeVideo Spec") {
    describe("youtube video url validation spec") {

      it("should give IllegalArgumentException when constructed with empty url") {
        the[IllegalArgumentException] thrownBy {
          new YoutubeDownloader("")
        } should have message "requirement failed: videoUrl can't be empty or null."
      }

      it("should give IllegalArgumentException when constructed with null url") {
        the[IllegalArgumentException] thrownBy {
          new YoutubeDownloader(null)
        } should have message "requirement failed: videoUrl can't be empty or null."
      }

      it("should give IllegalArgumentException when constructed with invalid url") {
        the[IllegalArgumentException] thrownBy {
          new YoutubeDownloader("abc.com/test")
        } should have message "requirement failed: 'abc.com/test' is not a valid Youtube url."
      }

      it("should give back valid url") {
        val downloader = new YoutubeDownloader(videoUrl)
        downloader.videoUrl should be(videoUrl)
      }

      it("should download video into a user provided output directory") {
        val downloader = new YoutubeDownloader(videoUrl)
        val videoInfo = downloader.extractVideoInfo()
        temporaryDirectory.create()
        val outputDir = temporaryDirectory.newFolder().toPath
        downloader.download(videoInfo, outputDir)
        outputDir.toFile.list should have size 1
      }

    }
  }

}

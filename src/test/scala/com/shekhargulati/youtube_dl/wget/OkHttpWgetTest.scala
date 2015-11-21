package com.shekhargulati.youtube_dl.wget

import java.nio.file.{Path, Paths}

import org.junit.Rule
import org.junit.rules.TemporaryFolder
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{FunSpec, Matchers}

@RunWith(classOf[JUnitRunner])
class OkHttpWgetTest extends FunSpec with Matchers {

  val _tmpDirectory = new TemporaryFolder

  @Rule
  def temporaryDirectory = _tmpDirectory

  val url = "https://www.gutenberg.org/files/50237/50237-0.txt"

  describe("OkHttpWget") {
    it("should make GET request and print response to console") {
      val output = OkHttpWget().get(url)
    }

    it("should download file") {
      temporaryDirectory.create()
      val outputDir: Path = temporaryDirectory.newFolder().toPath
      val downloadedFilePath = OkHttpWget().downloadFile(url)(outputDir, "book.txt")
      println(s"Downloaded file to path $downloadedFilePath")
      downloadedFilePath should be('isDefined)
      downloadedFilePath.get should be(outputDir.resolve(Paths.get("book.txt")))
    }
  }

  it("should download Youtube video") {
    val videoUrl = "https://r13---sn-cvh7zn7d.googlevideo.com/videoplayback?mt=1445069721&mv=m&pl=20&id=o-AH_v3T4WOYrT6Xjd_tnpIOzkYSpsbkMXo_NysNgPRx9N&ms=au&source=youtube&mm=31&mn=sn-cvh7zn7d&ip=122.162.160.215&ratebypass=yes&lmt=1298513132466490&requiressl=yes&sparams=dur%2Cid%2Cinitcwndbps%2Cip%2Cipbits%2Citag%2Clmt%2Cmime%2Cmm%2Cmn%2Cms%2Cmv%2Cpl%2Cratebypass%2Crequiressl%2Csource%2Cupn%2Cexpire&key=yt6&initcwndbps=312500&signature=1CF13E60D08085C07E2D40120C537462D7025E8E.385947FFC191E8A947AEB931386AD41416063015&sver=3&fexp=9408136%2C9408710%2C9414764%2C9416126%2C9416572%2C9416729%2C9416916%2C9417707%2C9418203%2C9418702%2C9419344%2C9420629%2C9420718%2C9420772%2C9421247%2C9421264%2C9421509%2C9422342&ipbits=0&itag=43&expire=1445091420&upn=3so5_7MzuFs&dur=0.000&mime=video%2Fwebm&signature=1CF13E60D08085C07E2D40120C537462D7025E8E.385947FFC191E8A947AEB931386AD41416063015"
    temporaryDirectory.create()
    val outputDir: Path = temporaryDirectory.newFolder().toPath
    val downloadedFilePath = OkHttpWget().downloadFile(videoUrl)(outputDir, "abc.webm")
    println(s"Downloaded file to path $downloadedFilePath")
    downloadedFilePath should be('isDefined)
    downloadedFilePath.get should be(outputDir.resolve(Paths.get("abc.webm")))
  }
}

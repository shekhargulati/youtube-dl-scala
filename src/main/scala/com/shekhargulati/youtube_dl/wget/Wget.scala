package com.shekhargulati.youtube_dl.wget

import java.io.FileOutputStream
import java.nio.charset.Charset
import java.nio.file.{Path, Paths}

import com.squareup.okhttp.OkHttpClient
import com.squareup.okhttp.Request.Builder
import okio.BufferedSource

trait Wget {
  type ResultProcessor[T] = BufferedSource => T

  val redirectToConsole: ResultProcessor[Unit] = {
    case source if source.exhausted() => println(s"<<<<<<<<< Redirected all output to console >>>>>>>>")
    case source =>
      println(source.readString(Charset.defaultCharset()))
      redirectToConsole(source)
  }

  def get[T](url: String, resultProcessor: ResultProcessor[T]): Option[T]

  def downloadFile(url: String)(outputDir: Path, fileName: String): Option[Path]

  def downloadFile(url: String, downloadFilePath: Path): Option[Path]

}

class OkHttpWget extends Wget {

  val client = new OkHttpClient()

  override def get[T](url: String, resultProcessor: ResultProcessor[T] = redirectToConsole) = {
    val request = new Builder().url(url).build()
    val response = client.newCall(request).execute()
    response match {
      case r if r.isSuccessful => Some(resultProcessor(r.body().source()))
      case _ => None
    }
  }

  override def downloadFile(url: String)(outputDir: Path, fileName: String): Option[Path] = {
    val downloadFilePath: Path = outputDir.resolve(Paths.get(fileName))
    downloadFile(url, downloadFilePath)
  }

  override def downloadFile(url: String, downloadFilePath: Path): Option[Path] = {
    val redirectToFile: ResultProcessor[Path] = source => {
      val downloadedFile: String = downloadFilePath.toAbsolutePath.toString
      val out = new FileOutputStream(downloadedFile)
      while (!source.exhausted()) {
        val buffer: Array[Byte] = new Array[Byte](1024)
        var n: Int = 0
        while (-1 != {
          n = source.read(buffer)
          n
        }) {
          out.write(buffer, 0, n)
        }
      }
      downloadFilePath
    }
    get(url, redirectToFile)
  }
}

object OkHttpWget {

  def apply() = new OkHttpWget

}
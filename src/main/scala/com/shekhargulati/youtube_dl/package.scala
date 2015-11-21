package com.shekhargulati

import com.shekhargulati.youtube_dl.AudioQuality.AudioQuality
import com.shekhargulati.youtube_dl.Encoding.Encoding
import com.shekhargulati.youtube_dl.Format.Format
import com.shekhargulati.youtube_dl.VideoQuality.VideoQuality

package object youtube_dl {

  object Format extends Enumeration {
    type Format = Value
    val FLV, GP3, MP4, WEBM = Value
  }

  object Encoding extends Enumeration {
    type Encoding = Value
    val H263, H264, VP8, VP9, MP4, MP3, AAC, VORBIS = Value
  }

  object AudioQuality extends Enumeration {
    type AudioQuality = Value
    val k256, k192, k128, k96, k64, k48, k36, k24 = Value
  }

  object VideoQuality extends Enumeration {
    type VideoQuality = Value
    val p3072, p2304, p2160, p1440, p1080, p720, p520, p480, p360, p270, p240, p224, p144 = Value
  }


  class StreamInfo(val c: Format)

  case class StreamCombined(override val c: Format, videoEncoding: Encoding, videoQuality: VideoQuality, audioEncoding: Encoding, audioQuality: AudioQuality) extends StreamInfo(c)

  case class StreamAudio(override val c: Format, audioEncoding: Encoding, audioQuality: AudioQuality) extends StreamInfo(c)

  case class StreamVideo(override val c: Format, videoEncoding: Encoding, videoQuality: VideoQuality) extends StreamInfo(c)

  val itagMap = Map(
    120 -> new StreamCombined(Format.FLV, Encoding.H264, VideoQuality.p720, Encoding.AAC, AudioQuality.k128),
    102 -> new StreamCombined(Format.WEBM, Encoding.VP8, VideoQuality.p720, Encoding.VORBIS, AudioQuality.k192),
    101 -> new StreamCombined(Format.WEBM, Encoding.VP8, VideoQuality.p360, Encoding.VORBIS, AudioQuality.k192),
    100 -> new StreamCombined(Format.WEBM, Encoding.VP8, VideoQuality.p360, Encoding.VORBIS, AudioQuality.k128),
    85 -> new StreamCombined(Format.MP4, Encoding.H264, VideoQuality.p1080, Encoding.AAC, AudioQuality.k192),
    84 -> new StreamCombined(Format.MP4, Encoding.H264, VideoQuality.p720, Encoding.AAC, AudioQuality.k192),
    83 -> new StreamCombined(Format.MP4, Encoding.H264, VideoQuality.p240, Encoding.AAC, AudioQuality.k96),
    82 -> new StreamCombined(Format.MP4, Encoding.H264, VideoQuality.p360, Encoding.AAC, AudioQuality.k96),
    46 -> new StreamCombined(Format.WEBM, Encoding.VP8, VideoQuality.p1080, Encoding.VORBIS, AudioQuality.k192),
    45 -> new StreamCombined(Format.WEBM, Encoding.VP8, VideoQuality.p720, Encoding.VORBIS, AudioQuality.k192),
    44 -> new StreamCombined(Format.WEBM, Encoding.VP8, VideoQuality.p480, Encoding.VORBIS, AudioQuality.k128),
    43 -> new StreamCombined(Format.WEBM, Encoding.VP8, VideoQuality.p360, Encoding.VORBIS, AudioQuality.k128),
    38 -> new StreamCombined(Format.MP4, Encoding.H264, VideoQuality.p3072, Encoding.AAC, AudioQuality.k192),
    37 -> new StreamCombined(Format.MP4, Encoding.H264, VideoQuality.p1080, Encoding.AAC, AudioQuality.k192),
    36 -> new StreamCombined(Format.GP3, Encoding.MP4, VideoQuality.p240, Encoding.AAC, AudioQuality.k36), // 3gp
    35 -> new StreamCombined(Format.FLV, Encoding.H264, VideoQuality.p480, Encoding.AAC, AudioQuality.k128),
    34 -> new StreamCombined(Format.FLV, Encoding.H264, VideoQuality.p360, Encoding.AAC, AudioQuality.k128),
    22 -> new StreamCombined(Format.MP4, Encoding.H264, VideoQuality.p720, Encoding.AAC, AudioQuality.k192),
    18 -> new StreamCombined(Format.MP4, Encoding.H264, VideoQuality.p360, Encoding.AAC, AudioQuality.k96), // mp4
    17 -> new StreamCombined(Format.GP3, Encoding.MP4, VideoQuality.p144, Encoding.AAC, AudioQuality.k24), // 3gp
    6 -> new StreamCombined(Format.FLV, Encoding.H263, VideoQuality.p270, Encoding.MP3, AudioQuality.k64),
    5 -> new StreamCombined(Format.FLV, Encoding.H263, VideoQuality.p240, Encoding.MP3, AudioQuality.k64), // flv

    133 -> new StreamVideo(Format.MP4, Encoding.H264, VideoQuality.p240),
    134 -> new StreamVideo(Format.MP4, Encoding.H264, VideoQuality.p360),
    135 -> new StreamVideo(Format.MP4, Encoding.H264, VideoQuality.p480),
    136 -> new StreamVideo(Format.MP4, Encoding.H264, VideoQuality.p720),
    137 -> new StreamVideo(Format.MP4, Encoding.H264, VideoQuality.p1080),
    138 -> new StreamVideo(Format.MP4, Encoding.H264, VideoQuality.p2160),
    160 -> new StreamVideo(Format.MP4, Encoding.H264, VideoQuality.p144),
    242 -> new StreamVideo(Format.WEBM, Encoding.VP9, VideoQuality.p240),
    243 -> new StreamVideo(Format.WEBM, Encoding.VP9, VideoQuality.p360),
    244 -> new StreamVideo(Format.WEBM, Encoding.VP9, VideoQuality.p480),
    247 -> new StreamVideo(Format.WEBM, Encoding.VP9, VideoQuality.p720),
    248 -> new StreamVideo(Format.WEBM, Encoding.VP9, VideoQuality.p1080),
    264 -> new StreamVideo(Format.MP4, Encoding.H264, VideoQuality.p1440),
    271 -> new StreamVideo(Format.WEBM, Encoding.VP9, VideoQuality.p1440),
    272 -> new StreamVideo(Format.WEBM, Encoding.VP9, VideoQuality.p2160),
    278 -> new StreamVideo(Format.WEBM, Encoding.VP9, VideoQuality.p144),
    298 -> new StreamVideo(Format.MP4, Encoding.H264, VideoQuality.p720),
    299 -> new StreamVideo(Format.MP4, Encoding.H264, VideoQuality.p1080),
    302 -> new StreamVideo(Format.WEBM, Encoding.VP9, VideoQuality.p720),
    303 -> new StreamVideo(Format.WEBM, Encoding.VP9, VideoQuality.p1080),

    139 -> new StreamAudio(Format.MP4, Encoding.AAC, AudioQuality.k48),
    140 -> new StreamAudio(Format.MP4, Encoding.AAC, AudioQuality.k128),
    141 -> new StreamAudio(Format.MP4, Encoding.AAC, AudioQuality.k256),
    171 -> new StreamAudio(Format.WEBM, Encoding.VORBIS, AudioQuality.k128),
    172 -> new StreamAudio(Format.WEBM, Encoding.VORBIS, AudioQuality.k192)
  )

}

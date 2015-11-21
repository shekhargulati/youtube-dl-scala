# youtube-dl-scala

Simple API written in Scala to download Youtube videos.

```scala
val downloader = new YoutubeDownloader(videoUrl)
val videoInfo = downloader.extractVideoInfo()
downloader.download(videoInfo, Paths.get("my_videos_directory"))
```

This will download video in the the `my_videos_directory`.

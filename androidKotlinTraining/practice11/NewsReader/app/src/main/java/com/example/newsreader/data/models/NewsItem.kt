package com.example.newsreader.data.models

import org.threeten.bp.ZonedDateTime

data class NewsItem(
  val title: String,
  val link: String,
  val pubDate: ZonedDateTime
)

package com.example.newsreader.data.models

import org.threeten.bp.LocalDateTime

data class NewsItem(
  val title: String,
  val link: String,
  val pubDate: LocalDateTime
)

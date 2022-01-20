package com.example.newsreader.data.source

import com.example.newsreader.data.models.NewsSource

data class StockNewsSources(
  val all: NewsSource = NewsSource("All", "NA"),
  val google: NewsSource = NewsSource("Google", "NA"),
  val toyokeizai: NewsSource = NewsSource("Toyokeizai", "NA")
)

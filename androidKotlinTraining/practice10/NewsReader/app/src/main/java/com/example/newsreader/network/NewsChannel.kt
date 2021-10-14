package com.example.newsreader.network

import com.tickaroo.tikxml.annotation.Element
import com.tickaroo.tikxml.annotation.Path
import com.tickaroo.tikxml.annotation.Xml

@Xml(name = "rss")
data class NewsChannel(
  @Path("channel")
  @Element
  val newsItems: List<NewsItem>
)

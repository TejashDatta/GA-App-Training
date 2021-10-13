package com.example.newsreader.network

import com.tickaroo.tikxml.TypeConverter
import com.tickaroo.tikxml.annotation.PropertyElement
import com.tickaroo.tikxml.annotation.Xml

@Xml(name = "item")
data class NewsItem(
  @PropertyElement val title: String,
  @PropertyElement val link: String,
  @PropertyElement val pubDate: String,
  @PropertyElement val source: String
)

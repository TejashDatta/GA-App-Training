package com.example.newsreader.network.data_transfer_objects.toyokeizai_news

import com.example.newsreader.network.data_transfer_objects.DateConverter
import com.tickaroo.tikxml.annotation.PropertyElement
import com.tickaroo.tikxml.annotation.Xml
import org.threeten.bp.ZonedDateTime

@Xml(name = "item")
data class NetworkToyokeizaiNewsItem(
  @PropertyElement val title: String,
  @PropertyElement val link: String,
  @PropertyElement(converter = DateConverter::class) val pubDate: ZonedDateTime
)

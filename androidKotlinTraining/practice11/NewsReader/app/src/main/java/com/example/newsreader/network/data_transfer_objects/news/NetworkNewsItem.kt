package com.example.newsreader.network.data_transfer_objects.news

import com.example.newsreader.network.data_transfer_objects.DateConverter
import com.tickaroo.tikxml.annotation.PropertyElement
import com.tickaroo.tikxml.annotation.Xml
import org.threeten.bp.ZonedDateTime

@Xml(name = "item")
data class NetworkNewsItem(
  @PropertyElement val title: String,
  @PropertyElement val link: String,
  @PropertyElement(converter = DateConverter::class) val pubDate: ZonedDateTime,
  @PropertyElement val source: String?
)

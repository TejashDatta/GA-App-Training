package com.example.newsreader.network.data_transfer_objects.general_news

import com.example.newsreader.data.models.NewsItem
import com.tickaroo.tikxml.annotation.Element
import com.tickaroo.tikxml.annotation.Path
import com.tickaroo.tikxml.annotation.Xml

@Xml(name = "rss")
data class NetworkGeneralNewsChannel(
  @Path("channel")
  @Element
  val networkGeneralNewsItems: List<NetworkGeneralNewsItem>
)

fun NetworkGeneralNewsChannel.toDomainModel(source: String) : List<NewsItem> {
  return networkGeneralNewsItems.map {
    NewsItem(
      title = it.title,
      link = it.link,
      publishedDate = it.pubDate,
      source = it.source ?: source
    )
  }
}

package com.example.newsreader.network.data_transfer_objects.news

import com.example.newsreader.data.models.NewsItem
import com.tickaroo.tikxml.annotation.Element
import com.tickaroo.tikxml.annotation.Path
import com.tickaroo.tikxml.annotation.Xml

@Xml(name = "rss")
data class NetworkNewsChannel(
  @Path("channel")
  @Element
  val networkNewsItems: List<NetworkNewsItem>
)

fun NetworkNewsChannel.toDomainModel(source: String) : List<NewsItem> {
  return networkNewsItems.map {
    NewsItem(
      title = it.title,
      link = it.link,
      publishedDate = it.pubDate,
      source = it.source ?: source
    )
  }
}

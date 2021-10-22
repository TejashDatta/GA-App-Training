package com.example.newsreader.network.data_transfer_objects

import com.example.newsreader.data.models.NewsItem
import com.tickaroo.tikxml.annotation.Element
import com.tickaroo.tikxml.annotation.Path
import com.tickaroo.tikxml.annotation.Xml

@Xml(name = "rss")
data class NetworkNewsChannel(
  @Path("channel")
  @Element
  val networkNewsItem: List<NetworkNewsItem>
)

fun NetworkNewsChannel.toDomainModel() : List<NewsItem> {
  return networkNewsItem.map {
    NewsItem(
      title = it.title,
      link = it.link,
      pubDate = it.pubDate
    )
  }
}

package com.example.newsreader.network.data_transfer_objects.google_news

import com.example.newsreader.data.models.NewsItem
import com.tickaroo.tikxml.annotation.Element
import com.tickaroo.tikxml.annotation.Path
import com.tickaroo.tikxml.annotation.Xml

@Xml(name = "rss")
data class NetworkGoogleNewsChannel(
  @Path("channel")
  @Element
  val networkGoogleNewsItems: List<NetworkGoogleNewsItem>
)

fun NetworkGoogleNewsChannel.toDomainModel() : List<NewsItem> {
  return networkGoogleNewsItems.map {
    NewsItem(
      title = it.title.removeSuffix(" - ${it.source}"),
      link = it.link,
      pubDate = it.pubDate,
      source = it.source
    )
  }
}

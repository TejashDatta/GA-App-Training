package com.example.newsreader.network.data_transfer_objects.toyokeizai_news

import com.example.newsreader.data.models.NewsItem
import com.tickaroo.tikxml.annotation.Element
import com.tickaroo.tikxml.annotation.Path
import com.tickaroo.tikxml.annotation.Xml

const val TOYOKEIZAI_NEWS_SOURCE_NAME = "東洋経済オンライン"

@Xml(name = "rss")
data class NetworkToyokeizaiNewsChannel(
  @Path("channel")
  @Element
  val networkToyokeizaiNewsItems: List<NetworkToyokeizaiNewsItem>
)

fun NetworkToyokeizaiNewsChannel.toDomainModel() : List<NewsItem> {
  return networkToyokeizaiNewsItems.map {
    NewsItem(
      title = it.title,
      link = it.link,
      publishedDate = it.pubDate,
      source = TOYOKEIZAI_NEWS_SOURCE_NAME
    )
  }
}

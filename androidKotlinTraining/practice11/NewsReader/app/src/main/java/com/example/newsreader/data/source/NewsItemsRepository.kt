package com.example.newsreader.data.source

import com.example.newsreader.data.models.NewsItem
import com.example.newsreader.network.GoogleNewsApi
import com.example.newsreader.network.ToyokeizaiNewsApi
import com.example.newsreader.network.data_transfer_objects.google_news.toDomainModel
import com.example.newsreader.network.data_transfer_objects.toyokeizai_news.toDomainModel
import io.reactivex.rxjava3.core.Observable

object NewsItemsRepositoryLocator {
  val repository = NewsItemsRepository(GoogleNewsApi, ToyokeizaiNewsApi)
}

class NewsItemsRepository(
  private val googleNewsApi: GoogleNewsApi, private val toyokeizaiNewsApi: ToyokeizaiNewsApi
) {
  private var cachedNewsItems: List<NewsItem>? = null

  fun getNewsItems(): Observable<List<NewsItem>> {
    return if (cachedNewsItems == null) {
      requestNewsItems().doOnNext { newsItems -> cachedNewsItems = newsItems }
    } else {
      Observable.just(cachedNewsItems!!)
    }
  }

  private fun requestNewsItems(): Observable<List<NewsItem>> {
    return Observable.zip(
      googleNewsApi.retrofitService.getNewsChannel(),
      toyokeizaiNewsApi.retrofitService.getNewsChannel(),
      { networkGoogleNewsChannel, networkToyokeizaiNewsChannel ->
        (networkGoogleNewsChannel.toDomainModel() + networkToyokeizaiNewsChannel.toDomainModel())
          .sortedByDescending { it.pubDate }
      }
    )
  }
}

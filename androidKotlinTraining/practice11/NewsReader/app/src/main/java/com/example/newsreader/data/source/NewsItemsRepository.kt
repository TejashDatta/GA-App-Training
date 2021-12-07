package com.example.newsreader.data.source

import com.example.newsreader.data.models.NewsItem
import com.example.newsreader.network.GoogleNewsApi
import com.example.newsreader.network.data_transfer_objects.google_news.toDomainModel
import io.reactivex.rxjava3.core.Observable

object NewsItemsRepositoryLocator {
  val repository = NewsItemsRepository(GoogleNewsApi)
}

class NewsItemsRepository(private val googleNewsApi: GoogleNewsApi) {
  private var cachedNewsItems: List<NewsItem>? = null

  fun getNewsItems(): Observable<List<NewsItem>> {
    return if (cachedNewsItems == null) {
      requestNewsItems().doOnNext { newsItems -> cachedNewsItems = newsItems }
    } else {
      Observable.just(cachedNewsItems!!)
    }
  }

  private fun requestNewsItems(): Observable<List<NewsItem>> {
    return googleNewsApi.retrofitService.getNewsChannel()
      .map { newsChannel -> newsChannel.toDomainModel() }
  }
}

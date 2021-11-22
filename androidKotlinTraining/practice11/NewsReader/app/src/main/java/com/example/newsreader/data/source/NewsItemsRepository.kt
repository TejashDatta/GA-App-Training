package com.example.newsreader.data.source

import com.example.newsreader.data.models.NewsItem
import com.example.newsreader.network.NewsApi
import com.example.newsreader.network.data_transfer_objects.toDomainModel
import io.reactivex.rxjava3.core.Observable

object NewsItemsRepositoryLocator {
  val repository = NewsItemsRepository(NewsApi)
}

class NewsItemsRepository(private val newsApi: NewsApi) {
  private var cachedNewsItems: List<NewsItem>? = null

  fun getNewsItems(): Observable<List<NewsItem>> {
    return if (cachedNewsItems == null) {
      requestNewsItems().doOnNext { newsItems -> cachedNewsItems = newsItems }
    } else {
      Observable.just(cachedNewsItems!!)
    }
  }

  private fun requestNewsItems(): Observable<List<NewsItem>> {
    return newsApi.retrofitService.getNewsChannel().map { newsChannel -> newsChannel.toDomainModel() }
  }
}

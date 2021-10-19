package com.example.newsreader.data

import android.util.Log
import com.example.newsreader.data.models.NewsItem
import com.example.newsreader.network.NewsApi
import com.example.newsreader.network.data_transfer_objects.asDomainModel
import io.reactivex.rxjava3.core.Observable

object NewsItemsRepository {
  private var cachedNewsItems: List<NewsItem>? = null

  fun getNewsItems(): Observable<List<NewsItem>> {
    return if (cachedNewsItems == null) {
      requestNewsItems().doOnNext { newsItems ->
        cachedNewsItems = newsItems
        Log.d("NewsItemsRepository", cachedNewsItems.toString())
      }
    }
    else {
      Observable.just(cachedNewsItems!!)
    }
  }

  private fun requestNewsItems(): Observable<List<NewsItem>> {
    return NewsApi.retrofitService.getNewsChannel().map { newsChannel -> newsChannel.asDomainModel() }
  }
}

package com.example.newsreader.data.source

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.example.newsreader.data.models.NewsItem
import com.example.newsreader.network.GoogleNewsApi
import com.example.newsreader.network.ToyokeizaiNewsApi
import com.example.newsreader.network.data_transfer_objects.google_news.toDomainModel
import com.example.newsreader.network.data_transfer_objects.toyokeizai_news.toDomainModel
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.BehaviorSubject

object NewsItemsRepositoryFactory: Application() {
  private const val FOLLOWED_NEWS_SHARED_PREFERENCES_KEY = "FOLLOWED_NEWS_SHARED_PREFERENCES"
  private const val RECENT_NEWS_SHARED_PREFERENCES_KEY = "RECENT_NEWS_SHARED_PREFERENCES"

  private val followedNewsSharedPreferences =
    applicationContext.getSharedPreferences(FOLLOWED_NEWS_SHARED_PREFERENCES_KEY, Context.MODE_PRIVATE)

  private val recentNewsSharedPreferences =
    applicationContext.getSharedPreferences(RECENT_NEWS_SHARED_PREFERENCES_KEY, Context.MODE_PRIVATE)

  val repository =
    NewsItemsRepository(
      GoogleNewsApi,
      ToyokeizaiNewsApi,
      followedNewsSharedPreferences,
      recentNewsSharedPreferences
    )
}

class NewsItemsRepository(
  private val googleNewsApi: GoogleNewsApi,
  private val toyokeizaiNewsApi: ToyokeizaiNewsApi,
  followedNewsSharedPreferences: SharedPreferences,
  recentNewsSharedPreferences: SharedPreferences
) {
  private var cachedNewsItems: List<NewsItem>? = null
  private var followedNewsManager = FollowedNewsManager(followedNewsSharedPreferences)
  private var recentNewsManager = RecentNewsManager(recentNewsSharedPreferences)

  fun getNewsItems(): Observable<List<NewsItem>> {
    return if (cachedNewsItems == null) {
      requestNewsItems().doOnNext { newsItems -> cachedNewsItems = newsItems }
    } else {
      Observable.just(cachedNewsItems!!)
    }
  }

  val followedNewsItemsSubject: BehaviorSubject<List<NewsItem>>
    get() = followedNewsManager.itemsSubject

  fun newsIsFollowed(newsItem: NewsItem) = followedNewsManager.isSaved(newsItem)

  fun addFollowedNews(newsItem: NewsItem) = followedNewsManager.add(newsItem)

  fun removeFollowedNews(newsItem: NewsItem) = followedNewsManager.remove(newsItem)

  val recentNewsItems: List<NewsItem>
    get() = recentNewsManager.items

  fun addRecentNews(newsItem: NewsItem) = recentNewsManager.add(newsItem)

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

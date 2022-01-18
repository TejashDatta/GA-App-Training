package com.example.newsreader.data.source

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.example.newsreader.data.models.NewsItem
import com.example.newsreader.data.models.NewsSource
import com.example.newsreader.network.GoogleNewsApi
import com.example.newsreader.network.ToyokeizaiNewsApi
import com.example.newsreader.network.data_transfer_objects.google_news.toDomainModel
import com.example.newsreader.network.data_transfer_objects.toyokeizai_news.toDomainModel
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.BehaviorSubject

object NewsItemsRepositoryFactory: Application() {
  private const val FOLLOWED_NEWS_SHARED_PREFERENCES_KEY = "FOLLOWED_NEWS_SHARED_PREFERENCES"
  private const val RECENT_NEWS_SHARED_PREFERENCES_KEY = "RECENT_NEWS_SHARED_PREFERENCES"

  private var repository : NewsItemsRepository? = null

  fun getInstance(context: Context) = repository ?: initRepository(context)

  private fun initRepository(context: Context): NewsItemsRepository  {
    val followedNewsSharedPreferences =
      context.getSharedPreferences(FOLLOWED_NEWS_SHARED_PREFERENCES_KEY, Context.MODE_PRIVATE)

    val recentNewsSharedPreferences =
      context.getSharedPreferences(RECENT_NEWS_SHARED_PREFERENCES_KEY, Context.MODE_PRIVATE)

    val newRepository = NewsItemsRepository(
        GoogleNewsApi,
        ToyokeizaiNewsApi,
        followedNewsSharedPreferences,
        recentNewsSharedPreferences
      )

    repository = newRepository
    return newRepository
  }
}

class NewsItemsRepository(
  private val googleNewsApi: GoogleNewsApi,
  private val toyokeizaiNewsApi: ToyokeizaiNewsApi,
  followedNewsSharedPreferences: SharedPreferences,
  recentNewsSharedPreferences: SharedPreferences
) {
  private var followedNewsManager = FollowedNewsManager(followedNewsSharedPreferences)
  private var recentNewsManager = RecentNewsManager(recentNewsSharedPreferences)

  private var cachedGoogleNews: List<NewsItem>? = null
  private var cachedToyokeizaiNews: List<NewsItem>? = null

  private fun requestGoogleNews(): Observable<List<NewsItem>> {
    return googleNewsApi.retrofitService.getNewsChannel().map { it.toDomainModel() }
  }

  private fun requestToyokeizaiNews(): Observable<List<NewsItem>> {
    return toyokeizaiNewsApi.retrofitService.getNewsChannel().map { it.toDomainModel() }
  }

  fun getGoogleNews(refresh: Boolean): Observable<List<NewsItem>> {
    return if (refresh || cachedGoogleNews == null) {
      requestGoogleNews().doOnNext { cachedGoogleNews = it }
    } else {
      Observable.just(cachedGoogleNews!!)
    }
  }

  fun getToyokeizaiNews(refresh: Boolean): Observable<List<NewsItem>> {
    return if (refresh || cachedToyokeizaiNews == null) {
      requestToyokeizaiNews().doOnNext { cachedToyokeizaiNews = it }
    } else {
      Observable.just(cachedToyokeizaiNews!!)
    }
  }

  fun getAllNews(refresh: Boolean): Observable<List<NewsItem>> {
    return Observable.zip(
      getGoogleNews(refresh),
      getToyokeizaiNews(refresh),
      { googleNews, toyokeizaiNews ->
        (googleNews + toyokeizaiNews).sortedByDescending { it.publishedDate }
      }
    )
  }

  val followedNewsItemsSubject: BehaviorSubject<List<NewsItem>>
    get() = followedNewsManager.itemsSubject

  fun isNewsFollowed(newsItem: NewsItem) = followedNewsManager.isSaved(newsItem)

  fun addFollowedNews(newsItem: NewsItem) = followedNewsManager.add(newsItem)

  fun removeFollowedNews(newsItem: NewsItem) = followedNewsManager.remove(newsItem)

  val recentNewsItems: List<NewsItem>
    get() = recentNewsManager.items

  fun addRecentNews(newsItem: NewsItem) = recentNewsManager.add(newsItem)

  fun addNewsSource(newsSource: NewsSource) {
    Log.d("NewsItemsRepository", newsSource.toString())
//    TODO: create news source manager
  }
}

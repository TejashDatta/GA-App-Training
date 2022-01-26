package com.example.newsreader.data.source

import android.app.Application
import android.content.Context
import com.example.newsreader.data.models.NewsItem
import com.example.newsreader.data.models.NewsSource
import com.example.newsreader.network.NewsApi
import com.example.newsreader.network.data_transfer_objects.news.toDomainModel
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.BehaviorSubject

object NewsRepositoryFactory: Application() {
  private const val FOLLOWED_NEWS_SHARED_PREFERENCES_KEY = "FOLLOWED_NEWS_SHARED_PREFERENCES"
  private const val RECENT_NEWS_SHARED_PREFERENCES_KEY = "RECENT_NEWS_SHARED_PREFERENCES"
  private const val NEWS_SOURCES_SHARED_PREFERENCES_KEY = "NEWS_SOURCES_SHARED_PREFERENCES"

  private var repository : NewsRepository? = null

  fun getInstance(context: Context) = repository ?: initRepository(context)

  private fun initRepository(context: Context): NewsRepository  {
    val followedNewsSharedPreferences =
      context.getSharedPreferences(FOLLOWED_NEWS_SHARED_PREFERENCES_KEY, Context.MODE_PRIVATE)

    val recentNewsSharedPreferences =
      context.getSharedPreferences(RECENT_NEWS_SHARED_PREFERENCES_KEY, Context.MODE_PRIVATE)

    val newsSourcesSharedPreferences =
      context.getSharedPreferences(NEWS_SOURCES_SHARED_PREFERENCES_KEY, Context.MODE_PRIVATE)

    return NewsRepository(
      NewsApi,
      FollowedNewsManager(followedNewsSharedPreferences),
      RecentNewsManager(recentNewsSharedPreferences),
      NewsSourcesManager(newsSourcesSharedPreferences)
    ).also {
      repository = it
    }
  }
}

class NewsRepository(
  private val newsApi: NewsApi,
  private val followedNewsManager: FollowedNewsManager,
  private val recentNewsManager: RecentNewsManager,
  private val newsSourcesManager: NewsSourcesManager
) {

  private var generalNewsCache = HashMap<String, List<NewsItem>>()

  private fun requestGeneralNews(newsSource: NewsSource): Observable<List<NewsItem>> {
    return newsApi.retrofitService
             .getNewsChannel(newsSource.url).map { it.toDomainModel(newsSource.name) }
  }

  private fun getGeneralNews(newsSource: NewsSource, refresh: Boolean): Observable<List<NewsItem>> {
    return if (refresh || !generalNewsCache.containsKey(newsSource.name)) {
      requestGeneralNews(newsSource).doOnNext { generalNewsCache[newsSource.name] = it }
    } else {
      Observable.just(generalNewsCache[newsSource.name]!!)
    }
  }

  private fun getAllNews(refresh: Boolean): Observable<List<NewsItem>> {
    val getNewsObservables = mutableListOf<Observable<List<NewsItem>>>()
    newsSourcesSubject.value?.let { newsSources ->
      newsSources.forEach { getNewsObservables.add(getGeneralNews(it, refresh)) }
    }

    return Observable.zip(
      getNewsObservables
    ) { newsItemsLists -> newsItemsLists
                            .map { it as List<NewsItem> }
                            .flatten()
                            .sortedByDescending { it.publishedDate }
    }
  }

  fun findNewsSource(newsSourceName: String): NewsSource {
    return newsSourcesSubject.value?.find { it.name == newsSourceName }
      ?: throw NoSuchElementException("NewsSource with name $newsSourceName does not exist")
  }

  fun getNews(newsSource: NewsSource, refresh: Boolean): Observable<List<NewsItem>> {
    return when(newsSource.name) {
      NewsSourcesManager.ALL_NEWS_NAME -> getAllNews(refresh)
      else -> getGeneralNews(newsSource, refresh)
    }
  }

  val followedNewsItemsSubject: BehaviorSubject<List<NewsItem>>
    get() = followedNewsManager.itemsSubject

  fun isNewsFollowed(newsItem: NewsItem) = followedNewsManager.isSaved(newsItem)

  fun addFollowedNews(newsItem: NewsItem) = followedNewsManager.add(newsItem)

  fun removeFollowedNews(newsItem: NewsItem) = followedNewsManager.remove(newsItem)

  val recentNewsItems: List<NewsItem>
    get() = recentNewsManager.items

  fun addRecentNews(newsItem: NewsItem) = recentNewsManager.add(newsItem)

  val newsSourcesSubject: BehaviorSubject<List<NewsSource>>
    get() = newsSourcesManager.newsSourcesSubject

  fun addNewsSource(newsSource: NewsSource) {
    newsSourcesManager.add(newsSource)
  }
}

package com.example.newsreader.data.source

import android.app.Application
import android.content.Context
import com.example.newsreader.data.models.NewsItem
import com.example.newsreader.data.models.NewsSource
import com.example.newsreader.network.GeneralNewsApi
import com.example.newsreader.network.GoogleNewsApi
import com.example.newsreader.network.ToyokeizaiNewsApi
import com.example.newsreader.network.data_transfer_objects.general_news.toDomainModel
import com.example.newsreader.network.data_transfer_objects.google_news.toDomainModel
import com.example.newsreader.network.data_transfer_objects.toyokeizai_news.toDomainModel
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
      GoogleNewsApi,
      ToyokeizaiNewsApi,
      GeneralNewsApi,
      FollowedNewsManager(followedNewsSharedPreferences),
      RecentNewsManager(recentNewsSharedPreferences),
      NewsSourcesManager(newsSourcesSharedPreferences)
    ).also {
      repository = it
    }
  }
}

class NewsRepository(
  private val googleNewsApi: GoogleNewsApi,
  private val toyokeizaiNewsApi: ToyokeizaiNewsApi,
  private val generalNewsApi: GeneralNewsApi,
  private val followedNewsManager: FollowedNewsManager,
  private val recentNewsManager: RecentNewsManager,
  private val newsSourcesManager: NewsSourcesManager
) {

  private var cachedGoogleNews: List<NewsItem>? = null
  private var cachedToyokeizaiNews: List<NewsItem>? = null
  private var generalNewsCache = HashMap<String, List<NewsItem>>()

  private fun requestGoogleNews(): Observable<List<NewsItem>> {
    return googleNewsApi.retrofitService.getNewsChannel().map { it.toDomainModel() }
  }

  private fun requestToyokeizaiNews(): Observable<List<NewsItem>> {
    return toyokeizaiNewsApi.retrofitService.getNewsChannel().map { it.toDomainModel() }
  }

  private fun requestGeneralNews(newsSource: NewsSource): Observable<List<NewsItem>> {
    return generalNewsApi.retrofitService
             .getNewsChannel(newsSource.url).map { it.toDomainModel(newsSource.name) }
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

  fun getGeneralNews(newsSource: NewsSource, refresh: Boolean): Observable<List<NewsItem>> {
    return if (refresh || !generalNewsCache.containsKey(newsSource.name)) {
      requestGeneralNews(newsSource).doOnNext { generalNewsCache[newsSource.name] = it }
    } else {
      Observable.just(generalNewsCache[newsSource.name]!!)
    }
  }

  fun getAllNews(refresh: Boolean): Observable<List<NewsItem>> {
    val getNewsObservables = mutableListOf<Observable<List<NewsItem>>>()
    getNewsObservables.add(getGoogleNews(refresh))
    getNewsObservables.add(getToyokeizaiNews(refresh))
    newsSourcesSubject.value?.let { newsSources ->
      newsSources.forEach { getNewsObservables.add(getGeneralNews(it, refresh)) }
    }

    return Observable.zip(
      getNewsObservables
    ) { newsItemsLists ->
        val allNewsItems = mutableListOf<NewsItem>()
        newsItemsLists.forEach { allNewsItems.addAll(it as List<NewsItem>) }
        allNewsItems.sortedByDescending { it.publishedDate }
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

package com.example.newsreader.data.source

import android.content.SharedPreferences
import android.util.Log
import com.example.newsreader.data.models.NewsSource
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import io.reactivex.rxjava3.subjects.BehaviorSubject

class NewsSourcesManager(private val sharedPreferences: SharedPreferences) {
  companion object {
    private const val NEWS_SOURCES_KEY = "news_sources"
    const val ALL_NEWS_NAME = "すべての記事一覧"
    const val GOOGLE_NEWS_NAME = "Google News"
    const val TOYOKEIZAI_NEWS_NAME = "東洋経済オンライン"
  }

  private val defaultNewsSources = listOf(
    NewsSource(ALL_NEWS_NAME, "NA"),
    NewsSource(GOOGLE_NEWS_NAME, "NA"),
    NewsSource(TOYOKEIZAI_NEWS_NAME, "https://toyokeizai.net/list/feed/rss/")
  )

  private val jsonAdapter: JsonAdapter<List<NewsSource>> =
    Moshi.Builder().build()
      .adapter(Types.newParameterizedType(List::class.java, NewsSource::class.java))
  
  private var newsSources = mutableListOf<NewsSource>()

  val newsSourcesSubject: BehaviorSubject<List<NewsSource>> = BehaviorSubject.createDefault((emptyList()))

  init {
    load()
    logOutput()
  }

  private fun publishChanges() {
    newsSourcesSubject.onNext(newsSources)
  }

  private fun save() {
    sharedPreferences
      .edit()
      .putString(NEWS_SOURCES_KEY, jsonAdapter.toJson(newsSources))
      .apply()
  }

  private fun load() {
    val safeJson = sharedPreferences.getString(NEWS_SOURCES_KEY, null) ?: return
    jsonAdapter.fromJson(safeJson)?.let {
      newsSources = (defaultNewsSources + it.toMutableList()) as MutableList<NewsSource>
    }
    publishChanges()
  }

  private fun logOutput() {
    Log.d("NewsSourcesManager", newsSources.toString())
  }

  fun add(newsSource: NewsSource) {
    newsSources.add(newsSource)
    save()
    logOutput()
    publishChanges()
  }
}

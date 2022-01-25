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
  }

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
      newsSources = it.toMutableList()
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

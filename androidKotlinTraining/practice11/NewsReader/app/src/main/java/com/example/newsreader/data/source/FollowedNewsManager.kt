package com.example.newsreader.data.source

import android.content.SharedPreferences
import android.util.Log
import com.example.newsreader.data.models.NewsItem
import com.squareup.moshi.*
import io.reactivex.rxjava3.subjects.BehaviorSubject
import org.threeten.bp.ZonedDateTime

class FollowedNewsManager(private val sharedPreferences: SharedPreferences) {
  companion object {
    private const val ITEMS_KEY = "followed_news"
  }

  class ZonedDateTimeAdapter {
    @ToJson fun toJson(zonedDateTime: ZonedDateTime) = zonedDateTime.toString()

    @FromJson fun fromJson(dateTimeString: String) = ZonedDateTime.parse(dateTimeString)
  }

  private val jsonAdapter: JsonAdapter<List<NewsItem>> =
    Moshi.Builder().add(ZonedDateTimeAdapter()).build()
      .adapter(Types.newParameterizedType(List::class.java, NewsItem::class.java))
  
  private var items = mutableListOf<NewsItem>()

  val itemsSubject: BehaviorSubject<List<NewsItem>> = BehaviorSubject.create()

  init {
    loadItems()
    logOutput()
  }

  private fun publishChanges() {
    itemsSubject.onNext(items)
  }

  private fun saveItems() {
    sharedPreferences
      .edit()
      .putString(ITEMS_KEY, jsonAdapter.toJson(items))
      .apply()
  }

  private fun loadItems() {
    val safeJson = sharedPreferences.getString(ITEMS_KEY, null) ?: return
    jsonAdapter.fromJson(safeJson)?.let {
      items = it.toMutableList()
    }
    publishChanges()
  }

  private fun logOutput() {
    Log.d("FollowedNewsManager", items.toString())
  }

  fun isSaved(newsItem: NewsItem) = newsItem in items

  fun add(newsItem: NewsItem) {
    items.add(newsItem)
    saveItems()
    logOutput()
    publishChanges()
  }

  fun remove(newsItem: NewsItem) {
    items.remove(newsItem)
    saveItems()
    logOutput()
    publishChanges()
  }
}

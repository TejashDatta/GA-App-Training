package com.example.newsreader.followed_news

import android.content.SharedPreferences
import com.example.newsreader.data.models.NewsItem
import com.squareup.moshi.*
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
  
  private var _items = mutableListOf<NewsItem>()
  val items: List<NewsItem>
    get() = _items

  init {
    loadItems()
  }

  private fun saveItems() {
    sharedPreferences
      .edit()
      .putString(ITEMS_KEY, jsonAdapter.toJson(_items))
      .apply()
  }

  private fun loadItems() {
    val safeJson = sharedPreferences.getString(ITEMS_KEY, null) ?: return
    jsonAdapter.fromJson(safeJson)?.let {
      _items = it.toMutableList()
    }
  }

  fun add(newsItem: NewsItem) {
    _items.add(newsItem)
    saveItems()
  }

  fun remove(newsItem: NewsItem) {
    _items.remove(newsItem)
    saveItems()
  }
}

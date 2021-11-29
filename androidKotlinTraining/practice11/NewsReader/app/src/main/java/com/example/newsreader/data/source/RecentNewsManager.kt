package com.example.newsreader.data.source

import android.content.SharedPreferences
import android.util.Log
import com.example.newsreader.data.models.NewsItem
import com.example.newsreader.data.models.RecentNewsItem
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types

class RecentNewsManager(private val sharedPreferences: SharedPreferences) {
  companion object {
    private const val ITEMS_KEY = "recent_news"
  }

  private val jsonAdapter: JsonAdapter<List<RecentNewsItem>> =
    Moshi.Builder().build()
      .adapter(Types.newParameterizedType(List::class.java, RecentNewsItem::class.java))
  
  private var _items = mutableListOf<RecentNewsItem>()
  val items: List<RecentNewsItem>
    get() = _items

  init {
    loadItems()
    logOutput()
  }

  private fun newsItemToRecentNewsItem(newsItem: NewsItem): RecentNewsItem {
    return RecentNewsItem(
      title = newsItem.title,
      link = newsItem.link,
      source = newsItem.source
    )
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

  private fun logOutput() {
    Log.d("RecentNewsManager", _items.toString())
  }

  fun add(newsItem: NewsItem) {
    _items.add(newsItemToRecentNewsItem(newsItem))
    saveItems()
    logOutput()
  }
}

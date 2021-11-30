package com.example.newsreader.data.source

import android.content.SharedPreferences
import android.util.Log
import com.example.newsreader.data.models.NewsItem
import com.example.newsreader.data.models.RecentNewsItem
import com.squareup.moshi.*

class RecentNewsManager(private val sharedPreferences: SharedPreferences) {
  companion object {
    private const val MAX_ITEMS = 20
    private const val ITEMS_KEY = "recent_news"
  }

  class ArrayDequeAdapter {
    @FromJson
    fun fromJson(items: Array<RecentNewsItem>) = ArrayDeque(items.toList())

    @ToJson
    fun toJson(items: ArrayDeque<RecentNewsItem>) = items.toArray()
  }

  private val jsonAdapter: JsonAdapter<ArrayDeque<RecentNewsItem>> =
    Moshi.Builder().add(ArrayDequeAdapter()).build()
      .adapter(Types.newParameterizedType(ArrayDeque::class.java, RecentNewsItem::class.java))

  private var _items = ArrayDeque<RecentNewsItem>(MAX_ITEMS)
  val items: List<RecentNewsItem>
    get() = _items.toList()

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
    _items = jsonAdapter.fromJson(safeJson)!!
  }

  private fun isFull() = _items.size == MAX_ITEMS

  private fun logOutput() {
    Log.d("RecentNewsManager", _items.toString())
  }

  fun add(newsItem: NewsItem) {
    if (isFull()) _items.removeFirst()
    _items.add(newsItemToRecentNewsItem(newsItem))
    saveItems()
    logOutput()
  }
}

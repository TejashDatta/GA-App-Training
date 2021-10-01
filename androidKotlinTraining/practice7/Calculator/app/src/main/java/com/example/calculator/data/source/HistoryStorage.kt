package com.example.calculator.data.source

import android.content.SharedPreferences
import android.util.Log
import com.squareup.moshi.*
import kotlin.collections.ArrayDeque

class HistoryStorage(private val sharedPreferences: SharedPreferences) {
  companion object {
    private const val QUEUE_SIZE = 30
    private const val ITEMS_KEY = "items"
  }

  class ArrayDequeAdapter {
    @FromJson
    fun fromJson(items: Array<String>) = ArrayDeque(items.toList())

    @ToJson
    fun toJson(items: ArrayDeque<String>) = items.toArray()
  }

  private var jsonAdapter: JsonAdapter<ArrayDeque<String>> =
    Moshi.Builder().add(ArrayDequeAdapter()).build()
      .adapter(Types.newParameterizedType(ArrayDeque::class.java, String::class.java))
  
  private var _items = ArrayDeque<String>(QUEUE_SIZE)
  val items: List<String>
    get() = _items.toList()

  init {
    loadItems()
    debugOutput()
  }

  private fun debugOutput() {
    Log.d("HistoryStorage", items.toString())
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

  fun addItem(item: String) {
    if (_items.size >= QUEUE_SIZE) _items.removeFirst()
    _items.addLast(item)
    saveItems()
    debugOutput()
  }
}

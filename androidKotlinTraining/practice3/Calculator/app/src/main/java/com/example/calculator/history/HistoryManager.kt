package com.example.calculator.history

import android.content.SharedPreferences
import android.util.Log
import com.squareup.moshi.*
import kotlin.collections.ArrayDeque

class HistoryManager(private val sharedPreferences: SharedPreferences) {
  companion object {
    private const val QUEUE_SIZE = 30
    private const val ITEMS_KEY = "items"
  }

  class ArrayDequeAdapter {
    @FromJson
    fun fromJson(arrayItems: Array<*>): ArrayDeque<String> {
      val items = ArrayDeque<String>(QUEUE_SIZE)
      arrayItems.forEach { items.addLast(it as String) }
      return items
    }

    @ToJson
    fun toJson(items: ArrayDeque<String>) = items.toArray()
  }

  private var jsonAdapter: JsonAdapter<ArrayDeque<String>> =
    Moshi.Builder().add(ArrayDequeAdapter()).build()
      .adapter(Types.newParameterizedType(ArrayDeque::class.java, String::class.java))
  var items = ArrayDeque<String>(QUEUE_SIZE)

  init {
    loadItems()
    debugOutput()
  }

  private fun debugOutput() {
    Log.d("HistoryManager", items.toString())
  }

  private fun saveItems() {
    sharedPreferences
      .edit()
      .putString(ITEMS_KEY, jsonAdapter.toJson(items))
      .apply()
  }

  private fun loadItems() {
    val safeJson = sharedPreferences.getString(ITEMS_KEY, null) ?: return
    items = jsonAdapter.fromJson(safeJson)!!
  }

  fun addItem(item: String) {
    if (items.size >= QUEUE_SIZE) items.removeFirst()
    items.addLast(item)
    saveItems()
    debugOutput()
  }
}


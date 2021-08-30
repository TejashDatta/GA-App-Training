package com.example.calculator.history

import android.content.SharedPreferences
import android.util.Log
import com.google.gson.Gson
import kotlin.collections.ArrayDeque

class HistoryManager(private val sharedPreferences: SharedPreferences) {
  companion object {
    private const val QUEUE_SIZE = 30
    private const val ITEMS_KEY = "items"
  }
  var items = ArrayDeque<String>()

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
      .putString(ITEMS_KEY, Gson().toJson(items))
      .apply()
  }

  private fun loadItems() {
    val safeJson = sharedPreferences.getString(ITEMS_KEY, null) ?: return
    items = Gson().fromJson(safeJson, ArrayDeque::class.java) as ArrayDeque<String>
  }

  fun add(item: String) {
    if (items.size >= QUEUE_SIZE) items.removeFirst()
    items.addLast(item)
    saveItems()
    debugOutput()
  }
}

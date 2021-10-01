package com.example.calculator.data.source

class HistoryRepository(private val historyManager: HistoryManager) {
  private var cachedItems: List<String>? = null
  private var cacheIsDirty = false

  fun getItems(): List<String> {
    if (cachedItems == null || cacheIsDirty) {
      cachedItems = historyManager.items
      cacheIsDirty = false
    }
    return cachedItems!!
  }

  fun addItem(item: String) {
    historyManager.addItem(item)
    cacheIsDirty = true
  }
}

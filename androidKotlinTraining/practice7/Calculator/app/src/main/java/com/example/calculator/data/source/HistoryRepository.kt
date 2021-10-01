package com.example.calculator.data.source

class HistoryRepository(private val historyStorage: HistoryStorage) {
  private var cachedItems: List<String>? = null
  private var cacheIsDirty = false

  fun getItems(): List<String> {
    if (cachedItems == null || cacheIsDirty) {
      cachedItems = historyStorage.items
      cacheIsDirty = false
    }
    return cachedItems!!
  }

  fun addItem(item: String) {
    historyStorage.addItem(item)
    cacheIsDirty = true
  }
}

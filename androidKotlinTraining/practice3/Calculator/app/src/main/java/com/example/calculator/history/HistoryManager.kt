package com.example.calculator.history

import android.util.Log
import kotlin.collections.ArrayDeque

object HistoryManager {
  private const val QUEUE_SIZE = 30
  val items = ArrayDeque<String>()

  private fun debugOutput() {
    items.forEach { Log.d("HistoryManager", it) }
  }

  fun add(item: String) {
    if (items.size >= QUEUE_SIZE) items.removeFirst()
    items.addLast(item)
    debugOutput()
  }
}

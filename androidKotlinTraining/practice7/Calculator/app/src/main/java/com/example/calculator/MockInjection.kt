package com.example.calculator

import android.content.Context
import android.content.Context.MODE_PRIVATE
import com.example.calculator.data.source.HistoryManager
import com.example.calculator.data.source.HistoryRepository

object MockInjection {
  private const val HISTORY_SHARED_PREFERENCES_KEY = "HISTORY_SHARED_PREFERENCES"

  fun provideHistoryManager(context: Context): HistoryManager {
    return HistoryManager(
      context.getSharedPreferences(HISTORY_SHARED_PREFERENCES_KEY, MODE_PRIVATE)
    )
  }

  fun provideHistoryRepository(context: Context): HistoryRepository {
    return HistoryRepository(provideHistoryManager(context))
  }
}

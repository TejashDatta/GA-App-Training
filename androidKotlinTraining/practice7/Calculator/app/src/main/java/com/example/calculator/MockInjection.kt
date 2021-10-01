package com.example.calculator

import android.content.Context
import android.content.Context.MODE_PRIVATE
import com.example.calculator.data.source.HistoryRepository
import com.example.calculator.data.source.HistoryStorage

object MockInjection {
  private const val HISTORY_SHARED_PREFERENCES_KEY = "HISTORY_SHARED_PREFERENCES"

  fun provideHistoryStorage(context: Context): HistoryStorage {
    return HistoryStorage(
      context.getSharedPreferences(HISTORY_SHARED_PREFERENCES_KEY, MODE_PRIVATE)
    )
  }

  fun provideHistoryRepository(context: Context): HistoryRepository {
    return HistoryRepository(provideHistoryStorage(context))
  }
}

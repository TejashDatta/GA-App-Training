package com.example.newsreader.data.source

import android.content.Context
import android.content.SharedPreferences

class SharedPreferencesRetriever(private val context: Context) {
  companion object {
    const val FOLLOWED_NEWS_SHARED_PREFERENCES_KEY = "FOLLOWED_NEWS_SHARED_PREFERENCES"
    const val RECENT_NEWS_SHARED_PREFERENCES_KEY = "RECENT_NEWS_SHARED_PREFERENCES"
  }

  fun retrieveFollowedNews(): SharedPreferences {
    return context.getSharedPreferences(FOLLOWED_NEWS_SHARED_PREFERENCES_KEY, Context.MODE_PRIVATE)
  }

  fun retrieveRecentNews(): SharedPreferences {
    return context.getSharedPreferences(RECENT_NEWS_SHARED_PREFERENCES_KEY, Context.MODE_PRIVATE)
  }
}

package com.example.newsreader.data.source

import android.content.Context
import android.content.SharedPreferences

class FollowedNewsSharedPreferencesRetriever(private val context: Context) {
  companion object {
    const val FOLLOWED_NEWS_SHARED_PREFERENCES_KEY = "FOLLOWED_NEWS_SHARED_PREFERENCES"
  }

  fun retrieve(): SharedPreferences {
    return context.getSharedPreferences(FOLLOWED_NEWS_SHARED_PREFERENCES_KEY, Context.MODE_PRIVATE)
  }
}

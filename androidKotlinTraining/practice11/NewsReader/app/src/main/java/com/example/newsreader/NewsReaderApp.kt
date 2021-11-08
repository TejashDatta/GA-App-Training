package com.example.newsreader

import com.jakewharton.threetenabp.AndroidThreeTen
import android.app.Application

class NewsReaderApp : Application() {
  override fun onCreate() {
    super.onCreate()
    AndroidThreeTen.init(this)
  }
}

package com.example.newsreader

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.newsreader.network.NewsApi
import com.jakewharton.threetenabp.AndroidThreeTen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

abstract class ScopedAppActivity: AppCompatActivity(), CoroutineScope by MainScope() {

  override fun onDestroy() {
    super.onDestroy()
    cancel()
  }
}

class MainActivity : ScopedAppActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    AndroidThreeTen.init(this)

    // TODO: change later
    launch {
      val newsChannel = NewsApi.retrofitService.getNewsChannel()
      Log.d("MainActivity", newsChannel.toString())
      Log.d("MainActivity", newsChannel.newsItems[0].pubDate.toString())
    }

    setContentView(R.layout.activity_main)
  }
}

package com.example.newsreader

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.newsreader.network.NewsApi
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

    // TODO: change later
    launch {
      val newsChannel = NewsApi.retrofitService.getNewsChannel()
      Log.d("MainActivity", newsChannel.toString())
    }

    setContentView(R.layout.activity_main)
  }
}

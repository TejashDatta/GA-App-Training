package com.example.newsreader

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.newsreader.network.NewsApi
import com.jakewharton.threetenabp.AndroidThreeTen
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class MainActivity : AppCompatActivity() {
  var compositeDisposable = CompositeDisposable()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    compositeDisposable.add(NewsApi.retrofitService.getNewsChannel()
      .subscribeOn(Schedulers.io())
      .observeOn(AndroidSchedulers.mainThread())
      .subscribe(
        { newsChannel ->
          Log.d("MainActivity", newsChannel.toString())
          Log.d("MainActivity", newsChannel.newsItems[0].pubDate.toString())
        },
        { e -> Log.d("MainActivity", e.toString()) }
      )
    )

    setContentView(R.layout.activity_main)
  }

  override fun onStop() {
    compositeDisposable.clear();
    super.onStop();
  }
}

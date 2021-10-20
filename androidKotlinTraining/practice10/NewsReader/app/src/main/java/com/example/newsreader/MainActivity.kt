package com.example.newsreader

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.newsreader.data.NewsItemsRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class MainActivity : AppCompatActivity() {
  private var compositeDisposable = CompositeDisposable()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    compositeDisposable.add(NewsItemsRepository.getNewsItems()
      .subscribeOn(Schedulers.io())
      .observeOn(AndroidSchedulers.mainThread())
      .subscribe(
        { newsItems ->
          Log.d("MainActivity", newsItems.toString())
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

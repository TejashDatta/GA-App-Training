package com.example.newsreader.news_index

import android.util.Log
import com.example.newsreader.data.NewsItemsRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class NewsIndexPresenter(
  private val newsIndexView: NewsIndexContract.View): NewsIndexContract.Presenter {

  var compositeDisposable = CompositeDisposable()

  init {
    newsIndexView.presenter = this
  }

  override fun start() {
    getAndSetNewsItems()
  }

  override fun end() {
    clearObservers()
  }

  private fun getAndSetNewsItems() {
    compositeDisposable.add(NewsItemsRepository.getNewsItems()
      .subscribeOn(Schedulers.io())
      .observeOn(AndroidSchedulers.mainThread())
      .subscribe(
        { newsItems -> newsIndexView.setRecyclerViewItems(newsItems) },
        { e -> Log.e("NewsIndexPresenter", e.toString()) }
      )
    )
  }

  private fun clearObservers() {
    compositeDisposable.clear()
  }
}

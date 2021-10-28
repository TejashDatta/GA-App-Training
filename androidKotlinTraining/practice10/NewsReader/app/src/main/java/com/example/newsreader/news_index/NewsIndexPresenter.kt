package com.example.newsreader.news_index

import android.util.Log
import com.example.newsreader.data.NewsItemsRepository
import com.example.newsreader.data.models.NewsItem
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers

class NewsIndexPresenter(
  private val newsIndexView: NewsIndexContract.View): NewsIndexContract.Presenter {

  var compositeDisposable = CompositeDisposable()

  init {
    newsIndexView.presenter = this
  }

  override fun start() {
    refreshNewsItems()
  }

  override fun end() {
    clearObservers()
  }

  override fun refreshNewsItems() {
    compositeDisposable.add(NewsItemsRepository.getNewsItems()
      .subscribeOn(Schedulers.io())
      .observeOn(AndroidSchedulers.mainThread())
      .doOnSubscribe { newsIndexView.showLoading() }
      .subscribeBy(
        onNext = { newsItems ->
            if (newsItems.isEmpty())
              newsIndexView.showNoResults()
            else
              newsIndexView.showItemsInRecyclerView(newsItems)
        },
        onError = { e ->
            newsIndexView.showError()
            Log.e("NewsIndexPresenter", e.toString())
        }
      )
    )
  }

  override fun onClickNewsItem(newsItem: NewsItem) {
    newsIndexView.openInTab(newsItem.link)
  }

  private fun clearObservers() {
    compositeDisposable.clear()
  }
}

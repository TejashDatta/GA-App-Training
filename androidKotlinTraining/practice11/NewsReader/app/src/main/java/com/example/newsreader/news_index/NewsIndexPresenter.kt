package com.example.newsreader.news_index

import android.util.Log
import com.example.newsreader.BaseSchedulerProvider
import com.example.newsreader.data.NewsItemsRepository
import com.example.newsreader.data.models.NewsItem
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.subscribeBy

class NewsIndexPresenter(
  private val newsIndexView: NewsIndexContract.View,
  private val newsItemsRepository: NewsItemsRepository,
  private val schedulerProvider: BaseSchedulerProvider
): NewsIndexContract.Presenter {

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
    compositeDisposable.add(newsItemsRepository.getNewsItems()
      .subscribeOn(schedulerProvider.io())
      .observeOn(schedulerProvider.ui())
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

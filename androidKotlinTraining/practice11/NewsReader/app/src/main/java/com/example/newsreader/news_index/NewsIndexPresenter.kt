package com.example.newsreader.news_index

import android.util.Log
import com.example.newsreader.BaseSchedulerProvider
import com.example.newsreader.data.source.NewsRepository
import com.example.newsreader.news_item.NewsItemFunctionsContract
import com.example.newsreader.news_item.NewsItemPresenterFunctions
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.subscribeBy

class NewsIndexPresenter(
  private val newsSourceName: String,
  private val newsIndexView: NewsIndexContract.View,
  private val newsRepository: NewsRepository,
  private val schedulerProvider: BaseSchedulerProvider
): NewsIndexContract.Presenter,
  NewsItemFunctionsContract.Presenter by NewsItemPresenterFunctions(newsIndexView, newsRepository)
{
  private var compositeDisposable = CompositeDisposable()

  init {
    newsIndexView.presenter = this
  }

  override fun start() {
    requestNews(refresh = false)
  }

  override fun end() {
    clearObservers()
  }

  override fun refreshNews() = requestNews(refresh = true)

  private fun requestNews(refresh: Boolean) {
    compositeDisposable.add(newsRepository.getNews(newsSourceName, refresh)
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

  private fun clearObservers() {
    compositeDisposable.clear()
  }
}

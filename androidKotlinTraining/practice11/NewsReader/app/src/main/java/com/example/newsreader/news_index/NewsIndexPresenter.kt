package com.example.newsreader.news_index

import android.util.Log
import com.example.newsreader.BaseSchedulerProvider
import com.example.newsreader.data.models.NewsItem
import com.example.newsreader.data.source.NewsRepository
import com.example.newsreader.news_item.NewsItemFunctionsContract
import com.example.newsreader.news_item.NewsItemPresenterFunctions
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.subscribeBy

class NewsIndexPresenter(
  private val newsSource: NewsSource,
  private val newsIndexView: NewsIndexContract.View,
  private val newsRepository: NewsRepository,
  private val schedulerProvider: BaseSchedulerProvider
): NewsIndexContract.Presenter,
  NewsItemFunctionsContract.Presenter by NewsItemPresenterFunctions(newsIndexView, newsRepository)
{
  enum class NewsSource { ALL, GOOGLE, TOYOKEIZAI }

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
    compositeDisposable.add(getNewsItemsObservable(refresh)
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

  private fun getNewsItemsObservable(refresh: Boolean): Observable<List<NewsItem>> {
    return when(newsSource) {
      NewsSource.ALL -> newsRepository.getAllNews(refresh)
      NewsSource.GOOGLE -> newsRepository.getGoogleNews(refresh)
      NewsSource.TOYOKEIZAI -> newsRepository.getToyokeizaiNews(refresh)
    }
  }

  private fun clearObservers() {
    compositeDisposable.clear()
  }
}

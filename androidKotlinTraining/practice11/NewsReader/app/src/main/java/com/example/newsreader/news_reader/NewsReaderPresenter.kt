package com.example.newsreader.news_reader

import android.util.Log
import com.example.newsreader.data.source.NewsRepository
import io.reactivex.rxjava3.disposables.CompositeDisposable

class NewsReaderPresenter(
  private val newsReaderView: NewsReaderContract.View,
  private val newsRepository: NewsRepository
): NewsReaderContract.Presenter {

  private var compositeDisposable = CompositeDisposable()

  override fun start() {
    newsReaderView.showAllNews()
    setupSubscriberToUpdateMainDrawerContent()
  }

  override fun end() {
    clearObservers()
  }

  override fun onClickAllNews() {
    newsReaderView.showAllNews()
  }

  override fun onClickGoogleNews() {
    newsReaderView.showGoogleNews()
  }

  override fun onClickToyokeizaiNews() {
    newsReaderView.showToyokeizaiNews()
  }

  override fun onClickFollowedNews() {
    newsReaderView.showFollowedNews()
  }

  override fun onClickRecentNews() {
    newsReaderView.showRecentNews()
  }

  override fun onClickGeneralNews(newsSourceName: String) {
    try {
      newsReaderView.showGeneralNews(newsRepository.findNewsSource(newsSourceName))
    } catch (e: Exception) {
      e.message?.let { Log.e("NewsReaderPresenter", it) }
    }
  }

  override fun onClickAddNewsSource() {
    newsReaderView.showAddNewsSource()
  }

  private fun setupSubscriberToUpdateMainDrawerContent() {
    compositeDisposable.add(newsRepository.newsSourcesSubject
      .subscribe { newsReaderView.setupDrawerMainContent(it) }
    )
  }

  private fun clearObservers() {
    compositeDisposable.clear()
  }
}

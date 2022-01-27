package com.example.newsreader.news_reader

import com.example.newsreader.data.source.NewsRepository
import com.example.newsreader.data.source.NewsSourcesManager
import io.reactivex.rxjava3.disposables.CompositeDisposable

class NewsReaderPresenter(
  private val newsReaderView: NewsReaderContract.View,
  private val newsRepository: NewsRepository
): NewsReaderContract.Presenter {

  private var compositeDisposable = CompositeDisposable()

  override fun start() {
    newsReaderView.showNews(NewsSourcesManager.ALL_NEWS_NAME)
    setupSubscriberToUpdateMainDrawerContent()
  }

  override fun end() {
    clearObservers()
  }

  override fun onClickNewsSource(newsSourceName: String) {
    newsReaderView.showNews(newsSourceName)
  }

  override fun onClickFollowedNews() {
    newsReaderView.showFollowedNews()
  }

  override fun onClickRecentNews() {
    newsReaderView.showRecentNews()
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

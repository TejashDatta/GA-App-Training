package com.example.newsreader.followed_news

import com.example.newsreader.BaseSchedulerProvider
import com.example.newsreader.data.source.NewsRepository
import com.example.newsreader.news_item.NewsItemFunctionsContract
import com.example.newsreader.news_item.NewsItemPresenterFunctions
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.subscribeBy

class FollowedNewsPresenter(
  private val followedNewsView: FollowedNewsContract.View,
  private val newsRepository: NewsRepository,
  private val schedulerProvider: BaseSchedulerProvider
): FollowedNewsContract.Presenter,
  NewsItemFunctionsContract.Presenter
    by NewsItemPresenterFunctions(followedNewsView, newsRepository)
{
  private var compositeDisposable = CompositeDisposable()

  init {
    followedNewsView.presenter = this
  }

  override fun start() {
    setupView()
  }

  override fun end() {
    clearObservers()
  }

  private fun setupView() {
    compositeDisposable.add(newsRepository.followedNewsItemsSubject
      .observeOn(schedulerProvider.ui())
      .subscribeBy(
        onNext = { newsItems ->
          if (newsItems.isEmpty())
            followedNewsView.showNoFollowedItems()
          else
            followedNewsView.showItemsInRecyclerView(newsItems)
        }))
  }

  private fun clearObservers() {
    compositeDisposable.clear()
  }
}

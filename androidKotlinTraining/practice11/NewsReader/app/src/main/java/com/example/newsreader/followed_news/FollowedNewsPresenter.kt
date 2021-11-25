package com.example.newsreader.followed_news

import com.example.newsreader.BaseSchedulerProvider
import com.example.newsreader.data.source.FollowedNewsManager
import com.example.newsreader.news_item.NewsItemFunctionsContract
import com.example.newsreader.news_item.NewsItemPresenterFunctions
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.subscribeBy

class FollowedNewsPresenter(
  private val newsIndexView: FollowedNewsContract.View,
  private val followedNewsManager: FollowedNewsManager,
  private val schedulerProvider: BaseSchedulerProvider
): FollowedNewsContract.Presenter,
  NewsItemFunctionsContract.Presenter by NewsItemPresenterFunctions(newsIndexView, followedNewsManager)
{
  private var compositeDisposable = CompositeDisposable()

  init {
    newsIndexView.presenter = this
  }

  override fun start() {
    setupView()
  }

  override fun end() {
    clearObservers()
  }

  private fun setupView() {
    compositeDisposable.add(followedNewsManager.itemsSubject
      .observeOn(schedulerProvider.ui())
      .subscribeBy(
        onNext = { newsItems ->
          if (newsItems.isEmpty())
            newsIndexView.showNoFollowedItems()
          else
            newsIndexView.showItemsInRecyclerView(newsItems)
        }))
  }

  private fun clearObservers() {
    compositeDisposable.clear()
  }
}

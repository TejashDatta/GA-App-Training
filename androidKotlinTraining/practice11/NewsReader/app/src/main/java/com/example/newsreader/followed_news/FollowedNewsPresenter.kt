package com.example.newsreader.followed_news

import com.example.newsreader.BaseSchedulerProvider
import com.example.newsreader.data.models.NewsItem
import com.example.newsreader.data.source.FollowedNewsManager
import com.example.newsreader.news_index.OptionsModalBottomSheet
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.subscribeBy

class FollowedNewsPresenter(
  private val newsIndexView: FollowedNewsContract.View,
  private val followedNewsManager: FollowedNewsManager,
  private val schedulerProvider: BaseSchedulerProvider
): FollowedNewsContract.Presenter {

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

  override fun onClickNewsItem(newsItem: NewsItem) {
    newsIndexView.openInCustomTab(newsItem.link)
  }

  override fun onClickNewsItemOptionsMenu(newsItem: NewsItem) {
    newsIndexView.openOptionsMenu(newsItem, followedNewsManager.isSaved(newsItem))
  }

  override fun onClickNewsItemOption(newsItem: NewsItem, option: OptionsModalBottomSheet.Option) {
    when(option) {
      OptionsModalBottomSheet.Option.SAVE -> {
        if(followedNewsManager.isSaved(newsItem)) {
          followedNewsManager.remove(newsItem)
        } else {
          followedNewsManager.add(newsItem)
        }
        newsIndexView.showToastForSaveClicked(followedNewsManager.isSaved(newsItem))
      }

      OptionsModalBottomSheet.Option.SHARE -> newsIndexView.shareNews(newsItem)
    }
  }

  private fun clearObservers() {
    compositeDisposable.clear()
  }
}

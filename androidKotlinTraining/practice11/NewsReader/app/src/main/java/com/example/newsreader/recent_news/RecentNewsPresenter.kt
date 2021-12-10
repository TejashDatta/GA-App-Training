package com.example.newsreader.recent_news

import com.example.newsreader.data.models.NewsItem
import com.example.newsreader.data.source.RecentNewsManager

class RecentNewsPresenter(
  private val recentNewsView: RecentNewsContract.View,
  private val recentNewsManager: RecentNewsManager
): RecentNewsContract.Presenter {
  init {
    recentNewsView.presenter = this
  }

  override fun start() {
    setupView()
  }

  private fun setupView() {
    if (recentNewsManager.items.isEmpty()) {
      recentNewsView.showNoRecentItems()
    } else {
      recentNewsView.showItemsInRecyclerView(recentNewsManager.items.reversed())
    }
  }

  override fun onClickNewsItem(newsItem: NewsItem) {
    recentNewsView.openInCustomTab(newsItem.link)
  }
}

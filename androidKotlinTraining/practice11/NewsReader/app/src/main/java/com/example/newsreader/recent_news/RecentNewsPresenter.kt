package com.example.newsreader.recent_news

import com.example.newsreader.data.models.NewsItem
import com.example.newsreader.data.source.NewsItemsRepository

class RecentNewsPresenter(
  private val recentNewsView: RecentNewsContract.View,
  private val newsItemsRepository: NewsItemsRepository
): RecentNewsContract.Presenter {
  init {
    recentNewsView.presenter = this
  }

  override fun start() {
    setupView()
  }

  private fun setupView() {
    if (newsItemsRepository.recentNewsItems.isEmpty()) {
      recentNewsView.showNoRecentItems()
    } else {
      recentNewsView.showItemsInRecyclerView(newsItemsRepository.recentNewsItems)
    }
  }

  override fun onClickNewsItem(newsItem: NewsItem) {
    recentNewsView.openInCustomTab(newsItem.link)
  }
}

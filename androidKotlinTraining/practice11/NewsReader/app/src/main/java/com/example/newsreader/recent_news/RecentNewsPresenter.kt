package com.example.newsreader.recent_news

import com.example.newsreader.data.models.NewsItem
import com.example.newsreader.data.source.NewsRepository

class RecentNewsPresenter(
  private val recentNewsView: RecentNewsContract.View,
  private val newsRepository: NewsRepository
): RecentNewsContract.Presenter {
  init {
    recentNewsView.presenter = this
  }

  override fun start() {
    setupView()
  }

  private fun setupView() {
    if (newsRepository.recentNewsItems.isEmpty()) {
      recentNewsView.showNoRecentItems()
    } else {
      recentNewsView.showItemsInRecyclerView(newsRepository.recentNewsItems)
    }
  }

  override fun onClickNewsItem(newsItem: NewsItem) {
    recentNewsView.openInCustomTab(newsItem.link)
  }
}

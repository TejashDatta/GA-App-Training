package com.example.newsreader.recent_news

import com.example.newsreader.BasePresenter
import com.example.newsreader.BaseView
import com.example.newsreader.data.models.NewsItem

class RecentNewsContract {
  interface View: BaseView<Presenter> {
    fun openInCustomTab(url: String)
    fun showItemsInRecyclerView(newsItems: List<NewsItem>)
    fun showNoRecentItems()
  }

  interface Presenter: BasePresenter {
    fun onClickRecentNewsItem(newsItem: NewsItem)
  }
}

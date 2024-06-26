package com.example.newsreader.news_index

import com.example.newsreader.BasePresenter
import com.example.newsreader.BaseView
import com.example.newsreader.data.models.NewsItem

class NewsIndexContract {
  interface View: BaseView<Presenter> {
    fun showItemsInRecyclerView(newsItems: List<NewsItem>)
    fun openInTab(url: String)
    fun showLoading()
    fun showError()
    fun showNoResults()
  }

  interface Presenter: BasePresenter {
    fun refreshNewsItems()
    fun onClickNewsItem(newsItem: NewsItem)
  }
}

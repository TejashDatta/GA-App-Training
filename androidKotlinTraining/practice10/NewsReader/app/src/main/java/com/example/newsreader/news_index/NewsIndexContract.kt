package com.example.newsreader.news_index

import com.example.newsreader.BasePresenter
import com.example.newsreader.BaseView
import com.example.newsreader.data.models.NewsItem

class NewsIndexContract {
  interface View: BaseView<Presenter> {
    fun setRecyclerViewItems(newsItems: List<NewsItem>)
    fun refreshComplete()
  }

  interface Presenter: BasePresenter {
    fun refreshNewsItems()
  }
}

package com.example.newsreader.news_reader

import com.example.newsreader.BasePresenter
import com.example.newsreader.BaseView
import com.example.newsreader.data.models.NewsSource

class NewsReaderContract {
  interface View: BaseView<Presenter> {
    fun showNews(newsSourceName: String)
    fun showFollowedNews()
    fun showRecentNews()
    fun showAddNewsSource()
    fun setupDrawerMainContent(newsSources: List<NewsSource>)
  }

  interface Presenter: BasePresenter {
    fun end()
    fun onClickNewsSource(newsSourceName: String)
    fun onClickFollowedNews()
    fun onClickRecentNews()
    fun onClickAddNewsSource()
  }
}

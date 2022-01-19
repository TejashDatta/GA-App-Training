package com.example.newsreader.news_reader

import com.example.newsreader.BasePresenter
import com.example.newsreader.BaseView
import com.example.newsreader.data.models.NewsSource

class NewsReaderContract {
  interface View: BaseView<Presenter> {
    fun showAllNews()
    fun showGoogleNews()
    fun showToyokeizaiNews()
    fun showFollowedNews()
    fun showRecentNews()
    fun showGeneralNews(newsSource: NewsSource)
    fun showAddNewsSource()
    fun setupDrawerMainContent(newsSources: List<NewsSource>)
  }

  interface Presenter: BasePresenter {
    fun end()
    fun onClickAllNews()
    fun onClickGoogleNews()
    fun onClickToyokeizaiNews()
    fun onClickFollowedNews()
    fun onClickRecentNews()
    fun onClickGeneralNews(newsSourceName: String)
    fun onClickAddNewsSource()
  }
}

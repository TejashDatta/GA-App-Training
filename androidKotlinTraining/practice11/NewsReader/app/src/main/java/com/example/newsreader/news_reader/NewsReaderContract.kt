package com.example.newsreader.news_reader

import com.example.newsreader.BasePresenter
import com.example.newsreader.BaseView

class NewsReaderContract {
  interface View: BaseView<Presenter> {
    fun showAllNews()
    fun showGoogleNews()
    fun showToyokeizaiNews()
    fun showFollowedNews()
    fun showRecentNews()
  }

  interface Presenter: BasePresenter {
    fun onClickAllNews()
    fun onClickGoogleNews()
    fun onClickToyokeizaiNews()
    fun onClickFollowedNews()
    fun onClickRecentNews()
  }
}

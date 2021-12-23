package com.example.newsreader.add_news_source

import com.example.newsreader.BasePresenter
import com.example.newsreader.BaseView
import com.example.newsreader.data.models.NewsSource

class AddNewsSourceContract {
  interface View: BaseView<Presenter> {
    fun setNameError(error: String)
    fun disableNameError()
    fun setLinkError(error: String)
    fun disableLinkError()
  }

  interface Presenter: BasePresenter {
    fun validateName(name: String)
    fun validateLink(link: String)
    fun addNewsSource(newsSource: NewsSource)
  }
}

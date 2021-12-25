package com.example.newsreader.add_news_source

import com.example.newsreader.BasePresenter
import com.example.newsreader.BaseView

class AddNewsSourceContract {
  interface View: BaseView<Presenter> {
    fun setNameError(error: String)
    fun disableNameError()
    fun setUrlError(error: String)
    fun disableUrlError()
    fun goBack()
  }

  interface Presenter: BasePresenter {
    fun validateName(name: String)
    fun validateUrl(url: String)
    fun onSaveClick(name: String, url: String)
  }
}

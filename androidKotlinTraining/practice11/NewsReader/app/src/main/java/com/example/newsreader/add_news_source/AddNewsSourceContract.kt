package com.example.newsreader.add_news_source

import com.example.newsreader.BasePresenter
import com.example.newsreader.BaseView

class AddNewsSourceContract {
  interface View: BaseView<Presenter> {
    fun setNameIsRequiredError()
    fun setNameIsTooLongError(maxLength: Int)
    fun disableNameError()
    fun setUrlIsRequiredError()
    fun setUrlIsTooLongError(maxLength: Int)
    fun setUrlFormatError()
    fun disableUrlError()
    fun goBack()
  }

  interface Presenter: BasePresenter {
    fun onNameInput(name: String)
    fun onUrlInput(url: String)
    fun onSaveClick(name: String, url: String)
  }
}

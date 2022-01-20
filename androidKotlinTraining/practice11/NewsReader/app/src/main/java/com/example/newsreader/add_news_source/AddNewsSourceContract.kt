package com.example.newsreader.add_news_source

import com.example.newsreader.BasePresenter
import com.example.newsreader.BaseView
import io.reactivex.rxjava3.core.Observable

class AddNewsSourceContract {
  interface View: BaseView<Presenter> {
    fun setNameIsRequiredError()
    fun setNameIsTooLongError(maxLength: Int)
    fun disableNameError()
    fun setUrlIsRequiredError()
    fun setUrlIsTooLongError(maxLength: Int)
    fun setUrlFormatError()
    fun disableUrlError()
    fun showNewsSourceAddedToast(newsSourceName: String)
    fun goBack()
  }

  interface Presenter: BasePresenter {
    val isFormValid: Observable<Boolean>
    fun end()
    fun onNameInput(name: String)
    fun onUrlInput(url: String)
    fun onSaveClick(name: String, url: String)
  }
}

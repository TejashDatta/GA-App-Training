package com.example.newsreader.add_news_source

import com.example.newsreader.data.models.NewsSource
import com.example.newsreader.data.source.NewsItemsRepository
import com.example.newsreader.data.validators.NewsSourceValidator
import io.reactivex.rxjava3.core.Observable

class AddNewsSourcePresenter(
  private val view: AddNewsSourceContract.View,
  private val newsSourceValidator: NewsSourceValidator,
  private val newsItemsRepository: NewsItemsRepository
): AddNewsSourceContract.Presenter {
  init {
    view.presenter = this
  }

  override val isFormValid: Observable<Boolean>
    get() = newsSourceValidator.isFormValid

  override fun start() {
  }

  override fun onNameInput(name: String) {
    when (newsSourceValidator.validateName(name)) {
      NewsSourceValidator.NameError.REQUIRED -> view.setNameIsRequiredError()
      NewsSourceValidator.NameError.NONE -> view.disableNameError()
    }
  }

  override fun onUrlInput(url: String) {
    when (newsSourceValidator.validateUrl(url)) {
      NewsSourceValidator.UrlError.REQUIRED -> view.setUrlIsRequiredError()
      NewsSourceValidator.UrlError.INCORRECT_FORMAT -> view.setUrlFormatError()
      NewsSourceValidator.UrlError.NONE -> view.disableUrlError()
    }
  }

  override fun onSaveClick(name: String, url: String) {
    newsItemsRepository.addNewsSource(NewsSource(name, url))
  }
}

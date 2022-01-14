package com.example.newsreader.add_news_source

import com.example.newsreader.data.models.NewsSource
import com.example.newsreader.data.source.NewsRepository
import com.example.newsreader.data.validators.NewsSourceValidator
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable

class AddNewsSourcePresenter(
  private val view: AddNewsSourceContract.View,
  private val newsSourceValidator: NewsSourceValidator,
  private val newsRepository: NewsRepository
): AddNewsSourceContract.Presenter {
  init {
    view.presenter = this
  }

  private var compositeDisposable = CompositeDisposable()

  override val isFormValid: Observable<Boolean>
    get() = newsSourceValidator.isFormValid

  override fun start() {
    subscribeToNameError()
    subscribeToUrlError()
  }

  override fun end() {
    clearObservers()
  }

  override fun onNameInput(name: String) {
    newsSourceValidator.name = name
  }

  override fun onUrlInput(url: String) {
    newsSourceValidator.url = url
  }

  override fun onSaveClick(name: String, url: String) {
    newsRepository.addNewsSource(NewsSource(name, url))
  }

  private fun subscribeToNameError() {
    compositeDisposable.add(newsSourceValidator.nameError
      .subscribe {
        when (it) {
          NewsSourceValidator.NameError.REQUIRED -> view.setNameIsRequiredError()
          NewsSourceValidator.NameError.NONE -> view.disableNameError()
        }
      }
    )
  }

  private fun subscribeToUrlError() {
    compositeDisposable.add(newsSourceValidator.urlError
      .subscribe {
        when (it) {
          NewsSourceValidator.UrlError.REQUIRED -> view.setUrlIsRequiredError()
          NewsSourceValidator.UrlError.INCORRECT_FORMAT -> view.setUrlFormatError()
          NewsSourceValidator.UrlError.NONE -> view.disableUrlError()
        }
      }
    )
  }

  private fun clearObservers() {
    compositeDisposable.clear()
  }
}

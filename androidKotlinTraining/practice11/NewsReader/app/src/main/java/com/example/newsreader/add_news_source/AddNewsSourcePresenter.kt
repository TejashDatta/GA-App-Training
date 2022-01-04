package com.example.newsreader.add_news_source

import com.example.newsreader.data.models.NewsSource
import com.example.newsreader.data.source.NewsItemsRepository
import io.reactivex.rxjava3.subjects.BehaviorSubject

class AddNewsSourcePresenter(
  private val view: AddNewsSourceContract.View,
  private val newsItemsRepository: NewsItemsRepository
): AddNewsSourceContract.Presenter {
  companion object {
    const val NAME_MAX_LENGTH = 30
    const val URL_MAX_LENGTH  = 200
  }

  override val isFormValid: BehaviorSubject<Boolean> = BehaviorSubject.create()

  private var isNameValid = false
  private var isUrlValid = false

  private val urlRegex =
    Regex("https?://(www\\.)?[-a-zA-Z0-9@:%._+~#=]{2,256}\\.[a-z]{2,4}\\b([-a-zA-Z0-9@:%_+.~#?&/=]*)")

  init {
    view.presenter = this
  }

  override fun start() {
    updateFormValidity()
  }

  override fun validateName(name: String) {
    isNameValid = false
    when {
      name.isEmpty() -> view.setNameIsRequiredError()
      name.length > NAME_MAX_LENGTH -> view.setNameIsTooLongError(NAME_MAX_LENGTH)
      else -> {
        view.disableNameError()
        isNameValid = true
      }
    }
    updateFormValidity()
  }

  override fun validateUrl(url: String) {
    isUrlValid = false
    when {
      url.isEmpty() -> view.setUrlIsRequiredError()
      url.length > URL_MAX_LENGTH -> view.setUrlIsTooLongError(URL_MAX_LENGTH)
      !url.matches(urlRegex) -> view.setUrlFormatError()
      else -> {
        view.disableUrlError()
        isUrlValid = true
      }
    }
    updateFormValidity()
  }

  override fun onSaveClick(name: String, url: String) {
    newsItemsRepository.addNewsSource(NewsSource(name, url))
  }

  private fun updateFormValidity() {
    isFormValid.onNext(isNameValid && isUrlValid)
  }
}

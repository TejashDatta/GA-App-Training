package com.example.newsreader.add_news_source

import com.example.newsreader.data.source.NewsItemsRepository

class AddNewsSourcePresenter(
  private val view: AddNewsSourceContract.View,
  private val newsItemsRepository: NewsItemsRepository
): AddNewsSourceContract.Presenter {
  companion object {
    const val NAME_MAX_LENGTH = 30
    const val URL_MAX_LENGTH  = 200
  }

//  TODO: check regex pattern
  private val urlRegex = Regex("https?:\\/\\/(www\\.)?[-a-zA-Z0-9@:%._\\+~#=]{2,256}\\.[a-z]{2,4}\\b([-a-zA-Z0-9@:%_\\+.~#?&//=]*)")

  init {
    view.presenter = this
  }

  override fun start() {
    TODO("Not yet implemented")
  }

  override fun validateName(name: String) {
    when {
      name.isEmpty() -> view.setNameIsRequiredError()
      name.length > NAME_MAX_LENGTH -> view.setNameIsTooLongError(NAME_MAX_LENGTH)
      else -> view.disableNameError()
    }
  }

  override fun validateUrl(url: String) {
    when {
      url.isEmpty() -> view.setUrlIsRequiredError()
      url.length > URL_MAX_LENGTH -> view.setUrlIsTooLongError(URL_MAX_LENGTH)
      !url.matches(urlRegex) -> view.setUrlFormatError()
      else -> view.disableUrlError()
    }
  }

  override fun onSaveClick(name: String, url: String) {
    TODO("Not yet implemented")
  }
}

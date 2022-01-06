package com.example.newsreader.data.validators

import io.reactivex.rxjava3.subjects.BehaviorSubject

class NewsSourceValidator(private val urlRegexMatcher: UrlRegexMatcher) {
  companion object {
    const val NAME_MAX_LENGTH = 30
    const val URL_MAX_LENGTH  = 200
  }

  enum class NameError { NONE, REQUIRED }
  enum class UrlError { NONE, REQUIRED, INCORRECT_FORMAT }

  val isFormValid: BehaviorSubject<Boolean> = BehaviorSubject.create()

  private var isNameValid = false
  private var isUrlValid = false

  init {
    updateFormValidity()
  }

  fun validateName(name: String): NameError {
    val validationResult = when {
      name.isEmpty() -> NameError.REQUIRED
      else -> NameError.NONE
    }

    isNameValid = validationResult == NameError.NONE
    updateFormValidity()
    return validationResult
  }

  fun validateUrl(url: String): UrlError {
    val validationResult = when {
      url.isEmpty() -> UrlError.REQUIRED
      !urlRegexMatcher.matches(url) -> UrlError.INCORRECT_FORMAT
      else -> UrlError.NONE
    }

    isUrlValid = validationResult == UrlError.NONE
    updateFormValidity()
    return validationResult
  }

  private fun updateFormValidity() {
    isFormValid.onNext(isNameValid && isUrlValid)
  }
}

interface UrlRegexMatcher {
  fun matches(url: String): Boolean
}

package com.example.newsreader.data.validators

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.BehaviorSubject

class NewsSourceValidator(private val urlRegexMatcher: UrlRegexMatcher) {
  companion object {
    const val NAME_MAX_LENGTH = 30
    const val URL_MAX_LENGTH  = 200
  }

  enum class NameError { NONE, REQUIRED }
  enum class UrlError { NONE, REQUIRED, INCORRECT_FORMAT }

  var name: String = ""
    set(value) {
      validateName(value)
      field = value
    }

  var url: String = ""
    set(value) {
      validateUrl(value)
      field = value
    }

  val nameError: BehaviorSubject<NameError> = BehaviorSubject.create()
  val urlError: BehaviorSubject<UrlError> = BehaviorSubject.create()

  val isFormValid: Observable<Boolean> = Observable.zip(
    nameError,
    urlError,
    { nameError, urlError -> nameError == NameError.NONE && urlError == UrlError.NONE }
  )

  private fun validateName(name: String) {
    val validationResult = when {
      name.isEmpty() -> NameError.REQUIRED
      else -> NameError.NONE
    }
    nameError.onNext(validationResult)
  }

  private fun validateUrl(url: String) {
    val validationResult = when {
      url.isEmpty() -> UrlError.REQUIRED
      !urlRegexMatcher.matches(url) -> UrlError.INCORRECT_FORMAT
      else -> UrlError.NONE
    }
    urlError.onNext(validationResult)
  }
}

interface UrlRegexMatcher {
  fun matches(url: String): Boolean
}

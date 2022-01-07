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

  val isNameValid: BehaviorSubject<Boolean> = BehaviorSubject.create()
  val isUrlValid: BehaviorSubject<Boolean> = BehaviorSubject.create()

  val isFormValid: Observable<Boolean> = Observable.zip(
    isNameValid,
    isUrlValid,
    { isNameValid, isUrlValid -> isNameValid && isUrlValid }
  )

  init {
    isNameValid.onNext(false)
    isUrlValid.onNext(false)
  }

  fun validateName(name: String): NameError {
    val validationResult = when {
      name.isEmpty() -> NameError.REQUIRED
      else -> NameError.NONE
    }

    isNameValid.onNext(validationResult == NameError.NONE)
    return validationResult
  }

  fun validateUrl(url: String): UrlError {
    val validationResult = when {
      url.isEmpty() -> UrlError.REQUIRED
      !urlRegexMatcher.matches(url) -> UrlError.INCORRECT_FORMAT
      else -> UrlError.NONE
    }

    isUrlValid.onNext(validationResult == UrlError.NONE)
    return validationResult
  }
}

interface UrlRegexMatcher {
  fun matches(url: String): Boolean
}

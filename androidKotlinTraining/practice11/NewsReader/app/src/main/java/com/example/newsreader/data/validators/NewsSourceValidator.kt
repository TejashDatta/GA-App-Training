package com.example.newsreader.data.validators

class NewsSourceValidator(private val urlRegexMatcher: UrlRegexMatcher) {
  companion object {
    const val NAME_MAX_LENGTH = 30
    const val URL_MAX_LENGTH  = 200
  }

  enum class NameError { NONE, REQUIRED }
  enum class UrlError { NONE, REQUIRED, INCORRECT_FORMAT }

  fun validateName(name: String): NameError {
    return when {
      name.isEmpty() -> NameError.REQUIRED
      else -> NameError.NONE
    }
  }

  fun validateUrl(url: String): UrlError {
    return when {
      url.isEmpty() -> UrlError.REQUIRED
      !urlRegexMatcher.matches(url) -> UrlError.INCORRECT_FORMAT
      else -> UrlError.NONE
    }
  }
}

interface UrlRegexMatcher {
  fun matches(url: String): Boolean
}

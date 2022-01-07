package com.example.newsreader.data.validators

import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class NewsSourceValidatorTest {
  @Mock private lateinit var urlRegexMatcher: UrlRegexMatcher

  private lateinit var newsSourceValidator: NewsSourceValidator

  @Before fun setupNewsSourceValidator() {
    newsSourceValidator = NewsSourceValidator(urlRegexMatcher)
  }

  @Test fun validateName_requiredErrorWhenNameIsEmpty() {
    val name = ""
    assertEquals(newsSourceValidator.validateName(name), NewsSourceValidator.NameError.REQUIRED)
  }

  @Test fun validateName_noneErrorWhenNameIsValid() {
    val name = "abc"
    assertEquals(newsSourceValidator.validateName(name), NewsSourceValidator.NameError.NONE)
  }

  @Test fun validateUrl_requiredErrorWhenUrlIsEmpty() {
    val url = ""
    assertEquals(newsSourceValidator.validateUrl(url), NewsSourceValidator.UrlError.REQUIRED)
  }

  @Test fun validateUrl_incorrectFormatErrorWhenUrlIsNotUrlFormat() {
    val url = "not a url"
    `when`(urlRegexMatcher.matches(url)).thenReturn(false)
    assertEquals(newsSourceValidator.validateUrl(url), NewsSourceValidator.UrlError.INCORRECT_FORMAT)
  }

  @Test fun validateUrl_noneErrorWhenUrlIsValid() {
    val url = "https://www.gizmodo.jp/index.xml/"
    `when`(urlRegexMatcher.matches(url)).thenReturn(true)
    assertEquals(newsSourceValidator.validateUrl(url), NewsSourceValidator.UrlError.NONE)
  }
}

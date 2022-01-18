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

  @Test fun nameSetterValidatesName_requiredErrorWhenNameIsEmpty() {
    newsSourceValidator.name = ""
    assertEquals(newsSourceValidator.nameError.value, NewsSourceValidator.NameError.REQUIRED)
  }

  @Test fun nameSetterValidatesName_noneErrorWhenNameIsValid() {
    newsSourceValidator.name = "abc"
    assertEquals(newsSourceValidator.nameError.value, NewsSourceValidator.NameError.NONE)
  }

  @Test fun urlSetterValidatesUrl_requiredErrorWhenUrlIsEmpty() {
    newsSourceValidator.url = ""
    assertEquals(newsSourceValidator.urlError.value, NewsSourceValidator.UrlError.REQUIRED)
  }

  @Test fun urlSetterValidatesUrl_incorrectFormatErrorWhenUrlIsNotUrlFormat() {
    val url = "not a url"
    `when`(urlRegexMatcher.matches(url)).thenReturn(false)
    newsSourceValidator.url = url
    assertEquals(newsSourceValidator.urlError.value, NewsSourceValidator.UrlError.INCORRECT_FORMAT)
  }

  @Test fun urlSetterValidatesUrl_noneErrorWhenUrlIsValid() {
    val url = "https://www.gizmodo.jp/index.xml/"
    `when`(urlRegexMatcher.matches(url)).thenReturn(true)
    newsSourceValidator.url = url
    assertEquals(newsSourceValidator.urlError.value, NewsSourceValidator.UrlError.NONE)
  }

  @Test fun isFormValid_isTrueWhenAllFieldsAreValid() {
    val url = "https://www.example.com"
    `when`(urlRegexMatcher.matches(url)).thenReturn(true)
    newsSourceValidator.name = "example"
    newsSourceValidator.url = url
    assertEquals(true, newsSourceValidator.isFormValid.blockingFirst())
  }

  @Test fun isFormValid_isFalseWhenAFieldIsInvalid() {
    val url = "https://www.example.com"
    `when`(urlRegexMatcher.matches(url)).thenReturn(true)
    newsSourceValidator.name = ""
    newsSourceValidator.url = url
    assertEquals(false, newsSourceValidator.isFormValid.blockingFirst())
  }
}

package com.example.newsreader

import com.example.newsreader.add_news_source.AddNewsSourceContract
import com.example.newsreader.add_news_source.AddNewsSourcePresenter
import com.example.newsreader.data.source.NewsItemsRepository
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class AddNewsSourcePresenterTest {
  @Mock private lateinit var view: AddNewsSourceContract.View

  @Mock private lateinit var newsItemsRepository: NewsItemsRepository

  private lateinit var addNewsSourcePresenter: AddNewsSourcePresenter

  @Before fun createPresenter() {
    addNewsSourcePresenter = AddNewsSourcePresenter(view, newsItemsRepository)
  }

  @Test fun createPresenter_setsPresenterToView() {
    verify(view).presenter = addNewsSourcePresenter
  }

  @Test fun validateName_errorWhenInputIsEmpty() {
    val name = ""
    addNewsSourcePresenter.validateName(name)
    verify(view).setNameIsRequiredError()
  }

  @Test fun validateName_errorWhenInputIsLongerThanMaxLength() {
    val name = "a".repeat(AddNewsSourcePresenter.Companion.NAME_MAX_LENGTH + 1)
    addNewsSourcePresenter.validateName(name)
    verify(view).setNameIsTooLongError(AddNewsSourcePresenter.Companion.NAME_MAX_LENGTH)
  }

  @Test fun validateName_disablesErrorForValidInput() {
    val name = "abc"
    addNewsSourcePresenter.validateName(name)
    verify(view).disableNameError()
  }

  @Test fun validateUrl_errorWhenInputIsEmpty() {
    val url = ""
    addNewsSourcePresenter.validateUrl(url)
    verify(view).setUrlIsRequiredError()
  }

  @Test fun validateUrl_errorWhenInputIsLongerThanMaxLength() {
    val url = "https://www.gizmodo.jp/index.xml/" +
        "a".repeat(AddNewsSourcePresenter.Companion.URL_MAX_LENGTH + 1)
    addNewsSourcePresenter.validateUrl(url)
    verify(view).setUrlIsTooLongError(AddNewsSourcePresenter.Companion.URL_MAX_LENGTH)
  }

  @Test fun validateUrl_errorWhenInputIsNotUrlFormat() {
    val url = "not a url"
    addNewsSourcePresenter.validateUrl(url)
    verify(view).setUrlFormatError()
  }

  @Test fun validateUrl_disablesErrorForValidInput() {
    val url = "https://www.gizmodo.jp/index.xml/"
    addNewsSourcePresenter.validateUrl(url)
    verify(view).disableUrlError()
  }
}

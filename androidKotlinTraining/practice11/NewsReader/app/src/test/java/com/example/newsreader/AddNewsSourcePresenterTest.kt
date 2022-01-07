package com.example.newsreader

import com.example.newsreader.add_news_source.AddNewsSourceContract
import com.example.newsreader.add_news_source.AddNewsSourcePresenter
import com.example.newsreader.data.source.NewsItemsRepository
import com.example.newsreader.data.validators.NewsSourceValidator
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class AddNewsSourcePresenterTest {
  @Mock private lateinit var view: AddNewsSourceContract.View

  @Mock private lateinit var newsSourceValidator: NewsSourceValidator

  @Mock private lateinit var newsItemsRepository: NewsItemsRepository

  private lateinit var addNewsSourcePresenter: AddNewsSourcePresenter

  @Before fun createPresenter() {
    addNewsSourcePresenter = AddNewsSourcePresenter(view, newsSourceValidator, newsItemsRepository)
  }

  @Test fun createPresenter_setsPresenterToView() {
    verify(view).presenter = addNewsSourcePresenter
  }

  @Test fun onNameInput_setsErrorDisplayForRequiredError() {
    val name = ""
    `when`(newsSourceValidator.validateName(name)).thenReturn(NewsSourceValidator.NameError.REQUIRED)
    addNewsSourcePresenter.onNameInput(name)
    verify(view).setNameIsRequiredError()
  }

  @Test fun onNameInput_disablesErrorDisplayForNoneError() {
    val name = "abc"
    `when`(newsSourceValidator.validateName(name)).thenReturn(NewsSourceValidator.NameError.NONE)
    addNewsSourcePresenter.onNameInput(name)
    verify(view).disableNameError()
  }

  @Test fun onUrlInput_setsErrorDisplayForRequiredError() {
    val url = ""
    `when`(newsSourceValidator.validateUrl(url)).thenReturn(NewsSourceValidator.UrlError.REQUIRED)
    addNewsSourcePresenter.onUrlInput(url)
    verify(view).setUrlIsRequiredError()
  }

  @Test fun onUrlInput_setsErrorDisplayForIncorrectFormatError() {
    val url = "not a url"
    `when`(newsSourceValidator.validateUrl(url)).thenReturn(NewsSourceValidator.UrlError.INCORRECT_FORMAT)
    addNewsSourcePresenter.onUrlInput(url)
    verify(view).setUrlFormatError()
  }

  @Test fun onUrlInput_disablesErrorDisplayForNoneError() {
    val url = "https://www.gizmodo.jp/index.xml/"
    `when`(newsSourceValidator.validateUrl(url)).thenReturn(NewsSourceValidator.UrlError.NONE)
    addNewsSourcePresenter.onUrlInput(url)
    verify(view).disableUrlError()
  }
}

package com.example.newsreader

import com.example.newsreader.add_news_source.AddNewsSourceContract
import com.example.newsreader.add_news_source.AddNewsSourcePresenter
import com.example.newsreader.data.models.NewsSource
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

  @Test fun onNameInput_errorWhenInputIsEmpty() {
    val name = ""
    addNewsSourcePresenter.onNameInput(name)
    verify(view).setNameIsRequiredError()
  }

  @Test fun onNameInput_errorWhenInputIsLongerThanMaxLength() {
    val name = "a".repeat(AddNewsSourcePresenter.Companion.NAME_MAX_LENGTH + 1)
    addNewsSourcePresenter.onNameInput(name)
    verify(view).setNameIsTooLongError(AddNewsSourcePresenter.Companion.NAME_MAX_LENGTH)
  }

  @Test fun onNameInput_disablesErrorForValidInput() {
    val name = "abc"
    addNewsSourcePresenter.onNameInput(name)
    verify(view).disableNameError()
  }

  @Test fun onUrlInput_errorWhenInputIsEmpty() {
    val url = ""
    addNewsSourcePresenter.onUrlInput(url)
    verify(view).setUrlIsRequiredError()
  }

  @Test fun onUrlInput_errorWhenInputIsLongerThanMaxLength() {
    val url = "https://www.gizmodo.jp/index.xml/" +
        "a".repeat(AddNewsSourcePresenter.Companion.URL_MAX_LENGTH + 1)
    addNewsSourcePresenter.onUrlInput(url)
    verify(view).setUrlIsTooLongError(AddNewsSourcePresenter.Companion.URL_MAX_LENGTH)
  }

  @Test fun onUrlInput_errorWhenInputIsNotUrlFormat() {
    val url = "not a url"
    addNewsSourcePresenter.onUrlInput(url)
    verify(view).setUrlFormatError()
  }

  @Test fun onUrlInput_disablesErrorForValidInput() {
    val url = "https://www.gizmodo.jp/index.xml/"
    addNewsSourcePresenter.onUrlInput(url)
    verify(view).disableUrlError()
  }

  @Test fun onSaveClick_addsNewsSourceInNewsRepository() {
    val name = "example"
    val url = "https://www.example.com"
    addNewsSourcePresenter.onSaveClick(name, url)
    verify(newsItemsRepository).addNewsSource(NewsSource(name, url))
  }
}

package com.example.newsreader

import com.example.newsreader.add_news_source.AddNewsSourceContract
import com.example.newsreader.add_news_source.AddNewsSourcePresenter
import com.example.newsreader.data.models.NewsSource
import com.example.newsreader.data.source.NewsRepository
import com.example.newsreader.data.validators.NewsSourceValidator
import com.example.newsreader.data.validators.UrlRegexMatcher
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class AddNewsSourcePresenterTest {
  @Mock private lateinit var view: AddNewsSourceContract.View

  @Mock private lateinit var newsRepository: NewsRepository

  @Mock private lateinit var urlRegexMatcher: UrlRegexMatcher

  private lateinit var addNewsSourcePresenter: AddNewsSourcePresenter

  private lateinit var newsSourceValidator: NewsSourceValidator

  val validName = "example"
  val validUrl = "https://www.example.com"

  @Before fun setupValidatorAndPresenter() {
    newsSourceValidator = NewsSourceValidator(urlRegexMatcher)
    addNewsSourcePresenter = AddNewsSourcePresenter(view, newsSourceValidator, newsRepository)
  }

  @Test fun createPresenter_setsPresenterToView() {
    verify(view).presenter = addNewsSourcePresenter
  }

  @Test fun onNameInput_setsNameInValidator() {
    addNewsSourcePresenter.onNameInput(validName)
    assertEquals(validName, newsSourceValidator.name)
  }

  @Test fun onUrlInput_setsUrlInValidator() {
    addNewsSourcePresenter.onUrlInput(validUrl)
    assertEquals(validUrl, newsSourceValidator.url)
  }

  @Test fun onSaveClick_addsNewsSourceInNewsRepository() {
    addNewsSourcePresenter.onSaveClick(validName, validUrl)
    verify(newsRepository).addNewsSource(NewsSource(validName, validUrl))
  }
  
//  TODO: test that presenter sets error display when validator emits error event
}

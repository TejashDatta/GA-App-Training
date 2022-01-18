package com.example.newsreader

import com.example.newsreader.news_reader.NewsReaderContract
import com.example.newsreader.news_reader.NewsReaderPresenter
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class NewsReaderPresenterTest {
  @Mock private lateinit var newsReaderView: NewsReaderContract.View

  private lateinit var newsReaderPresenter: NewsReaderPresenter

  @Before fun setupNewsIndexPresenter() {
    newsReaderPresenter = NewsReaderPresenter(newsReaderView)
  }

  @Test fun start_showsAllNews() {
    newsReaderPresenter.start()

    verify(newsReaderView).showAllNews()
  }

  @Test fun onClickAllNews_showsAllNews() {
    newsReaderPresenter.onClickAllNews()

    verify(newsReaderView).showAllNews()
  }

  @Test fun onClickGoogleNews_showsGoogleNews() {
    newsReaderPresenter.onClickGoogleNews()

    verify(newsReaderView).showGoogleNews()
  }

  @Test fun onClickToyokeizaiNews_showsToyokeizaiNews() {
    newsReaderPresenter.onClickToyokeizaiNews()

    verify(newsReaderView).showToyokeizaiNews()
  }

  @Test fun onClickFollowedNews_showsFollowedNews() {
    newsReaderPresenter.onClickFollowedNews()

    verify(newsReaderView).showFollowedNews()
  }

  @Test fun onClickRecentNews_showsRecentNews() {
    newsReaderPresenter.onClickRecentNews()

    verify(newsReaderView).showRecentNews()
  }
}

package com.example.newsreader

import com.example.newsreader.data.models.NewsSource
import com.example.newsreader.data.source.NewsRepository
import com.example.newsreader.news_reader.NewsReaderContract
import com.example.newsreader.news_reader.NewsReaderPresenter
import io.reactivex.rxjava3.subjects.BehaviorSubject
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class NewsReaderPresenterTest {
  @Mock private lateinit var newsReaderView: NewsReaderContract.View
  @Mock private lateinit var newsRepository: NewsRepository
  @Mock private lateinit var newsSourcesSubject: BehaviorSubject<List<NewsSource>>

  private lateinit var newsReaderPresenter: NewsReaderPresenter

  @Before fun setupNewsIndexPresenter() {
    newsReaderPresenter = NewsReaderPresenter(newsReaderView, newsRepository)
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

  @Test fun onClickGeneralNews_showsGeneralNews() {
    val newsSource = NewsSource("example", "https://www.example.com/index.xml")

    `when`(newsRepository.newsSourcesSubject).thenReturn(newsSourcesSubject)
    `when`(newsSourcesSubject.value).thenReturn(listOf(newsSource))

    newsReaderPresenter.onClickGeneralNews(newsSource.name)

    verify(newsReaderView).showGeneralNews(newsSource)
  }

  @Test fun onClickAddNewsSource_showsAddNewsSource() {
    newsReaderPresenter.onClickAddNewsSource()

    verify(newsReaderView).showAddNewsSource()
  }

//  TODO: test that news sources subscriber calls setup main drawer in view
}

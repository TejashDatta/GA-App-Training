package com.example.newsreader

import com.example.newsreader.data.models.NewsSource
import com.example.newsreader.data.source.NewsRepository
import com.example.newsreader.data.source.NewsSourcesManager
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
  private var newsSourcesSubject: BehaviorSubject<List<NewsSource>> = BehaviorSubject.create()

  private lateinit var newsReaderPresenter: NewsReaderPresenter

  @Before fun setupNewsIndexPresenter() {
    `when`(newsRepository.newsSourcesSubject).thenReturn(newsSourcesSubject)
    newsReaderPresenter = NewsReaderPresenter(newsReaderView, newsRepository)
  }

  @Test fun start_showsAllNews() {
    newsReaderPresenter.start()

    verify(newsReaderView).showNews(NewsSourcesManager.ALL_NEWS_NAME)
  }

  @Test fun onClickNewsSource_showsNews() {
    val newsSourceName = "example"

    newsReaderPresenter.onClickNewsSource(newsSourceName)

    verify(newsReaderView).showNews(newsSourceName)
  }

  @Test fun onClickFollowedNews_showsFollowedNews() {
    newsReaderPresenter.onClickFollowedNews()

    verify(newsReaderView).showFollowedNews()
  }

  @Test fun onClickRecentNews_showsRecentNews() {
    newsReaderPresenter.onClickRecentNews()

    verify(newsReaderView).showRecentNews()
  }

  @Test fun onClickAddNewsSource_showsAddNewsSource() {
    newsReaderPresenter.onClickAddNewsSource()

    verify(newsReaderView).showAddNewsSource()
  }

//  TODO: test that news sources subscriber calls setup main drawer in view
}

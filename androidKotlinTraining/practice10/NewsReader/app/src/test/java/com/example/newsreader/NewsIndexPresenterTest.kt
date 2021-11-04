package com.example.newsreader

import com.example.newsreader.data.NewsItemsRepository
import com.example.newsreader.data.models.NewsItem
import com.example.newsreader.news_index.NewsIndexContract
import com.example.newsreader.news_index.NewsIndexPresenter
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.TestScheduler
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class NewsIndexPresenterTest {
  @Mock private lateinit var newsIndexView: NewsIndexContract.View

  @Mock private lateinit var newsItemsRepository: NewsItemsRepository

  @Mock private lateinit var newsItem: NewsItem

  private lateinit var newsIndexPresenter: NewsIndexPresenter

  private val testScheduler = TestScheduler()

  @Before fun setupNewsIndexPresenter() {
    newsIndexPresenter =
      NewsIndexPresenter(newsIndexView, newsItemsRepository, TestSchedulerProvider(testScheduler))
  }

  @Test fun createPresenter_setsPresenterToView() {
    verify(newsIndexView).presenter = newsIndexPresenter
  }

  @Test fun onClickNewsItem_opensTabInView() {
    newsIndexPresenter.onClickNewsItem(newsItem)

    verify(newsIndexView).openInTab(newsItem.link)
  }

  @Test fun refreshNewsItems_showItemsInRecyclerViewWhenThereAreResults() {
    val resultsList = listOf(newsItem, newsItem)
    `when`(newsItemsRepository.getNewsItems()).thenReturn(Observable.just(resultsList))

    newsIndexPresenter.refreshNewsItems()
    testScheduler.triggerActions()

    verify(newsIndexView).showItemsInRecyclerView(resultsList)
  }

  @Test fun refreshNewsItems_showNoResultsMessageWhenThereAreNoResults() {
    `when`(newsItemsRepository.getNewsItems()).thenReturn(Observable.just(emptyList()))

    newsIndexPresenter.refreshNewsItems()
    testScheduler.triggerActions()

    verify(newsIndexView).showNoResults()
  }

  @Test fun refreshNewsItems_showErrorMessageWhenError () {
    `when`(newsItemsRepository.getNewsItems())
      .thenReturn(Observable.error(RuntimeException("example error")))

    newsIndexPresenter.refreshNewsItems()
    testScheduler.triggerActions()

    verify(newsIndexView).showError()
  }
}

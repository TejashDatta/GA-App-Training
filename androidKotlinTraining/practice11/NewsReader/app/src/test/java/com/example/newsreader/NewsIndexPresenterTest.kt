package com.example.newsreader

import com.example.newsreader.data.NewsItemsRepository
import com.example.newsreader.data.models.NewsItem
import com.example.newsreader.news_index.NewsIndexContract
import com.example.newsreader.news_index.NewsIndexPresenter
import io.reactivex.rxjava3.core.Observable
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class NewsIndexPresenterTest {
  @Mock private lateinit var newsIndexView: NewsIndexContract.View

  @Mock private lateinit var newsItemsRepository: NewsItemsRepository

  @Mock private lateinit var newsItem1: NewsItem
  @Mock private lateinit var newsItem2: NewsItem

  private lateinit var newsIndexPresenter: NewsIndexPresenter

  @Before fun setupNewsIndexPresenter() {
    newsIndexPresenter =
      NewsIndexPresenter(newsIndexView, newsItemsRepository, TestSchedulerProvider())
  }

  @Test fun createPresenter_setsPresenterToView() {
    verify(newsIndexView).presenter = newsIndexPresenter
  }

  @Test fun onClickNewsItem_opensTabInView() {
    newsIndexPresenter.onClickNewsItem(newsItem1)

    verify(newsIndexView).openInTab(newsItem1.link)
  }

  @Test fun onClickNewsItemOptions_opensOptionsMenu() {
    newsIndexPresenter.onClickNewsItemOptions(newsItem1)

    verify(newsIndexView).openOptionsMenu(newsItem1)
  }
  
  @Test fun start_showItemsInRecyclerViewWhenThereAreResults() {
    val resultsList = listOf(newsItem1, newsItem2)
    `when`(newsItemsRepository.getNewsItems()).thenReturn(Observable.just(resultsList))

    newsIndexPresenter.start()

    verify(newsItemsRepository).getNewsItems()
    verify(newsIndexView).showLoading()
    verify(newsIndexView).showItemsInRecyclerView(resultsList)
  }

  @Test fun refreshNewsItems_showItemsInRecyclerViewWhenThereAreResults() {
    val resultsList = listOf(newsItem1, newsItem2)
    `when`(newsItemsRepository.getNewsItems()).thenReturn(Observable.just(resultsList))

    newsIndexPresenter.refreshNewsItems()

    verify(newsItemsRepository).getNewsItems()
    verify(newsIndexView).showLoading()
    verify(newsIndexView).showItemsInRecyclerView(resultsList)
  }

  @Test fun refreshNewsItems_showNoResultsMessageWhenThereAreNoResults() {
    `when`(newsItemsRepository.getNewsItems()).thenReturn(Observable.just(emptyList()))

    newsIndexPresenter.refreshNewsItems()

    verify(newsItemsRepository).getNewsItems()
    verify(newsIndexView).showLoading()
    verify(newsIndexView).showNoResults()
  }

  @Test fun refreshNewsItems_showErrorMessageWhenError () {
    `when`(newsItemsRepository.getNewsItems())
      .thenReturn(Observable.error(RuntimeException("example error")))

    newsIndexPresenter.refreshNewsItems()

    verify(newsItemsRepository).getNewsItems()
    verify(newsIndexView).showLoading()
    verify(newsIndexView).showError()
  }
}

package com.example.newsreader

import com.example.newsreader.data.models.NewsItem
import com.example.newsreader.data.source.NewsItemsRepository
import com.example.newsreader.recent_news.RecentNewsContract
import com.example.newsreader.recent_news.RecentNewsPresenter
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class RecentNewsPresenterTest {
  @Mock private lateinit var recentNewsView: RecentNewsContract.View

  @Mock private lateinit var newsItemsRepository: NewsItemsRepository

  @Mock private lateinit var newsItem: NewsItem

  private lateinit var recentNewsPresenter: RecentNewsPresenter

  @Before fun setupRecentNewsPresenter() {
    recentNewsPresenter = RecentNewsPresenter(recentNewsView, newsItemsRepository)
  }

  @Test fun createPresenter_setsPresenterToView() {
    verify(recentNewsView).presenter = recentNewsPresenter
  }

  @Test fun start_showsItemsInRecyclerViewWhenThereAreRecentItems() {
    val newsItemList = listOf(newsItem)
    `when`(newsItemsRepository.recentNewsItems).thenReturn(newsItemList)
    recentNewsPresenter.start()

    verify(recentNewsView).showItemsInRecyclerView(newsItemList)
  }

  @Test fun start_showNoRecentItemsWhenThereAreNoRecentItems() {
    `when`(newsItemsRepository.recentNewsItems).thenReturn(emptyList())
    recentNewsPresenter.start()

    verify(recentNewsView).showNoRecentItems()
  }

  @Test fun onClickNewsItem_opensCustomTabInView() {
    recentNewsPresenter.onClickNewsItem(newsItem)

    verify(recentNewsView).openInCustomTab(newsItem.link)
  }
}

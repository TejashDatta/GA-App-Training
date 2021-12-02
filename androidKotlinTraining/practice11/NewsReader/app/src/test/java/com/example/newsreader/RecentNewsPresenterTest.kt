package com.example.newsreader

import com.example.newsreader.data.models.NewsItem
import com.example.newsreader.data.source.RecentNewsManager
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

  @Mock private lateinit var recentNewsManager: RecentNewsManager

  @Mock private lateinit var newsItem: NewsItem

  private lateinit var recentNewsPresenter: RecentNewsPresenter

  @Before fun setupRecentNewsPresenter() {
    recentNewsPresenter = RecentNewsPresenter(recentNewsView, recentNewsManager)
  }

  @Test fun createPresenter_setsPresenterToView() {
    verify(recentNewsView).presenter = recentNewsPresenter
  }

  @Test fun start_showsItemsInRecyclerViewWhenThereAreRecentItems() {
    val newsItemList = listOf(newsItem)
    `when`(recentNewsManager.items).thenReturn(newsItemList)
    recentNewsPresenter.start()

    verify(recentNewsView).showItemsInRecyclerView(newsItemList)
  }

  @Test fun start_showNoRecentItemsWhenThereAreNoRecentItems() {
    `when`(recentNewsManager.items).thenReturn(emptyList())
    recentNewsPresenter.start()

    verify(recentNewsView).showNoRecentItems()
  }

  @Test fun onClickRecentNewsItem_opensCustomTabInView() {
    recentNewsPresenter.onClickRecentNewsItem(newsItem)

    verify(recentNewsView).openInCustomTab(newsItem.link)
  }
}

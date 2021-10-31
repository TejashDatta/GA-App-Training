package com.example.newsreader

import com.example.newsreader.data.models.NewsItem
import com.example.newsreader.news_index.NewsIndexContract
import com.example.newsreader.news_index.NewsIndexPresenter
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class NewsIndexPresenterTest {
  @Mock private lateinit var newsIndexView: NewsIndexContract.View

  @Mock private lateinit var newsItem: NewsItem

  private lateinit var newsIndexPresenter: NewsIndexPresenter

  @Before fun setupNewsIndexPresenter() {
    newsIndexPresenter = NewsIndexPresenter(newsIndexView)
  }

  @Test fun createPresenter_setsPresenterToView() {
    verify(newsIndexView).presenter = newsIndexPresenter
  }


  @Test fun onClickNewsItem_opensTabInView() {
    newsIndexPresenter.onClickNewsItem(newsItem)

    verify(newsIndexView).openInTab(newsItem.link)
  }
}

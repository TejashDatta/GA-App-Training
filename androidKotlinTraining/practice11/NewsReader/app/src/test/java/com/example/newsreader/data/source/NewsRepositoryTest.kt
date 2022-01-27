package com.example.newsreader.data.source

import com.example.newsreader.data.models.NewsItem
import com.example.newsreader.data.models.NewsSource
import com.example.newsreader.network.NewsApi
import com.example.newsreader.network.NewsApiService
import com.example.newsreader.network.data_transfer_objects.news.NetworkNewsChannel
import com.example.newsreader.network.data_transfer_objects.news.NetworkNewsItem
import com.example.newsreader.network.data_transfer_objects.news.toDomainModel
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.BehaviorSubject
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner
import org.threeten.bp.ZonedDateTime

@RunWith(MockitoJUnitRunner::class)
class NewsRepositoryTest {
  @Mock private lateinit var newsApi: NewsApi
  @Mock private lateinit var newsRetrofitService: NewsApiService

  @Mock private lateinit var followedNewsManager: FollowedNewsManager
  @Mock private lateinit var recentNewsManager: RecentNewsManager
  @Mock private lateinit var newsSourcesManager: NewsSourcesManager

  @Mock private lateinit var newsItem: NewsItem

  private val newsSource1 = NewsSource("example1", "www.example1.com")
  private val newsChannel1 =
    NetworkNewsChannel(listOf(
      NetworkNewsItem("test1", "testUrl1", ZonedDateTime.now(), null),
      NetworkNewsItem("test2", "testUrl2", ZonedDateTime.now().minusHours(3), null)
    ))

  private val newsSource2 = NewsSource("example2", "www.example2.com")
  private val newsChannel2 =
    NetworkNewsChannel(listOf(
      NetworkNewsItem("test3", "testUrl3", ZonedDateTime.now().minusHours(2), "testSource")
    ))

  private lateinit var newsRepository: NewsRepository

  @Before fun setupMocksAndNewsItemsRepository() {
    `when`(newsApi.retrofitService).thenReturn(newsRetrofitService)
    `when`(newsRetrofitService.getNewsChannel(newsSource1.url)).thenReturn(Observable.just(newsChannel1))
    `when`(newsRetrofitService.getNewsChannel(newsSource2.url)).thenReturn(Observable.just(newsChannel2))

    val newsSourcesSubject: BehaviorSubject<List<NewsSource>> =
      BehaviorSubject.createDefault(listOf(newsSource1, newsSource2))
    `when`(newsSourcesManager.newsSourcesSubject).thenReturn(newsSourcesSubject)

    newsRepository = NewsRepository(
      newsApi,
      followedNewsManager,
      recentNewsManager,
      newsSourcesManager
    )
  }

  @Test fun getNews_returnsNewsItemsOfNewsSource() {
    val actual = newsRepository.getNews(newsSource1.name, refresh = true).blockingFirst()
    val expected = newsChannel1.toDomainModel(newsSource1.name)
    assertEquals(actual, expected)
  }

  @Test fun getNews_withAllNewsSource_returnsAllNewsItemsByDescendingOrderOfPublishedDate() {
    val actual =
      newsRepository.getNews(NewsSourcesManager.ALL_NEWS_NAME, refresh = true).blockingFirst()
    val expected = (newsChannel1.toDomainModel(newsSource1.name) +
                    newsChannel2.toDomainModel(newsSource2.name)
                    ).sortedByDescending { it.publishedDate }
    assertEquals(actual, expected)
  }

  @Test fun followedNewsItemsSubject_returnsItemsSubjectFromFollowedNewsManager() {
    assertEquals(followedNewsManager.itemsSubject, newsRepository.followedNewsItemsSubject)
  }

  @Test fun isNewsFollowed_callsIsSavedFromFollowedNewsManager() {
    newsRepository.isNewsFollowed(newsItem)
    verify(followedNewsManager).isSaved(newsItem)
  }

  @Test fun addFollowedNews_callsAddFromFollowedNewsManager() {
    newsRepository.addFollowedNews(newsItem)
    verify(followedNewsManager).add(newsItem)
  }

  @Test fun removeFollowedNews_callsRemoveFromFollowedNewsManager() {
    newsRepository.removeFollowedNews(newsItem)
    verify(followedNewsManager).remove(newsItem)
  }

  @Test fun recentNewsItems_returnsItemsFromRecentNewsManager() {
    assertEquals(recentNewsManager.items, newsRepository.recentNewsItems)
  }

  @Test fun addRecentNews_callsAddFromRecentNewsManager() {
    newsRepository.addRecentNews(newsItem)
    verify(recentNewsManager).add(newsItem)
  }

  @Test fun newsSourcesSubject_returnsNewsSourcesSubjectFromNewsSourcesManager() {
    newsRepository.newsSourcesSubject
    verify(newsSourcesManager).newsSourcesSubject
  }

  @Test fun addNewsSource_callsAddFromNewsSourcesManager() {
    newsRepository.addNewsSource(newsSource1)
    verify(newsSourcesManager).add(newsSource1)
  }
}

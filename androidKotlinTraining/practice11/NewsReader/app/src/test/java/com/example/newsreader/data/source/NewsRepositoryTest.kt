package com.example.newsreader.data.source

import com.example.newsreader.data.models.NewsItem
import com.example.newsreader.data.models.NewsSource
import com.example.newsreader.network.*
import com.example.newsreader.network.data_transfer_objects.general_news.NetworkGeneralNewsChannel
import com.example.newsreader.network.data_transfer_objects.general_news.NetworkGeneralNewsItem
import com.example.newsreader.network.data_transfer_objects.general_news.toDomainModel
import com.example.newsreader.network.data_transfer_objects.google_news.NetworkGoogleNewsChannel
import com.example.newsreader.network.data_transfer_objects.google_news.NetworkGoogleNewsItem
import com.example.newsreader.network.data_transfer_objects.google_news.toDomainModel
import com.example.newsreader.network.data_transfer_objects.toyokeizai_news.NetworkToyokeizaiNewsChannel
import com.example.newsreader.network.data_transfer_objects.toyokeizai_news.NetworkToyokeizaiNewsItem
import com.example.newsreader.network.data_transfer_objects.toyokeizai_news.toDomainModel
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
  @Mock private lateinit var googleNewsApi: GoogleNewsApi
  @Mock private lateinit var googleNewsRetrofitService: GoogleNewsApiService
  private val googleNewsChannel =
    NetworkGoogleNewsChannel(listOf(NetworkGoogleNewsItem("test1", "testUrl1", ZonedDateTime.now(), "testSource")))

  @Mock private lateinit var toyokeizaiNewsApi: ToyokeizaiNewsApi
  @Mock private lateinit var toyokeizaiNewsRetrofitService: ToyokeizaiNewsApiService
  private val toyokeizaiNewsChannel =
    NetworkToyokeizaiNewsChannel(listOf(NetworkToyokeizaiNewsItem("test2", "testUrl2", ZonedDateTime.now().minusHours(2))))

  @Mock private lateinit var generalNewsApi: GeneralNewsApi
  @Mock private lateinit var generalNewsRetrofitService: GeneralNewsApiService
  private val generalNewsChannel =
    NetworkGeneralNewsChannel(listOf(
      NetworkGeneralNewsItem("test3", "testUrl3", ZonedDateTime.now().minusHours(1)),
      NetworkGeneralNewsItem("test4", "testUrl4", ZonedDateTime.now().minusHours(3))
    ))

  @Mock private lateinit var followedNewsManager: FollowedNewsManager
  @Mock private lateinit var recentNewsManager: RecentNewsManager
  @Mock private lateinit var newsSourcesManager: NewsSourcesManager

  @Mock private lateinit var newsItem: NewsItem
  private val exampleNewsSource = NewsSource("example", "www.example.com")

  private lateinit var newsRepository: NewsRepository

  @Before fun setupMocksAndNewsItemsRepository() {
    `when`(googleNewsApi.retrofitService).thenReturn(googleNewsRetrofitService)
    `when`(googleNewsRetrofitService.getNewsChannel()).thenReturn(Observable.just(googleNewsChannel))

    `when`(toyokeizaiNewsApi.retrofitService).thenReturn(toyokeizaiNewsRetrofitService)
    `when`(toyokeizaiNewsRetrofitService.getNewsChannel()).thenReturn(Observable.just(toyokeizaiNewsChannel))

    `when`(generalNewsApi.retrofitService).thenReturn(generalNewsRetrofitService)
    `when`(generalNewsRetrofitService.getNewsChannel(exampleNewsSource.url)).thenReturn(Observable.just(generalNewsChannel))

    newsRepository = NewsRepository(
      googleNewsApi,
      toyokeizaiNewsApi,
      generalNewsApi,
      followedNewsManager,
      recentNewsManager,
      newsSourcesManager
    )
  }

  @Test fun getGoogleNews_returnsGoogleNewsItems() {
    val actual = newsRepository.getGoogleNews(refresh = true).blockingFirst()
    val expected = googleNewsChannel.toDomainModel()
    assertEquals(actual, expected)
  }

  @Test fun getToyokeizaiNews_returnsToyokeizaiNewsItems() {
    val actual = newsRepository.getToyokeizaiNews(refresh = true).blockingFirst()
    val expected = toyokeizaiNewsChannel.toDomainModel()
    assertEquals(actual, expected)
  }

  @Test fun getGeneralNews_returnsNewsItemsOfNewsSource() {
    val actual = newsRepository.getGeneralNews(exampleNewsSource, refresh = true).blockingFirst()
    val expected = generalNewsChannel.toDomainModel(exampleNewsSource.name)
    assertEquals(actual, expected)
  }

  @Test fun getAllNews_returnsAllNewsItemsByDescendingOrderOfPublishedDate() {
    val newsSourcesSubject: BehaviorSubject<List<NewsSource>> = BehaviorSubject.create()
    newsSourcesSubject.onNext(listOf(exampleNewsSource))
    `when`(newsSourcesManager.newsSourcesSubject).thenReturn(newsSourcesSubject)

    val actual = newsRepository.getAllNews(refresh = true).blockingFirst()
    val expected = (googleNewsChannel.toDomainModel() +
                    toyokeizaiNewsChannel.toDomainModel() +
                    generalNewsChannel.toDomainModel(exampleNewsSource.name)
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
    newsRepository.addNewsSource(exampleNewsSource)
    verify(newsSourcesManager).add(exampleNewsSource)
  }
}

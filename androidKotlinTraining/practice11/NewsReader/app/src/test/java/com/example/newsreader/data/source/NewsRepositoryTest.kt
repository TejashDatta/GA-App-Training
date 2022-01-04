package com.example.newsreader.data.source

import android.content.SharedPreferences
import com.example.newsreader.network.GoogleNewsApi
import com.example.newsreader.network.GoogleNewsApiService
import com.example.newsreader.network.ToyokeizaiNewsApi
import com.example.newsreader.network.ToyokeizaiNewsApiService
import com.example.newsreader.network.data_transfer_objects.google_news.NetworkGoogleNewsChannel
import com.example.newsreader.network.data_transfer_objects.google_news.NetworkGoogleNewsItem
import com.example.newsreader.network.data_transfer_objects.google_news.toDomainModel
import com.example.newsreader.network.data_transfer_objects.toyokeizai_news.NetworkToyokeizaiNewsChannel
import com.example.newsreader.network.data_transfer_objects.toyokeizai_news.NetworkToyokeizaiNewsItem
import com.example.newsreader.network.data_transfer_objects.toyokeizai_news.toDomainModel
import io.reactivex.rxjava3.core.Observable
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
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
    NetworkToyokeizaiNewsChannel(listOf(NetworkToyokeizaiNewsItem("test2", "testUrl2", ZonedDateTime.now().minusHours(1))))

  @Mock private lateinit var followedNewsSharedPreferences: SharedPreferences
  @Mock private lateinit var recentNewsSharedPreferences: SharedPreferences
  @Mock private lateinit var newsSourcesSharedPreferences: SharedPreferences

  private lateinit var newsRepository: NewsRepository

  @Before fun setupMocksAndNewsItemsRepository() {
    `when`(googleNewsApi.retrofitService).thenReturn(googleNewsRetrofitService)
    `when`(googleNewsRetrofitService.getNewsChannel()).thenReturn(Observable.just(googleNewsChannel))

    `when`(toyokeizaiNewsApi.retrofitService).thenReturn(toyokeizaiNewsRetrofitService)
    `when`(toyokeizaiNewsRetrofitService.getNewsChannel()).thenReturn(Observable.just(toyokeizaiNewsChannel))

    newsRepository = NewsRepository(
      googleNewsApi,
      toyokeizaiNewsApi,
      followedNewsSharedPreferences,
      recentNewsSharedPreferences,
      newsSourcesSharedPreferences
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

  @Test fun getAllNews_returnsAllNewsItemsByDescendingOrderOfPublishedDate() {
    val actual = newsRepository.getAllNews(refresh = true).blockingFirst()
    val expected = googleNewsChannel.toDomainModel() + toyokeizaiNewsChannel.toDomainModel()
    assertEquals(actual, expected)
  }
}

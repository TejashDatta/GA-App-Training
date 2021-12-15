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
class NewsItemsRepositoryTest {
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

  private lateinit var newsItemsRepository: NewsItemsRepository

  @Before fun setupMocksAndNewsItemsRepository() {
    `when`(googleNewsApi.retrofitService).thenReturn(googleNewsRetrofitService)
    `when`(googleNewsRetrofitService.getNewsChannel()).thenReturn(Observable.just(googleNewsChannel))

    `when`(toyokeizaiNewsApi.retrofitService).thenReturn(toyokeizaiNewsRetrofitService)
    `when`(toyokeizaiNewsRetrofitService.getNewsChannel()).thenReturn(Observable.just(toyokeizaiNewsChannel))

    newsItemsRepository = NewsItemsRepository(
      googleNewsApi,
      toyokeizaiNewsApi,
      followedNewsSharedPreferences,
      recentNewsSharedPreferences
    )
  }

  @Test fun getGoogleNews_returnsGoogleNewsItems() {
    val actual = newsItemsRepository.getGoogleNews(refresh = true).blockingFirst()
    val expected = googleNewsChannel.toDomainModel()
    assertEquals(actual, expected)
  }

  @Test fun getToyokeizaiNews_returnsToyokeizaiNewsItems() {
    val actual = newsItemsRepository.getToyokeizaiNews(refresh = true).blockingFirst()
    val expected = toyokeizaiNewsChannel.toDomainModel()
    assertEquals(actual, expected)
  }

  @Test fun getAllNews_returnsAllNewsItemsByDescendingOrderOfPublishedDate() {
    val actual = newsItemsRepository.getAllNews(refresh = true).blockingFirst()
    val expected = googleNewsChannel.toDomainModel() + toyokeizaiNewsChannel.toDomainModel()
    assertEquals(actual, expected)
  }
}

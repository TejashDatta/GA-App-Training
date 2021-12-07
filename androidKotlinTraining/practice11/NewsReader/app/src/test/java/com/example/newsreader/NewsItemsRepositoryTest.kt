package com.example.newsreader

import com.example.newsreader.data.source.NewsItemsRepository
import com.example.newsreader.network.GoogleNewsApi
import com.example.newsreader.network.GoogleNewsApiService
import com.example.newsreader.network.data_transfer_objects.google_news.NetworkGoogleNewsChannel
import com.example.newsreader.network.data_transfer_objects.google_news.NetworkGoogleNewsItem
import com.example.newsreader.network.data_transfer_objects.google_news.toDomainModel
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
  @Mock private lateinit var retrofitService: GoogleNewsApiService

  @Mock private lateinit var googleNewsChannel: NetworkGoogleNewsChannel

  private lateinit var newsItemsRepository: NewsItemsRepository

  @Before fun setupNewsItemsRepository() {
    newsItemsRepository = NewsItemsRepository(googleNewsApi)
  }

  @Test fun getNewsItems_returnsNewsItems() {
    googleNewsChannel =
      NetworkGoogleNewsChannel(listOf(NetworkGoogleNewsItem("test", "testUrl", ZonedDateTime.now(), "testSource")))
    `when`(googleNewsApi.retrofitService).thenReturn(retrofitService)
    `when`(retrofitService.getNewsChannel()).thenReturn(Observable.just(googleNewsChannel))

    val actual = newsItemsRepository.getNewsItems().blockingFirst()
    val expected = googleNewsChannel.toDomainModel()
    assertEquals(actual, expected)
  }
}

package com.example.newsreader

import com.example.newsreader.data.NewsItemsRepository
import com.example.newsreader.network.NewsApi
import com.example.newsreader.network.NewsApiService
import com.example.newsreader.network.data_transfer_objects.NetworkNewsChannel
import com.example.newsreader.network.data_transfer_objects.NetworkNewsItem
import com.example.newsreader.network.data_transfer_objects.toDomainModel
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
  @Mock private lateinit var newsApi: NewsApi
  @Mock private lateinit var retrofitService: NewsApiService

  @Mock private lateinit var newsChannel: NetworkNewsChannel

  private lateinit var newsItemsRepository: NewsItemsRepository

  @Before fun setupNewsItemsRepository() {
    newsItemsRepository = NewsItemsRepository(newsApi)
  }

  @Test fun getNewsItems_returnsNewsItems() {
    newsChannel =
      NetworkNewsChannel(listOf(NetworkNewsItem("test", "testUrl", ZonedDateTime.now(), "testSource")))
    `when`(newsApi.retrofitService).thenReturn(retrofitService)
    `when`(retrofitService.getNewsChannel()).thenReturn(Observable.just(newsChannel))

    val actual = newsItemsRepository.getNewsItems().blockingFirst()
    val expected = newsChannel.toDomainModel()
    assertEquals(actual, expected)
  }
}

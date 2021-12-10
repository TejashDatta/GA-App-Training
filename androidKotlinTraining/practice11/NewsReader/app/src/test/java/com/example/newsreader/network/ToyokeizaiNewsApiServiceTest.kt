package com.example.newsreader.network

import com.example.newsreader.network.data_transfer_objects.toyokeizai_news.NetworkToyokeizaiNewsChannel
import com.tickaroo.tikxml.TikXml
import com.tickaroo.tikxml.retrofit.TikXmlConverterFactory
import okhttp3.ResponseBody.Companion.toResponseBody
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory

class ToyokeizaiNewsApiServiceTest {
  private lateinit var server: MockWebServer

  private lateinit var retrofitService: ToyokeizaiNewsApiService

  private val exampleResponseContent = MockResponseFileReader("example-toyokeizai-news-rss-response.xml").content

  private val tikXml = TikXml.Builder().exceptionOnUnreadXml(false).build()

  @Before fun setup() {
    server = MockWebServer()

    server.enqueue(MockResponse().setBody(exampleResponseContent))

    server.start()

    retrofitService = createRetrofit(server).create(ToyokeizaiNewsApiService::class.java)
  }

  private fun createRetrofit(server: MockWebServer): Retrofit {
    return Retrofit.Builder()
      .addConverterFactory(TikXmlConverterFactory.create(tikXml))
      .baseUrl(server.url("/"))
      .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
      .build()
  }

  @After fun tearDown() {
    server.shutdown()
  }

  @Test fun getNewsChannel_makesCorrectRequest() {
    retrofitService.getNewsChannel().blockingFirst()

    val request = server.takeRequest()
    assertEquals("/list/feed/rss/", request.path)
  }

  @Test fun getNewsChannel_receivesAndParsesResponseCorrectly() {
    val exampleResponseContentSource = exampleResponseContent.toResponseBody().source()
    val expected = tikXml.read(exampleResponseContentSource, NetworkToyokeizaiNewsChannel::class.java)
    val response = retrofitService.getNewsChannel().blockingFirst()

    assertEquals(response, expected)
  }
}

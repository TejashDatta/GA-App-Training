package com.example.newsreader

import com.example.newsreader.network.NewsApiService
import com.example.newsreader.network.data_transfer_objects.NetworkNewsChannel
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

class NewsApiServiceTest {
  private lateinit var server: MockWebServer

  private lateinit var retrofitService: NewsApiService

  private val exampleResponseContent = MockResponseFileReader("example-rss-response.xml").content

  private val tikXml = TikXml.Builder().exceptionOnUnreadXml(false).build()

  @Before fun setup() {
    server = MockWebServer()

    server.enqueue(MockResponse().setBody(exampleResponseContent))

    server.start()

    retrofitService = createRetrofit(server).create(NewsApiService::class.java)
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
    assertEquals("/rss?hl=ja&gl=JP&ceid=JP:ja", request.path)
  }

  @Test fun getNewsChannel_receivesAndParsesResponseCorrectly() {
    val exampleResponseContentSource = exampleResponseContent.toResponseBody().source()
    val expected = tikXml.read(exampleResponseContentSource, NetworkNewsChannel::class.java)
    val response = retrofitService.getNewsChannel().blockingFirst()

    assertEquals(response, expected)
  }
}

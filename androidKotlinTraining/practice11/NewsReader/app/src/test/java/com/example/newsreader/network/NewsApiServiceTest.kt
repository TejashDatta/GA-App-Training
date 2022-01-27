package com.example.newsreader.network

import com.example.newsreader.network.data_transfer_objects.news.NetworkNewsChannel
import com.tickaroo.tikxml.TikXml
import okhttp3.ResponseBody.Companion.toResponseBody
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.HttpException
import java.net.HttpURLConnection

@RunWith(MockitoJUnitRunner::class)
class NewsApiServiceTest {
  private lateinit var server: MockWebServer

  private val retrofitService = NewsApi.retrofitService

  private val exampleResponseContent = MockResponseFileReader("example-toyokeizai-news-rss-response.xml").content

  private val tikXml = TikXml.Builder().exceptionOnUnreadXml(false).build()

  @Before fun setup() {
    server = MockWebServer()
  }

  private fun setupNormalServerResponse() {
    server.enqueue(MockResponse().setBody(exampleResponseContent))

    server.start()
  }

  private fun setupNotFoundErrorServerResponse() {
    server.enqueue(MockResponse().setResponseCode(HttpURLConnection.HTTP_NOT_FOUND))

    server.start()
  }

  @After fun tearDown() {
    server.shutdown()
  }

  @Test fun getNewsChannel_makesRequestToCorrectFullUrl() {
    setupNormalServerResponse()

    val fullUrl = server.url("/index.xml").toString()
    retrofitService.getNewsChannel(fullUrl).blockingFirst()

    val request = server.takeRequest()
    assertEquals(fullUrl, request.requestUrl.toString())
  }

  @Test fun getNewsChannel_receivesAndParsesResponseCorrectly() {
    setupNormalServerResponse()
    val mockWebServerUrl = server.url("/").toString()

    val exampleResponseContentSource = exampleResponseContent.toResponseBody().source()
    val expected = tikXml.read(exampleResponseContentSource, NetworkNewsChannel::class.java)
    val response = retrofitService.getNewsChannel(mockWebServerUrl).blockingFirst()

    assertEquals(response, expected)
  }

  @Test fun getNewsChannel_throwsNotFoundHttpExceptionWhenSiteNotFound() {
    setupNotFoundErrorServerResponse()
    val mockWebServerUrl = server.url("/").toString()

    assertThrows(HttpException::class.java) {
      retrofitService.getNewsChannel(mockWebServerUrl).blockingFirst()
    }
  }
}

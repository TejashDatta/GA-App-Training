package com.example.newsreader

import com.example.newsreader.network.NewsApi
import org.junit.Test
import java.util.concurrent.CountDownLatch

class NewsApiTest {
  @Test fun getNewsChannel_returnsNewsChannelObservable() {
    val latch = CountDownLatch(1)

    NewsApi.retrofitService.getNewsChannel()
      .subscribe { newsChannel ->
        println(newsChannel)
        latch.countDown()
      }

    latch.await()
  }
}

package com.example.newsreader.network

import com.example.newsreader.network.data_transfer_objects.toyokeizai_news.NetworkToyokeizaiNewsChannel
import com.tickaroo.tikxml.TikXml
import com.tickaroo.tikxml.retrofit.TikXmlConverterFactory
import io.reactivex.rxjava3.core.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.http.GET

private const val BASE_URL = "https://toyokeizai.net/"
private const val NEWS_CHANNEL_ENDPOINT = "/list/feed/rss/"

private val tikXml = TikXml.Builder()
                      .exceptionOnUnreadXml(false)
                      .build()

private val retrofit = Retrofit.Builder()
                        .addConverterFactory(TikXmlConverterFactory.create(tikXml))
                        .baseUrl(BASE_URL)
                        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                        .build()

interface ToyokeizaiNewsApiService {
  @GET(NEWS_CHANNEL_ENDPOINT)
  fun getNewsChannel(): Observable<NetworkToyokeizaiNewsChannel>
}

object ToyokeizaiNewsApi {
  val retrofitService: ToyokeizaiNewsApiService by lazy {
    retrofit.create(ToyokeizaiNewsApiService::class.java)
  }
}

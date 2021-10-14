package com.example.newsreader.network

import com.tickaroo.tikxml.TikXml
import com.tickaroo.tikxml.retrofit.TikXmlConverterFactory
import io.reactivex.rxjava3.core.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.http.GET

private const val BASE_URL = "https://news.google.com/"
private const val NEWS_CHANNEL_ENDPOINT = "rss?hl=ja&gl=JP&ceid=JP:ja"

private val tikXml = TikXml.Builder()
                      .exceptionOnUnreadXml(false)
                      .build()

private val retrofit = Retrofit.Builder()
                        .addConverterFactory(TikXmlConverterFactory.create(tikXml))
                        .baseUrl(BASE_URL)
                        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                        .build()

interface NewsApiService {
  @GET(NEWS_CHANNEL_ENDPOINT)
  fun getNewsChannel(): Observable<NewsChannel>
}

object NewsApi {
  val retrofitService: NewsApiService by lazy {
    retrofit.create(NewsApiService::class.java)
  }
}

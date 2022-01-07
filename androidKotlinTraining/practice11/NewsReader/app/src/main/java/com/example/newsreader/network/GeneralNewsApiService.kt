package com.example.newsreader.network

import com.example.newsreader.network.data_transfer_objects.general_news.NetworkGeneralNewsChannel
import com.tickaroo.tikxml.TikXml
import com.tickaroo.tikxml.retrofit.TikXmlConverterFactory
import io.reactivex.rxjava3.core.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.http.GET
import retrofit2.http.Url

// retrofit は baseUrl を利用しなくても、指定する必要があります
private const val NECESSARY_BASE_URL = "https://news.google.com/"

private val tikXml = TikXml.Builder()
                      .exceptionOnUnreadXml(false)
                      .build()

private val retrofit = Retrofit.Builder()
                        .addConverterFactory(TikXmlConverterFactory.create(tikXml))
                        .baseUrl(NECESSARY_BASE_URL)
                        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                        .build()

interface GeneralNewsApiService {
  @GET
  fun getNewsChannel(@Url url: String): Observable<NetworkGeneralNewsChannel>
}

object GeneralNewsApi {
  val retrofitService: GeneralNewsApiService by lazy {
    retrofit.create(GeneralNewsApiService::class.java)
  }
}

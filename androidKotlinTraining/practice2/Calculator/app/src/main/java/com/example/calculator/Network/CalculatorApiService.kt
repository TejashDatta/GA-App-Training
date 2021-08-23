package com.example.calculator.Network

import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL = "https://www.rurihabachi.com/"
private const val EXPRESSION_QUERY_PARAMETER = "exp"

private val retrofit = Retrofit
                        .Builder()
                        .addConverterFactory(ScalarsConverterFactory.create())
                        .baseUrl(BASE_URL)
                        .build()

interface CalculatorApiService {
  @GET("/web/webapi/calculator/json")
  suspend fun requestCalculation(@Query(EXPRESSION_QUERY_PARAMETER) expression: String): String
}

object CalculatorApi {
  val retrofitService: CalculatorApiService by lazy {
    retrofit.create(CalculatorApiService::class.java)
  }
}

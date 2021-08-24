package com.example.calculator.Network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL = "https://www.rurihabachi.com/"
private const val CALCULATOR_ENDPOINT = "/web/webapi/calculator/json"
private const val EXPRESSION_QUERY_PARAMETER = "exp"

private val moshi = Moshi
                      .Builder()
                      .add(KotlinJsonAdapterFactory())
                      .build()

private val retrofit = Retrofit
                        .Builder()
                        .addConverterFactory(MoshiConverterFactory.create(moshi))
                        .baseUrl(BASE_URL)
                        .build()

enum class CalculatorApiResponseStatus(val status_code: Int) { OK(0) }

interface CalculatorApiService {
  @GET(CALCULATOR_ENDPOINT)
  suspend fun requestCalculation(
    @Query(EXPRESSION_QUERY_PARAMETER) expression: String): CalculatorResponse
}

object CalculatorApi {
  val retrofitService: CalculatorApiService by lazy {
    retrofit.create(CalculatorApiService::class.java)
  }
}

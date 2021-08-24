package com.example.calculator.Network

import com.squareup.moshi.Json

data class CalculatorResponse(
  val status: Int,
  val message: String,
  val value: List<CalculatedValue>
) {
  val result
    get() = value[0].value
}

data class CalculatedValue(
  @Json(name = "calculatedvalue") val value: Double
)

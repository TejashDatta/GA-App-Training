package com.example.calculator.network

import com.squareup.moshi.Json

data class CalculationResult(
  val status: Int,
  val message: String,
  val value: List<CalculatedValue>
)

data class CalculatedValue(
  @Json(name = "calculatedvalue") val value: Double
)

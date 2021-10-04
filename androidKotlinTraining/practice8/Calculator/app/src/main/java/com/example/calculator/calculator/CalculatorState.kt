package com.example.calculator.calculator

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CalculatorState(
  val history: String?,
  val decimalPointIsSet: Boolean,
  val operand1: String,
  val operand2: String,
  var operator: Char?,
  var result: Double?
) : Parcelable

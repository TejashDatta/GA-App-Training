package com.example.calculator

import android.widget.TextView
import androidx.databinding.BindingAdapter

@BindingAdapter("calculatorState", "output")
fun TextView.displayOutput(calculatorState: CalculatorState?, output: String?) {
  val safeCalculatorState = calculatorState ?: return
  val safeOutput = output ?: return

  text = when(safeCalculatorState) {
    CalculatorState.READY, CalculatorState.COMPLETED -> safeOutput
    CalculatorState.LOADING -> context.getString(R.string.loading)
    CalculatorState.ERROR -> context.getString(R.string.error_notice)
  }
}


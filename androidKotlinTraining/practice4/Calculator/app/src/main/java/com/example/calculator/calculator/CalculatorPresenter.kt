package com.example.calculator.calculator

import android.util.Log

class CalculatorPresenter(
  private val calculatorView: CalculatorContract.View): CalculatorContract.Presenter {

  init {
    calculatorView.presenter = this
  }

  override fun start() {
    Log.d("CalculatorPresenter", "presenter started")
  }
}

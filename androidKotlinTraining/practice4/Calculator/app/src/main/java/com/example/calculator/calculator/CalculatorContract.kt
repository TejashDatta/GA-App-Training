package com.example.calculator.calculator

class CalculatorContract {
  interface View {
    var presenter: Presenter
  }

  interface Presenter {
    fun start()
  }
}

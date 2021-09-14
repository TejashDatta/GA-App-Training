package com.example.calculator.calculator

class CalculatorContract {
  interface View {
    var presenter: Presenter

    fun setOutput(output: String)
  }

  interface Presenter {
    fun start()

    fun reset()

    fun operandInput(digit: Char)

    fun operatorInput(operatorSymbol: Char)

    fun decimalPointInput()

    fun requestResult()
  }
}

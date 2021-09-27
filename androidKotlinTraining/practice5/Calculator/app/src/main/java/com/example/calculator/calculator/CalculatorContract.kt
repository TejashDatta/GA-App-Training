package com.example.calculator.calculator

import com.example.calculator.BasePresenter
import com.example.calculator.BaseView

class CalculatorContract {
  interface View: BaseView<Presenter> {
    fun setOutput(output: String)
  }

  interface Presenter: BasePresenter {
    fun reset()

    fun operandInput(digit: Char)

    fun operatorInput(operatorSymbol: Char)

    fun decimalPointInput()

    fun requestResult()
  }
}

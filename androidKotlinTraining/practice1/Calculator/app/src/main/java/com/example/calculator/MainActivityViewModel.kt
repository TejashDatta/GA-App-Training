package com.example.calculator

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainActivityViewModel: ViewModel() {
  private var decimalPointIsSet = false

  private var operand1: String = ""
  private var operand2: String = ""
  private var operator: Char? = null
  private var result: Float? = null

  val output = MutableLiveData<String>()

  init {
    reset()
  }

  private fun updateOutput() {
    val formattedResult = if (result == null) "" else "= ${result}"
    output.value = "${operand1} ${operator ?: ""} ${operand2} $formattedResult"
  }

  private fun beginNewOperandInput() {
    decimalPointIsSet = false
  }

  private fun resetIfCalculationCompleted() {
    if (result != null) reset()
  }

  private fun String.completeDecimalPoint(): String {
    return if (this.lastOrNull() == '.') this + '0' else this
  }

  fun reset(){
    operand1 = ""
    operand2 = ""
    operator = null
    result = null
    beginNewOperandInput()

    updateOutput()
  }

  fun operandInput(digit: Char) {
    resetIfCalculationCompleted()

    if (operator == null) operand1 += digit else operand2 += digit

    updateOutput()
  }

  fun operatorInput(operatorSymbol: Char) {
    resetIfCalculationCompleted()

    operand1 = operand1.completeDecimalPoint()
    
    if (operand1 == "" || operator != null) return
    operator = operatorSymbol
    beginNewOperandInput()

    updateOutput()
  }

  fun decimalPointInput() {
    resetIfCalculationCompleted()

    if (decimalPointIsSet) return
    var operand = if (operator == null) operand1 else operand2
    if (operand == "") operand = "0"
    operand += '.'
    if (operator == null) operand1 = operand else operand2 = operand
    decimalPointIsSet = true

    updateOutput()
  }

  fun requestResult() {
    resetIfCalculationCompleted()

    operand2 = operand2.completeDecimalPoint()

    val safeOperand1 = operand1.toFloatOrNull() ?: return
    val safeOperand2 = operand2.toFloatOrNull() ?: return
    val safeOperator = operator ?: return

    result = when(safeOperator) {
      '+' -> safeOperand1 + safeOperand2
      '-' -> safeOperand1 - safeOperand2
      '*' -> safeOperand1 * safeOperand2
      '/' -> safeOperand1 / safeOperand2
      else -> throw Exception("Unknown operator")
    }

    updateOutput()
  }
}

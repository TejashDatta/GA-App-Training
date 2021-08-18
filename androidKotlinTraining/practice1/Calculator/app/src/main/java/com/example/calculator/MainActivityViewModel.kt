package com.example.calculator

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainActivityViewModel: ViewModel() {
  private var decimalPointIsSet: Boolean = false

  val operand1 = MutableLiveData<String>()
  val operand2 = MutableLiveData<String>()
  val operator = MutableLiveData<Char>()
  val result = MutableLiveData<Float>()

  init {
    reset()
  }

  private fun reset(){
    operand1.value = ""
    operand2.value = ""
    result.value = null
    decimalPointIsSet = false
  }

  fun operandInput(digit: Char) {
    if (result != null) return reset()
    val operand = if (operator.value == null) operand1 else operand2
    operand.value += digit
  }

  fun operatorInput(operatorSymbol: Char) {
    if (result != null) return reset()
    if (operator.value != null) return
    operator.value = operatorSymbol
    decimalPointIsSet = false
  }

  fun decimalPointInput() {
    if(decimalPointIsSet) return
    val operand = if (operator.value == null) operand1 else operand2
    if (operand.value == "") operand.value = "0"
    operand.value += '.'
    decimalPointIsSet = true
  }

  fun requestResult() {
    val safeOperand1 = operand1.value?.toFloat() ?: return
    val safeOperand2 = operand2.value?.toFloat() ?: return
    val safeOperator = operator.value ?: return
    result.value = when(safeOperator) {
      '+' -> safeOperand1 + safeOperand2
      '-' -> safeOperand1 - safeOperand2
      '*' -> safeOperand1 * safeOperand2
      '/' -> safeOperand1 / safeOperand2
      else -> throw Exception("Unknown operator")
    }
  }
}

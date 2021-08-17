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
    if (operator.value == null) {
      operand1.value += digit
    }
    else {
      operand2.value += digit
    }
  }

  fun operatorInput(operatorSymbol: Char) {
    if (result != null) return reset()
    if (operator.value != null) return
    operator.value = operatorSymbol
    decimalPointIsSet = false
  }

  fun decimalPointInput() {
    if(decimalPointIsSet) return
    if (operator.value == null) {
      operand1.value += '.'
    }
    else {
      operand2.value += '.'
    }
    decimalPointIsSet = true
  }

  fun requestResult() {
    val safeOperand1 = operand1.value?.toFloat() ?: return
    val safeOperand2 = operand2.value?.toFloat() ?: return
    result.value = when(operator.value) {
      '+' -> safeOperand1 + safeOperand2
      '-' -> safeOperand1 - safeOperand2
      '*' -> safeOperand1 * safeOperand2
      '/' -> safeOperand1 / safeOperand2
      else -> throw Exception("Unknown operator")
    }
  }
}

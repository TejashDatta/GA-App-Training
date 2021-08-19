package com.example.calculator

import android.util.Log
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainActivityViewModel: ViewModel() {
  private var decimalPointIsSet = false

  private val operand1 = MutableLiveData<String>()
  private val operand2 = MutableLiveData<String>()
  private val operator = MutableLiveData<Char>()
  private val result = MutableLiveData<Float>()

  val output = MediatorLiveData<String>()

  init {
    reset()
    output.addSource(operand1) { updateOutput() }
    output.addSource(operand2) { updateOutput() }
    output.addSource(operator) { updateOutput() }
    output.addSource(result) { updateOutput() }
  }

  private fun updateOutput() {
    val formattedResult = if (result.value == null) "" else "= ${result.value}"
    output.value = "${operand1.value} ${operator.value ?: ""} ${operand2.value} $formattedResult"
  }

  private fun beginNewOperandInput() {
    decimalPointIsSet = false
  }

  private fun checkForResult() {
    if (result.value != null) reset()
  }

  private fun MutableLiveData<String>.completeDecimalPoint(): MutableLiveData<String> {
    if (this.value?.lastOrNull() == '.') this.value += '0'
    return this
  }

  private fun MutableLiveData<String>.toFloat(): Float? {
    if (this.value == "") return null
    return this.value?.toFloat()
  }

  fun reset(){
    operand1.value = ""
    operand2.value = ""
    operator.value = null
    result.value = null
    beginNewOperandInput()
  }

  fun operandInput(digit: Char) {
    checkForResult()
    val operand = if (operator.value == null) operand1 else operand2
    operand.value += digit
  }

  fun operatorInput(operatorSymbol: Char) {
    checkForResult()
    if (operand1.completeDecimalPoint().value == "" || operator.value != null) return
    operator.value = operatorSymbol
    beginNewOperandInput()
  }

  fun decimalPointInput() {
    checkForResult()
    if (decimalPointIsSet) return
    val operand = if (operator.value == null) operand1 else operand2
    if (operand.value == "") operand.value = "0"
    operand.value += '.'
    decimalPointIsSet = true
  }

  fun requestResult() {
    checkForResult()
    val safeOperand1 = operand1.toFloat() ?: return
    val safeOperand2 = operand2.completeDecimalPoint().toFloat() ?: return
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

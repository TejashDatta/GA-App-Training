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

  private fun resetIfCalculationCompleted() {
    if (result.value != null) reset()
  }

  private fun MutableLiveData<String>.completeDecimalPoint(): MutableLiveData<String> {
    if (this.value?.lastOrNull() == '.') this.value += '0'
    return this
  }

  private fun logDisplay() {
    Log.d(
      "MainActivityViewModel",
      "${operand1.value} ${operator.value ?: ""} ${operand2.value} = ${result.value ?: ""}"
    )
  }

  fun reset(){
    operand1.value = ""
    operand2.value = ""
    operator.value = null
    result.value = null
    beginNewOperandInput()
  }

  fun operandInput(digit: Char) {
    resetIfCalculationCompleted()

    val operand = if (operator.value == null) operand1 else operand2
    operand.value += digit
  }

  fun operatorInput(operatorSymbol: Char) {
    resetIfCalculationCompleted()

    if (operand1.completeDecimalPoint().value == "" || operator.value != null) return
    operator.value = operatorSymbol
    beginNewOperandInput()
  }

  fun decimalPointInput() {
    resetIfCalculationCompleted()

    if (decimalPointIsSet) return
    val operand = if (operator.value == null) operand1 else operand2
    if (operand.value == "") operand.value = "0"
    operand.value += '.'
    decimalPointIsSet = true
  }

  fun requestResult() {
    resetIfCalculationCompleted()

    val safeOperand1 = operand1.value?.toFloatOrNull() ?: return
    val safeOperand2 = operand2.completeDecimalPoint().value?.toFloatOrNull() ?: return
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

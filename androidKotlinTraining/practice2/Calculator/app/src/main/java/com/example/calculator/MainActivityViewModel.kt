package com.example.calculator

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.calculator.network.CalculatorApi
import kotlinx.coroutines.launch

class MainActivityViewModel: ViewModel() {
  companion object {
    const val MAX_RESULT_LENGTH = 12
  }

  private var decimalPointIsSet = false

  private var operand1: String = ""
  private var operand2: String = ""
  private var operator: Char? = null
  private var result: Double? = null

  val output = MutableLiveData<String>()

  init {
    reset()
    calculate()
  }

  private fun calculate() {
    val expression = "5 * 5.325"
    viewModelScope.launch {
      try {
        Log.d("MainActivityViewModel",
          CalculatorApi.retrofitService.requestCalculation(expression).toString()
        )
      } catch (e: Exception) {
        Log.d("MainActivityViewModel", e.message ?: "")
      }
    }
  }

  private fun updateOutput() {
    output.value = "${operand1} ${operator ?: ""} ${operand2} ${formattedResult()}"
  }

  private fun formattedResult(): String{
    if (result == null) return ""

    val formattedResult = "%f".format(result).trim('0').trim('.')
    val abbreviatedResult = formattedResult.substring(0, Math.min(formattedResult.length, MAX_RESULT_LENGTH))
    val abbreviationIndicator = if (formattedResult.length > MAX_RESULT_LENGTH) "..." else ""
    return "= $abbreviatedResult$abbreviationIndicator"
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
    operand = operand.ifEmpty { "0" }
    operand += '.'
    if (operator == null) operand1 = operand else operand2 = operand
    decimalPointIsSet = true

    updateOutput()
  }

  fun requestResult() {
    resetIfCalculationCompleted()

    operand2 = operand2.completeDecimalPoint()

    val safeOperand2 = operand2.toDoubleOrNull() ?: return
    val safeOperand1 = operand1.toDoubleOrNull() ?: return
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

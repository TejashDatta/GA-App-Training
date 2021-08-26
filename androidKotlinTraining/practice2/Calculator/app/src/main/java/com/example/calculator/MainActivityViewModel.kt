package com.example.calculator

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.calculator.network.CalculatorApi
import com.example.calculator.network.CalculatorApiResponseStatus
import kotlinx.coroutines.launch

enum class CalculatorState { READY, LOADING, COMPLETED, ERROR }

class MainActivityViewModel : ViewModel() {
  companion object {
    const val MAX_RESULT_LENGTH = 12
  }

  private var decimalPointIsSet = false

  private var operand1: String = ""
  private var operand2: String = ""
  private var operator: Char? = null
  private var result: Double? = null

  val calculatorState = MutableLiveData<CalculatorState>()
  val output = MutableLiveData<String>()

  init {
    reset()
  }

  private fun setOutputToExpression() {
    output.value = "${operand1} ${operator ?: ""} ${operand2} ${formattedResult()}"
  }

  private fun formattedResult(): String{
    if (result == null) return ""

    val formattedResult = "%.${MAX_RESULT_LENGTH}f".format(result).trim('0').trim('.')
    val abbreviatedResult = formattedResult.substring(0, Math.min(formattedResult.length, MAX_RESULT_LENGTH))
    val abbreviationIndicator = if (formattedResult.length > MAX_RESULT_LENGTH) "..." else ""
    return "= $abbreviatedResult$abbreviationIndicator"
  }

  private fun beginNewOperandInput() {
    decimalPointIsSet = false
  }

  private fun resetIfCalculationCompleted() {
    if (calculatorState.value == CalculatorState.COMPLETED) reset()
  }

  private fun String.completeDecimalPoint(): String {
    return if (this.lastOrNull() == '.') this + '0' else this
  }

  private suspend fun requestCalculation(expression: String): Double {
    val response = CalculatorApi.retrofitService.requestCalculation(expression)
    if (response.status == CalculatorApiResponseStatus.OK.status_code) {
      return response.result
    } else {
      throw Exception(response.message)
    }
  }

  fun reset(){
    calculatorState.value = CalculatorState.READY

    operand1 = ""
    operand2 = ""
    operator = null
    result = null
    beginNewOperandInput()

    setOutputToExpression()
  }

  fun operandInput(digit: Char) {
    resetIfCalculationCompleted()

    if (operator == null) operand1 += digit else operand2 += digit

    setOutputToExpression()
  }

  fun operatorInput(operatorSymbol: Char) {
    resetIfCalculationCompleted()

    operand1 = operand1.completeDecimalPoint()

    if (operand1 == "" || operator != null) return
    operator = operatorSymbol
    beginNewOperandInput()

    setOutputToExpression()
  }

  fun decimalPointInput() {
    resetIfCalculationCompleted()

    if (decimalPointIsSet) return
    var operand = if (operator == null) operand1 else operand2
    operand = operand.ifEmpty { "0" }
    operand += '.'
    if (operator == null) operand1 = operand else operand2 = operand
    decimalPointIsSet = true

    setOutputToExpression()
  }

  fun requestResult() {
    resetIfCalculationCompleted()

    operand2 = operand2.completeDecimalPoint()

    val safeOperand1 = operand1.ifEmpty { return }
    val safeOperand2 = operand2.ifEmpty { return }
    val safeOperator = operator ?: return

    viewModelScope.launch {
      calculatorState.value = CalculatorState.LOADING
      try {
        result = requestCalculation("$safeOperand1$safeOperator$safeOperand2")
        setOutputToExpression()
        calculatorState.value = CalculatorState.COMPLETED
      } catch (e: Exception) {
        Log.d("MainActivityViewModel", e.message ?: "error")
        calculatorState.value = CalculatorState.ERROR
      }
    }
  }
}

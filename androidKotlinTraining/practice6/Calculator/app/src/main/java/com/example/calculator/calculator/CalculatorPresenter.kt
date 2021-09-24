package com.example.calculator.calculator

import com.example.calculator.history.HistoryManager

class CalculatorPresenter(
  private val calculatorView: CalculatorContract.View,
  private var historyManager: HistoryManager
): CalculatorContract.Presenter {
  companion object {
    const val MAX_RESULT_LENGTH = 12

    val OPERAND_DIGIT_RANGE = '0'..'9'

    val OPERATOR_SYMBOLS = charArrayOf('+', '-', '*', '/')
  }

  override var history: String? = null

  private var decimalPointIsSet = false

  private var operand1: String = ""
  private var operand2: String = ""
  private var operator: Char? = null
  private var result: Double? = null

  init {
    calculatorView.presenter = this
  }

  override fun start() {
    setOutput()
  }

  private fun setOutput() {
    calculatorView.setOutput(history ?: outputExpression())
  }

  private fun outputExpression(): String {
    val equalSign = if (result != null) "=" else ""
    return "$operand1 ${operator ?: ""} $operand2 $equalSign ${formattedResult()}".trim()
  }

  private fun formattedResult(): String{
    if (result == null) return ""

    val formattedResult = "%.${MAX_RESULT_LENGTH}f".format(result).trimEnd('0').trimEnd('.')
    val abbreviatedResult = formattedResult.substring(
                              0, formattedResult.length.coerceAtMost(MAX_RESULT_LENGTH))
    val abbreviationIndicator = if (formattedResult.length > MAX_RESULT_LENGTH) "..." else ""
    return "$abbreviatedResult$abbreviationIndicator"
  }

  private fun beginNewOperandInput() {
    decimalPointIsSet = false
  }

  private fun resetIfCompleted() {
    if (history != null || result != null) reset()
  }

  private fun String.completeDecimalPoint(): String {
    return if (this.lastOrNull() == '.') this + '0' else this
  }

  override fun reset() {
    history = null
    operand1 = ""
    operand2 = ""
    operator = null
    result = null
    beginNewOperandInput()

    setOutput()
  }

  override fun operandInput(digit: Char) {
    resetIfCompleted()
    if (digit !in OPERAND_DIGIT_RANGE) return

    if (operator == null) operand1 += digit else operand2 += digit

    setOutput()
  }

  override fun operatorInput(operatorSymbol: Char) {
    resetIfCompleted()
    if (operatorSymbol !in OPERATOR_SYMBOLS) return

    operand1 = operand1.completeDecimalPoint()

    if (operand1 == "" || operator != null) return
    operator = operatorSymbol
    beginNewOperandInput()

    setOutput()
  }

  override fun decimalPointInput() {
    resetIfCompleted()

    if (decimalPointIsSet) return
    var operand = if (operator == null) operand1 else operand2
    operand = operand.ifEmpty { "0" }
    operand += '.'
    if (operator == null) operand1 = operand else operand2 = operand
    decimalPointIsSet = true

    setOutput()
  }

  override fun requestResult() {
    resetIfCompleted()

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

    setOutput()
    historyManager.addItem(outputExpression())
  }
}

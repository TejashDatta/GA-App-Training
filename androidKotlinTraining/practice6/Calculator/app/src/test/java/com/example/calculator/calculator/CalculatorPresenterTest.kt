package com.example.calculator.calculator

import com.example.calculator.history.HistoryManager
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class CalculatorPresenterTest {
  @Mock private lateinit var calculatorView: CalculatorContract.View

  @Mock private lateinit var historyManager: HistoryManager

  private lateinit var calculatorPresenter: CalculatorPresenter

  @Before fun setupCalculatorPresenter() {
    calculatorPresenter = CalculatorPresenter(calculatorView, historyManager)
  }

  @Test fun createPresenter_setsPresenterToView() {
    verify(calculatorView).presenter = calculatorPresenter
  }

  @Test fun start_setsOutput() {
    calculatorPresenter.start()
    verify(calculatorView).setOutput(anyString())
  }

  @Test fun reset_clearsOutput() {
    calculatorPresenter.reset()
    verify(calculatorView).setOutput("")
  }

  private fun operandInputSingleDigitTest(operand: Char) {
    calculatorPresenter.operandInput(operand)
    verify(calculatorView).setOutput(operand.toString())
  }

  @Test fun operandInput1() = operandInputSingleDigitTest('1')
  @Test fun operandInput2() = operandInputSingleDigitTest('2')
  @Test fun operandInput3() = operandInputSingleDigitTest('3')
  @Test fun operandInput4() = operandInputSingleDigitTest('4')
  @Test fun operandInput5() = operandInputSingleDigitTest('5')
  @Test fun operandInput6() = operandInputSingleDigitTest('6')
  @Test fun operandInput7() = operandInputSingleDigitTest('7')
  @Test fun operandInput8() = operandInputSingleDigitTest('8')
  @Test fun operandInput9() = operandInputSingleDigitTest('9')

  @Test fun operandInputMultipleDigits() {
    calculatorPresenter.operandInput('1')
    calculatorPresenter.operandInput('2')
    verify(calculatorView).setOutput("12")
  }

  private fun operatorInputTest(operator: Char) {
    calculatorPresenter.operandInput('2')

    calculatorPresenter.operatorInput(operator)
    verify(calculatorView).setOutput("2 $operator")
  }

  @Test fun operatorInputPlus() = operatorInputTest('+')
  @Test fun operatorInputMinus() = operatorInputTest('-')
  @Test fun operatorInputAsterisk() = operatorInputTest('*')
  @Test fun operatorInputSlash() = operatorInputTest('/')

  @Test fun decimalPointInput() {
    calculatorPresenter.operandInput('2')

    calculatorPresenter.decimalPointInput()
    verify(calculatorView).setOutput("2.")
  }

  @Test fun decimalPointDoubleInput() {
    calculatorPresenter.operandInput('2')

    calculatorPresenter.decimalPointInput()
    calculatorPresenter.decimalPointInput()
    verify(calculatorView).setOutput("2.")
  }

  private fun runRequestResult(
    operand1: Char, operator: Char, operand2: Char) {

    calculatorPresenter.operandInput(operand1)
    calculatorPresenter.operatorInput(operator)
    calculatorPresenter.operandInput(operand2)

    calculatorPresenter.requestResult()
  }

  private fun requestResultTest(
    operand1: Char, operator: Char, operand2: Char, expectedResult: String) {

    runRequestResult(operand1, operator, operand2)
    verify(calculatorView).setOutput("$operand1 $operator $operand2 = $expectedResult")
  }

  @Test fun requestResultAddition() = requestResultTest('2', '+', '4', "6")
  @Test fun requestResultSubtraction() = requestResultTest('2', '-', '4', "-2")
  @Test fun requestResultMultiplication() = requestResultTest('2', '*', '4', "8")
  @Test fun requestResultDivision() = requestResultTest('2', '/', '4', "0.5")

  @Test fun requestResult_addsItemToHistoryManager() {
    runRequestResult('2', '+', '4')
    verify(historyManager).addItem("2 + 4 = 6")
  }
}

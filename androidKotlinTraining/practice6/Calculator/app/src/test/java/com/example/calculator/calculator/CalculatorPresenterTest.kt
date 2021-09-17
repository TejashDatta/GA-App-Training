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

  @Before fun setupTasksPresenter() {
    calculatorPresenter = CalculatorPresenter(calculatorView, historyManager)
  }

  @Test fun createPresenter_setsThePresenterToView() {
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

  @Test fun operandInput_addsDigitToOutput() {
    calculatorPresenter.operandInput('2')
    verify(calculatorView).setOutput("2")
  }

  @Test fun operatorInput_addsOperatorToOutput() {
    calculatorPresenter.operandInput('2')

    calculatorPresenter.operatorInput('+')
    verify(calculatorView).setOutput("2 +")
  }

  @Test fun decimalPointInput_addsPointToOutput() {
    calculatorPresenter.operandInput('2')

    calculatorPresenter.decimalPointInput()
    verify(calculatorView).setOutput("2.")
  }

  @Test fun requestResult_calculatesAndAddsResultToOutput() {
    calculatorPresenter.operandInput('2')
    calculatorPresenter.operatorInput('+')
    calculatorPresenter.operandInput('4')

    calculatorPresenter.requestResult()
    verify(calculatorView).setOutput("2 + 4 = 6")
  }
}

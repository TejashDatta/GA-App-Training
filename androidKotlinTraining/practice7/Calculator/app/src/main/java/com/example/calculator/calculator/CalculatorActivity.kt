package com.example.calculator.calculator

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.calculator.MockInjection
import com.example.calculator.R
import com.example.calculator.util.replaceFragmentInActivity

class CalculatorActivity : AppCompatActivity() {
  companion object {
    const val CALCULATOR_STATE_KEY = "CALCULATOR_STATE"
  }

  private lateinit var calculatorPresenter: CalculatorPresenter

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.app_act)

    setSupportActionBar(findViewById(R.id.toolbar))

    val calculatorFragment =
      supportFragmentManager.findFragmentById(R.id.contentFrame) as CalculatorFragment?
        ?: CalculatorFragment.newInstance().also {
          replaceFragmentInActivity(R.id.contentFrame, it)
        }

    val calculatorState: CalculatorState? = savedInstanceState?.getParcelable(CALCULATOR_STATE_KEY)

    val historyRepository = MockInjection.provideHistoryRepository(applicationContext)

    calculatorPresenter = CalculatorPresenter(calculatorState, calculatorFragment, historyRepository)
  }

  override fun onSaveInstanceState(outState: Bundle) {
    outState.putParcelable(CALCULATOR_STATE_KEY, calculatorPresenter.getState())

    super.onSaveInstanceState(outState)
  }
}

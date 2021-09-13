package com.example.calculator.calculator

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.calculator.R
import com.example.calculator.util.replaceFragmentInActivity

class CalculatorActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.calculator_act)

    val calculatorFragment =
      supportFragmentManager.findFragmentById(R.id.contentFrame) as CalculatorFragment?
        ?: CalculatorFragment.newInstance().also {
          replaceFragmentInActivity(R.id.contentFrame, it)
        }
  }
}

package com.example.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.calculator.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
  private lateinit var binding: ActivityMainBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

    binding.buttonGrid.adapter = ButtonGridAdapter(
      { operand ->  viewModel.operandInput(operand) },
      { operator ->  viewModel.operatorInput(operator) },
      { viewModel.decimalPointInput() },
      { viewModel.resultRequested() }
    )
  }
}

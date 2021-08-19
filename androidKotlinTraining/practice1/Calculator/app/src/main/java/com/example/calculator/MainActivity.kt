package com.example.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.calculator.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
  private lateinit var binding: ActivityMainBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val viewModel =
      ViewModelProvider(this, MainActivityViewModelFactory()).get(MainActivityViewModel::class.java)

    binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
    binding.lifecycleOwner = this

    binding.buttonGrid.adapter = ButtonGridAdapter(
      operandClickListener = { operand ->  viewModel.operandInput(operand) },
      operatorClickListener = { operator ->  viewModel.operatorInput(operator) },
      decimalPointClickListener = { viewModel.decimalPointInput() },
      resultClickListener = { viewModel.requestResult() }
    )
  }
}

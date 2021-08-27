package com.example.calculator.calculator

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.calculator.CalculatorViewModel

class CalculatorViewModelFactory: ViewModelProvider.Factory {
  override fun <T : ViewModel?> create(modelClass: Class<T>): T {
    if (modelClass.isAssignableFrom(CalculatorViewModel::class.java)) {
      return CalculatorViewModel() as T
    }
    throw IllegalArgumentException("Unknown ViewModel class")
  }
}


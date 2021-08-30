package com.example.calculator.calculator

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.calculator.CalculatorViewModel
import com.example.calculator.history.HistoryManager

class CalculatorViewModelFactory(private val historyManager: HistoryManager): ViewModelProvider.Factory {
  override fun <T : ViewModel?> create(modelClass: Class<T>): T {
    if (modelClass.isAssignableFrom(CalculatorViewModel::class.java)) {
      return CalculatorViewModel(historyManager) as T
    }
    throw IllegalArgumentException("Unknown ViewModel class")
  }
}


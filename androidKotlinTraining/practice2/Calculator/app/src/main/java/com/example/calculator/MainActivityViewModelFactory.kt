package com.example.calculator

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class MainActivityViewModelFactory(): ViewModelProvider.Factory {
  override fun <T : ViewModel?> create(modelClass: Class<T>): T {
    if (modelClass.isAssignableFrom(MainActivityViewModel::class.java)) {
      return MainActivityViewModel() as T
    }
    throw IllegalArgumentException("Unknown ViewModel class")
  }
}


package com.example.calculator.calculator

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.calculator.R

class CalculatorFragment : Fragment() {

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    return inflater.inflate(R.layout.calculator_frag, container, false)
  }

  companion object {
    fun newInstance() = CalculatorFragment()
  }
}

package com.example.calculator.calculator

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.calculator.databinding.CalculatorFragBinding

class CalculatorFragment : Fragment() {
  companion object {
    private const val GRID_COLUMNS = 4

    fun newInstance() = CalculatorFragment()
  }

  private lateinit var binding: CalculatorFragBinding

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
    binding = CalculatorFragBinding.inflate(inflater)

    binding.clearButton.setOnClickListener { testClickHandler() }

    binding.buttonGrid.adapter = ButtonGridAdapter(
      operandClickListener = { operand ->  testClickHandler(operand) },
      operatorClickListener = { operator ->  testClickHandler(operator) },
      decimalPointClickListener = { testClickHandler() },
      resultClickListener = { testClickHandler() }
    )

    binding.buttonGrid.addItemDecoration(
      GridMarginItemDecoration(GRID_COLUMNS, requireActivity().applicationContext))

    return binding.root
  }

  private fun testClickHandler(input: Char? = null) {
    Log.d("CalculatorFragment", input?.toString() ?: "method has no input")
  }
}

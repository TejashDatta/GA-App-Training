package com.example.calculator.calculator

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.calculator.CalculatorViewModel
import com.example.calculator.databinding.FragmentCalculatorBinding

class CalculatorFragment : Fragment() {
  companion object {
    const val GRID_COLUMNS = 4
  }

  private lateinit var binding: FragmentCalculatorBinding

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    val viewModel = ViewModelProvider(this, CalculatorViewModelFactory())
                      .get(CalculatorViewModel::class.java)

    binding = FragmentCalculatorBinding.inflate(inflater)
    binding.lifecycleOwner = viewLifecycleOwner

    binding.viewModel = viewModel

    binding.buttonGrid.adapter = ButtonGridAdapter(
      operandClickListener = { operand ->  viewModel.operandInput(operand) },
      operatorClickListener = { operator ->  viewModel.operatorInput(operator) },
      decimalPointClickListener = { viewModel.decimalPointInput() },
      resultClickListener = { viewModel.requestResult() }
    )

    activity?.let {
      binding.buttonGrid.addItemDecoration(
        GridMarginItemDecoration(GRID_COLUMNS, it.applicationContext))
    }

    return binding.root
  }
}

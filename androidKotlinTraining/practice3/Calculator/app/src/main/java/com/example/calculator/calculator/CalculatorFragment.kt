package com.example.calculator.calculator

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.calculator.CalculatorViewModel
import com.example.calculator.databinding.FragmentCalculatorBinding
import com.example.calculator.history.HistoryManager

class CalculatorFragment : Fragment() {
  companion object {
    const val GRID_COLUMNS = 4
  }

  private lateinit var binding: FragmentCalculatorBinding

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

    val historyManager = HistoryManager(requireActivity().getPreferences(Context.MODE_PRIVATE))

    val viewModel = ViewModelProvider(this, CalculatorViewModelFactory(historyManager))
                      .get(CalculatorViewModel::class.java)

    setOutputFromHistory(viewModel)

    binding = FragmentCalculatorBinding.inflate(inflater)
    binding.lifecycleOwner = viewLifecycleOwner

    binding.viewModel = viewModel

    binding.historyButton.setOnClickListener { view : View ->
      view.findNavController().navigate(
        CalculatorFragmentDirections.actionCalculatorFragmentToHistoryFragment())
    }

    binding.buttonGrid.adapter = ButtonGridAdapter(
      operandClickListener = { operand ->  viewModel.operandInput(operand) },
      operatorClickListener = { operator ->  viewModel.operatorInput(operator) },
      decimalPointClickListener = { viewModel.decimalPointInput() },
      resultClickListener = { viewModel.requestResult() }
    )

    binding.buttonGrid.addItemDecoration(
      GridMarginItemDecoration(GRID_COLUMNS, requireActivity().applicationContext))

    return binding.root
  }

  private fun setOutputFromHistory(viewModel: CalculatorViewModel) {
    if (viewModel.output.value != "") return

    val args = CalculatorFragmentArgs.fromBundle(requireArguments())
    val safeHistory = args.history ?: return

    viewModel.setOutputFromHistory(safeHistory)
  }
}

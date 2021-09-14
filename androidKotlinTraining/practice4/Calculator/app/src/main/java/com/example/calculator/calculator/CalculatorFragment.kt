package com.example.calculator.calculator

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.calculator.R

class CalculatorFragment : Fragment() {
  companion object {
    private const val GRID_COLUMNS = 4

    fun newInstance() = CalculatorFragment()
  }

  private lateinit var clearButton: Button
  private lateinit var buttonGrid: RecyclerView

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
    val root = inflater.inflate(R.layout.calculator_frag, container, false)

    with(root){
      clearButton = findViewById(R.id.clearButton)
      buttonGrid = findViewById(R.id.buttonGrid)
    }

    clearButton.setOnClickListener { testClickHandler() }

    buttonGrid.adapter = ButtonGridAdapter(
      operandClickListener = { operand ->  testClickHandler(operand) },
      operatorClickListener = { operator ->  testClickHandler(operator) },
      decimalPointClickListener = { testClickHandler() },
      resultClickListener = { testClickHandler() }
    )

    buttonGrid.addItemDecoration(
      GridMarginItemDecoration(GRID_COLUMNS, requireActivity().applicationContext))

    return root
  }

  private fun testClickHandler(input: Char? = null) {
    Log.d("CalculatorFragment", input?.toString() ?: "method has no input")
  }
}

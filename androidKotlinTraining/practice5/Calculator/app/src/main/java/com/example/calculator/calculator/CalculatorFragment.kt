package com.example.calculator.calculator

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.calculator.R
import com.example.calculator.history.HistoryActivity

class CalculatorFragment : Fragment(), CalculatorContract.View {
  companion object {
    private const val GRID_COLUMNS = 4

    fun newInstance() = CalculatorFragment()
  }

  override lateinit var presenter: CalculatorContract.Presenter
  private lateinit var historyButton: Button
  private lateinit var clearButton: Button
  private lateinit var resultTextView: TextView
  private lateinit var buttonGrid: RecyclerView

  override fun onResume() {
    super.onResume()
    presenter.start()
  }

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
    val root = inflater.inflate(R.layout.calculator_frag, container, false)

    with(root){
      historyButton = findViewById(R.id.historyButton)
      clearButton = findViewById(R.id.clearButton)
      resultTextView = findViewById(R.id.resultTextView)
      buttonGrid = findViewById(R.id.buttonGrid)
    }

    historyButton.setOnClickListener { showHistory() }

    clearButton.setOnClickListener { presenter.reset() }

    buttonGrid.adapter = ButtonGridAdapter(
      operandClickListener = { operand ->  presenter.operandInput(operand) },
      operatorClickListener = { operator ->  presenter.operatorInput(operator) },
      decimalPointClickListener = { presenter.decimalPointInput() },
      resultClickListener = { presenter.requestResult() }
    )

    buttonGrid.addItemDecoration(
      GridMarginItemDecoration(GRID_COLUMNS, requireActivity().applicationContext))

    return root
  }

  private fun showHistory() {
    startActivity(Intent(context, HistoryActivity::class.java))
  }

  override fun setOutput(output: String) {
    resultTextView.text = output
  }
}

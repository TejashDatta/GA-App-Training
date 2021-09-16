package com.example.calculator.calculator

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContract
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.calculator.R
import com.example.calculator.history.HistoryActivity

class CalculatorFragment : Fragment(), CalculatorContract.View {

  private val getHistory = registerForActivityResult(PickHistory()) { history: String? ->
    presenter.history = history ?: return@registerForActivityResult
  }

  companion object {
    const val ARGUMENT_HISTORY_ID = "HISTORY_ID"

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

    historyButton.setOnClickListener { getHistory.launch(Unit) }

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

  override fun setOutput(output: String) {
    resultTextView.text = output
  }
}

class PickHistory : ActivityResultContract<Unit, String?>() {
  override fun createIntent(context: Context, _input: Unit) =
    Intent(context, HistoryActivity::class.java)

  override fun parseResult(resultCode: Int, result: Intent?): String? {
    if (resultCode != Activity.RESULT_OK) return null

    return result!!.getStringExtra(CalculatorFragment.ARGUMENT_HISTORY_ID)
  }
}

package com.example.calculator.history

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.example.calculator.R
import com.example.calculator.calculator.CalculatorFragment

class HistoryFragment : Fragment(), HistoryContract.View {
  companion object {
    const val RECYCLER_VIEW_STATE_KEY = "RECYCLER_VIEW_STATE"

    fun newInstance() = HistoryFragment()
  }

  override lateinit var presenter: HistoryContract.Presenter
  private lateinit var historyRecyclerView: RecyclerView

  override fun onResume() {
    super.onResume()
    presenter.start()
  }

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
    val root = inflater.inflate(R.layout.history_frag, container, false)

    historyRecyclerView = root.findViewById(R.id.historyRecyclerView)

    historyRecyclerView.apply {
      addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
      layoutManager?.onRestoreInstanceState(savedInstanceState?.getParcelable(RECYCLER_VIEW_STATE_KEY))
    }

    return root
  }

  override fun setRecyclerViewAdapter(historyItems: List<String>) {
    historyRecyclerView.adapter =
      HistoryRecyclerViewAdapter(historyItems) { history -> presenter.onHistorySelected(history) }
  }

  override fun navigateToCalculatorWithHistory(history: String) {
    requireActivity().apply {
      setResult(
        Activity.RESULT_OK,
        Intent().putExtra(CalculatorFragment.ARGUMENT_HISTORY_ID, history)
      )
      finish()
    }
  }

  override fun onSaveInstanceState(outState: Bundle) {
    outState.putParcelable(
      RECYCLER_VIEW_STATE_KEY,
      historyRecyclerView.layoutManager?.onSaveInstanceState()
    )

    super.onSaveInstanceState(outState)
  }
}

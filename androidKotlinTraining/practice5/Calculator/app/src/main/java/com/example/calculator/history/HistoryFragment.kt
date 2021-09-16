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
    fun newInstance() = HistoryFragment()
  }

  override lateinit var presenter: HistoryContract.Presenter
  private lateinit var historyList: RecyclerView

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
    val root = inflater.inflate(R.layout.history_frag, container, false)

    historyList = root.findViewById(R.id.historyList)

    with(historyList) {
      adapter = HistoryListAdapter(presenter.historyItems) { history -> onHistorySelected(history) }
      addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
    }

    return root
  }

  private fun onHistorySelected(history: String) {
    requireActivity().apply {
      setResult(
        Activity.RESULT_OK,
        Intent().putExtra(CalculatorFragment.ARGUMENT_HISTORY_ID, history)
      )
      finish()
    }
  }
}

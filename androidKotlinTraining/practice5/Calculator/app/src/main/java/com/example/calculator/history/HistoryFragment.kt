package com.example.calculator.history

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.example.calculator.R

class HistoryFragment : Fragment(), HistoryContract.View {
  companion object {
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

    historyRecyclerView
      .addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))

    return root
  }

  override fun setRecyclerViewAdapter(historyItems: List<String>) {
    historyRecyclerView.adapter =
      HistoryRecyclerViewAdapter(historyItems) { history -> presenter.onHistorySelected(history) }
  }

  override fun navigateToCalculatorWithHistory(history: String) {
    Log.d("HistoryFragment", history)
  }
}

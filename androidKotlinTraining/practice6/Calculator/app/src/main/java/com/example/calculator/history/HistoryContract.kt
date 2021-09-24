package com.example.calculator.history

import com.example.calculator.BasePresenter
import com.example.calculator.BaseView

class HistoryContract {
  interface View: BaseView<Presenter> {
    override var presenter: Presenter

    fun setRecyclerViewAdapter(historyItems: List<String>)

    fun navigateToCalculatorWithHistory(history: String)
  }

  interface Presenter: BasePresenter {
    override fun start()

    fun onHistorySelected(history: String)
  }
}

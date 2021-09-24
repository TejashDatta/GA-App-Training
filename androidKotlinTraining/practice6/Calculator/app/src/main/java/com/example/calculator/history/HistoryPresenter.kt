package com.example.calculator.history

class HistoryPresenter(
  private val historyView: HistoryContract.View,
  private val historyManager: HistoryManager
): HistoryContract.Presenter {
  init {
    historyView.presenter = this
  }

  override fun start() {
    historyView.setRecyclerViewAdapter(historyManager.items)
  }

  override fun onHistorySelected(history: String) {
    historyView.navigateToCalculatorWithHistory(history)
  }
}

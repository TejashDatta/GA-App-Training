package com.example.calculator.history

import com.example.calculator.data.source.HistoryRepository

class HistoryPresenter(
  private val historyView: HistoryContract.View,
  private val historyRepository: HistoryRepository
): HistoryContract.Presenter {
  init {
    historyView.presenter = this
  }

  override fun start() {
    historyView.setRecyclerViewAdapter(historyRepository.getItems())
  }

  override fun onHistorySelected(history: String) {
    historyView.navigateToCalculatorWithHistory(history)
  }
}

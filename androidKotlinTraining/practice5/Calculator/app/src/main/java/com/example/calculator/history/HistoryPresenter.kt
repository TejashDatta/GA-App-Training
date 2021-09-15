package com.example.calculator.history

class HistoryPresenter(
  private val historyView: HistoryContract.View,
  private var historyManager: HistoryManager
): HistoryContract.Presenter {
  init {
    historyView.presenter = this
  }

  override val historyItems: List<String>
    get() = historyManager.items
}

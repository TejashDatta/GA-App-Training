package com.example.calculator.history

class HistoryContract {
  interface View {
    var presenter: Presenter
  }

  interface Presenter {
    val historyItems: List<String>
  }
}

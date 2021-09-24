package com.example.calculator.history

import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.anyList
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class HistoryPresenterTest {
  @Mock private lateinit var historyView: HistoryContract.View

  @Mock private lateinit var historyManager: HistoryManager

  private lateinit var historyPresenter: HistoryPresenter

  @Before fun setupHistoryPresenter() {
    historyPresenter = HistoryPresenter(historyView, historyManager)
  }

  @Test fun createPresenter_setsPresenterToView() {
    verify(historyView).presenter = historyPresenter
  }

  @Test fun start_setsRecyclerViewInViewWithHistoryItems() {
    historyPresenter.start()

    verify(historyManager).items
    verify(historyView).setRecyclerViewAdapter(anyList())
  }

  @Test fun onHistorySelected_navigatesToCalculatorWithHistoryInView() {
    val history = "2 + 2 = 4"
    historyPresenter.onHistorySelected(history)

    verify(historyView).navigateToCalculatorWithHistory(history)
  }
}

package com.example.calculator.history

import com.example.calculator.data.source.HistoryRepository
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class HistoryPresenterTest {
  @Mock private lateinit var historyView: HistoryContract.View

  @Mock private lateinit var historyRepository: HistoryRepository

  private lateinit var historyPresenter: HistoryPresenter

  @Before fun setupHistoryPresenter() {
    historyPresenter = HistoryPresenter(historyView, historyRepository)
  }

  @Test fun createPresenter_setsPresenterToView() {
    verify(historyView).presenter = historyPresenter
  }

  @Test fun start_setsRecyclerViewInViewWithHistoryItems() {
    historyPresenter.start()

    verify(historyView).setRecyclerViewAdapter(historyRepository.getItems())
  }

  @Test fun onHistorySelected_navigatesToCalculatorWithHistoryInView() {
    val history = "2 + 2 = 4"
    historyPresenter.onHistorySelected(history)

    verify(historyView).navigateToCalculatorWithHistory(history)
  }
}

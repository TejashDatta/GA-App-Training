package com.example.calculator.data.source

import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class HistoryRepositoryTest {
  @Mock private lateinit var historyManager: HistoryManager

  private lateinit var historyRepository: HistoryRepository

  @Before fun setupHistoryRepository() {
    historyRepository = HistoryRepository(historyManager)
  }

  @Test fun getItems_getsItemsFromHistoryManager() {
    historyRepository.getItems()

    verify(historyManager).items
  }

  @Test fun getItems_returnsFromCache() {
    historyRepository.getItems()
    historyRepository.getItems()

    verify(historyManager, times(1)).items
  }

  @Test fun addItem_addsItemInHistoryManager() {
    val exampleExpression = "2 + 2 = 4"
    historyRepository.addItem(exampleExpression)

    verify(historyManager).addItem(exampleExpression)
  }
}

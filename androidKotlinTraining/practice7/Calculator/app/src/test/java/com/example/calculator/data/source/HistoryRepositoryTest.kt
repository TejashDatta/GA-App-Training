package com.example.calculator.data.source

import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class HistoryRepositoryTest {
  @Mock private lateinit var historyManager: HistoryManager

  private lateinit var historyRepository: HistoryRepository

  private val exampleExpression = "2 + 2 = 4"

  @Before fun setupMockAndHistoryRepository() {
    `when`(historyManager.items).thenReturn(listOf(exampleExpression))

    historyRepository = HistoryRepository(historyManager)
  }

  @Test fun getItems_getsItemsFromHistoryManager() {
    val items = historyRepository.getItems()

    verify(historyManager).items
    assertEquals(items, listOf(exampleExpression))
  }

  @Test fun getItems_returnsFromCache() {
    var items = historyRepository.getItems()
    items = historyRepository.getItems()

    verify(historyManager, times(1)).items
    assertEquals(items, listOf(exampleExpression))
  }

  @Test fun addItem_addsItemInHistoryManager() {
    historyRepository.addItem(exampleExpression)

    verify(historyManager).addItem(exampleExpression)
  }
}

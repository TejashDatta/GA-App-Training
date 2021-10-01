package com.example.calculator.history

import android.content.SharedPreferences
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class HistoryManagerTest {
  @Mock private lateinit var sharedPreferences: SharedPreferences
  @Mock private lateinit var sharedPreferencesEditor: SharedPreferences.Editor

  private lateinit var historyManager: HistoryManager

  private val sampleItem = "2 + 2 = 4"

  @Before fun setupMocksAndHistoryManager() {
    `when`(sharedPreferences.edit()).thenReturn(sharedPreferencesEditor)
    `when`(sharedPreferencesEditor.putString(anyString(), anyString())).thenReturn(sharedPreferencesEditor)
    `when`(sharedPreferences.getString(anyString(), any())).thenReturn("[\"$sampleItem\"]")

    historyManager = HistoryManager(sharedPreferences)
  }

  @Test fun loadsItemsFromSharedPreferences() {
    verify(sharedPreferences).getString(anyString(), any())
    assert(historyManager.items[0] == sampleItem)
  }

  @Test fun addItem_addsToList() {
    historyManager.addItem(sampleItem)
    assert(historyManager.items[1] == sampleItem)
  }

  @Test fun addItem_addsToSharedPreferences() {
    historyManager.addItem(sampleItem)
    verify(sharedPreferences).edit()
    verify(sharedPreferencesEditor).putString(anyString(), eq("[\"$sampleItem\",\"$sampleItem\"]"))
  }
}

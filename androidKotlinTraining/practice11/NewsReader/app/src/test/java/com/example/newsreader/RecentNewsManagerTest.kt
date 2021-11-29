package com.example.newsreader

import android.content.SharedPreferences
import com.example.newsreader.data.models.NewsItem
import com.example.newsreader.data.models.RecentNewsItem
import com.example.newsreader.data.source.RecentNewsManager
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner
import org.threeten.bp.ZonedDateTime

@RunWith(MockitoJUnitRunner::class)
class RecentNewsManagerTest {
  @Mock private lateinit var sharedPreferences: SharedPreferences
  @Mock private lateinit var sharedPreferencesEditor: SharedPreferences.Editor

  private lateinit var recentNewsManager: RecentNewsManager

  private var newsItem =
    NewsItem("test", "testUrl", ZonedDateTime.parse("2021-11-16T16:31:29.042+05:30[Asia/Calcutta]"), "testSource")
  private var recentNewsItem =
    RecentNewsItem("test", "testUrl", "testSource")
  private var recentNewsItemListJson =
    "[{\"link\":\"testUrl\",\"source\":\"testSource\",\"title\":\"test\"}]"

  private val ITEMS_KEY = "recent_news"

  @Before fun setupSharedPreferencesMocks() {
    `when`(sharedPreferences.edit()).thenReturn(sharedPreferencesEditor)
    `when`(sharedPreferencesEditor.putString(eq(ITEMS_KEY), anyString())).thenReturn(sharedPreferencesEditor)
  }

  private fun setupEmptyRecentNewsManager() {
    `when`(sharedPreferences.getString(ITEMS_KEY, null)).thenReturn(null)
    recentNewsManager = RecentNewsManager(sharedPreferences)
  }

  private fun setupRecentNewsManagerWithRecentNewsItem() {
    `when`(sharedPreferences.getString(ITEMS_KEY, null)).thenReturn(recentNewsItemListJson)
    recentNewsManager = RecentNewsManager(sharedPreferences)
  }

  @Test fun initLoadsItemsFromSharedPreferences() {
    setupRecentNewsManagerWithRecentNewsItem()

    verify(sharedPreferences).getString(ITEMS_KEY, null)
    assertEquals(recentNewsManager.items[0], recentNewsItem)
  }

  @Test fun add_addsToList() {
    setupEmptyRecentNewsManager()

    recentNewsManager.add(newsItem)
    assertEquals(recentNewsManager.items[0], recentNewsItem)
  }

  @Test fun add_addsToSharedPreferences() {
    setupEmptyRecentNewsManager()

    recentNewsManager.add(newsItem)
    verify(sharedPreferences).edit()
    verify(sharedPreferencesEditor).putString(ITEMS_KEY, recentNewsItemListJson)
  }
}

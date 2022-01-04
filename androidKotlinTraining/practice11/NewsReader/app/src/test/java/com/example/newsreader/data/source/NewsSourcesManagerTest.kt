package com.example.newsreader.data.source

import android.content.SharedPreferences
import com.example.newsreader.data.models.NewsSource
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class NewsSourcesManagerTest {
  @Mock private lateinit var sharedPreferences: SharedPreferences
  @Mock private lateinit var sharedPreferencesEditor: SharedPreferences.Editor

  private lateinit var newsSourcesManager: NewsSourcesManager

  private var newsSource = NewsSource("example", "https://example.com")
  private var newsSourceListJson = "[{\"name\":\"example\",\"url\":\"https://example.com\"}]"

  private val NEWS_SOURCES_KEY = "news_sources"

  @Before fun setupSharedPreferencesMocks() {
    `when`(sharedPreferences.edit()).thenReturn(sharedPreferencesEditor)
    `when`(sharedPreferencesEditor.putString(eq(NEWS_SOURCES_KEY), anyString())).thenReturn(sharedPreferencesEditor)
  }

  private fun setupEmptyNewsSourcesManager() {
    `when`(sharedPreferences.getString(NEWS_SOURCES_KEY, null)).thenReturn(null)
    newsSourcesManager = NewsSourcesManager(sharedPreferences)
  }

  private fun setupNewsSourcesManagerWithNewsSource() {
    `when`(sharedPreferences.getString(NEWS_SOURCES_KEY, null)).thenReturn(newsSourceListJson)
    newsSourcesManager = NewsSourcesManager(sharedPreferences)
  }

  @Test fun initLoadsItemsFromSharedPreferences() {
    setupNewsSourcesManagerWithNewsSource()

    verify(sharedPreferences).getString(NEWS_SOURCES_KEY, null)
    val newsSources = newsSourcesManager.newsSourcesSubject.blockingFirst()
    assertEquals(newsSources[0], newsSource)
  }

  @Test fun add_addsToList() {
    setupEmptyNewsSourcesManager()

    newsSourcesManager.add(newsSource)
    val newsSources = newsSourcesManager.newsSourcesSubject.blockingFirst()
    assertEquals(newsSources[0], newsSource)
  }

  @Test fun add_addsToSharedPreferences() {
    setupEmptyNewsSourcesManager()

    newsSourcesManager.add(newsSource)
    verify(sharedPreferences).edit()
    verify(sharedPreferencesEditor).putString(NEWS_SOURCES_KEY, newsSourceListJson)
  }
}

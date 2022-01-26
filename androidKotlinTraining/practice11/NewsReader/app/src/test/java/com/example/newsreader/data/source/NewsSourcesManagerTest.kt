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

  private var exampleNewsSource = NewsSource("example", "https://example.com")
  private var defaultsWithExampleNewsSourceListJson =
    "[{\"name\":\"すべての記事一覧\",\"url\":\"NA\"}," +
    "{\"name\":\"Google News\",\"url\":\"https://news.google.com/rss?hl=ja&gl=JP&ceid=JP:ja\"}," +
    "{\"name\":\"東洋経済オンライン\",\"url\":\"https://toyokeizai.net/list/feed/rss/\"}," +
    "{\"name\":\"example\",\"url\":\"https://example.com\"}]"

  private val NEWS_SOURCES_KEY = "news_sources"

  @Before fun setupSharedPreferencesMocks() {
    `when`(sharedPreferences.edit()).thenReturn(sharedPreferencesEditor)
    `when`(sharedPreferencesEditor.putString(eq(NEWS_SOURCES_KEY), anyString())).thenReturn(sharedPreferencesEditor)
  }

  private fun setupDefaultNewsSourcesManager() {
    `when`(sharedPreferences.getString(NEWS_SOURCES_KEY, null)).thenReturn(null)
    newsSourcesManager = NewsSourcesManager(sharedPreferences)
  }

  private fun setupNewsSourcesManagerFromNewsSourcesJson() {
    `when`(sharedPreferences.getString(NEWS_SOURCES_KEY, null)).thenReturn(defaultsWithExampleNewsSourceListJson)
    newsSourcesManager = NewsSourcesManager(sharedPreferences)
  }

  @Test fun initLoadsItemsFromSharedPreferences() {
    setupNewsSourcesManagerFromNewsSourcesJson()

    verify(sharedPreferences).getString(NEWS_SOURCES_KEY, null)
    val newsSources = newsSourcesManager.newsSourcesSubject.blockingFirst()
    assertEquals(newsSources.last(), exampleNewsSource)
  }

  @Test fun add_addsToList() {
    setupDefaultNewsSourcesManager()

    newsSourcesManager.add(exampleNewsSource)
    val newsSources = newsSourcesManager.newsSourcesSubject.blockingFirst()
    assertEquals(newsSources.last(), exampleNewsSource)
  }

  @Test fun add_addsToSharedPreferences() {
    setupDefaultNewsSourcesManager()

    newsSourcesManager.add(exampleNewsSource)
    verify(sharedPreferences).edit()
    verify(sharedPreferencesEditor).putString(NEWS_SOURCES_KEY, defaultsWithExampleNewsSourceListJson)
  }
}

package com.example.newsreader.data.source

import android.content.SharedPreferences
import com.example.newsreader.data.models.NewsItem
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner
import org.threeten.bp.ZonedDateTime

@RunWith(MockitoJUnitRunner::class)
class FollowedNewsManagerTest {
  @Mock private lateinit var sharedPreferences: SharedPreferences
  @Mock private lateinit var sharedPreferencesEditor: SharedPreferences.Editor

  private lateinit var followedNewsManager: FollowedNewsManager

  private var newsItem =
    NewsItem("test", "testUrl", ZonedDateTime.parse("2021-11-16T16:31:29.042+05:30[Asia/Calcutta]"), "testSource")
  private var newsItemListJson =
    "[{\"link\":\"testUrl\",\"publishedDate\":\"2021-11-16T16:31:29.042+05:30[Asia/Calcutta]\",\"source\":\"testSource\",\"title\":\"test\"}]"

  private val ITEMS_KEY = "followed_news"

  @Before fun setupSharedPreferencesMocks() {
    `when`(sharedPreferences.edit()).thenReturn(sharedPreferencesEditor)
    `when`(sharedPreferencesEditor.putString(eq(ITEMS_KEY), anyString())).thenReturn(sharedPreferencesEditor)
  }

  private fun setupEmptyFollowedNewsManager() {
    `when`(sharedPreferences.getString(ITEMS_KEY, null)).thenReturn(null)
    followedNewsManager = FollowedNewsManager(sharedPreferences)
  }

  private fun setupFollowedNewsManagerWithNewsItem() {
    `when`(sharedPreferences.getString(ITEMS_KEY, null)).thenReturn(newsItemListJson)
    followedNewsManager = FollowedNewsManager(sharedPreferences)
  }

  @Test fun initLoadsItemsFromSharedPreferences() {
    setupFollowedNewsManagerWithNewsItem()

    verify(sharedPreferences).getString(ITEMS_KEY, null)
    val items = followedNewsManager.itemsSubject.blockingFirst()
    assertEquals(items[0], newsItem)
  }

  @Test fun add_addsToList() {
    setupEmptyFollowedNewsManager()

    followedNewsManager.add(newsItem)
    val items = followedNewsManager.itemsSubject.blockingFirst()
    assertEquals(items[0], newsItem)
  }

  @Test fun add_addsToSharedPreferences() {
    setupEmptyFollowedNewsManager()

    followedNewsManager.add(newsItem)
    verify(sharedPreferences).edit()
    verify(sharedPreferencesEditor).putString(ITEMS_KEY, newsItemListJson)
  }

  @Test fun remove_removesFromList() {
    setupFollowedNewsManagerWithNewsItem()

    followedNewsManager.remove(newsItem)
    val items = followedNewsManager.itemsSubject.blockingFirst()
    assertEquals(items.size, 0)
  }

  @Test fun remove_removesFromSharedPreferences() {
    setupFollowedNewsManagerWithNewsItem()

    followedNewsManager.remove(newsItem)
    verify(sharedPreferences).edit()
    verify(sharedPreferencesEditor).putString(ITEMS_KEY, "[]")
  }
}

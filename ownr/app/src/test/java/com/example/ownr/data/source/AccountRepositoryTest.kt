package com.example.ownr.data.source

import android.content.SharedPreferences
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.eq
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class AccountRepositoryTest {
  private lateinit var accountRepository: AccountRepository

  @Mock private lateinit var sharedPreferences: SharedPreferences
  @Mock private lateinit var sharedPreferencesEditor: SharedPreferences.Editor

  private val EMAIL_KEY = "EMAIL_KEY"
  private val DEFAULT_EMAIL = "user@example.com"
  private val DEFAULT_PASSWORD = "password"


  @Before fun setupSharedPreferencesMocks() {
    `when`(sharedPreferences.edit())
      .thenReturn(sharedPreferencesEditor)
    `when`(sharedPreferencesEditor.putString(eq(EMAIL_KEY), Mockito.anyString()))
      .thenReturn(sharedPreferencesEditor)
  }

  private fun setupAccountRepositoryWithoutCurrentUserEmail() {
    `when`(sharedPreferences.getString(EMAIL_KEY, null)).thenReturn(null)
    accountRepository = AccountRepository(sharedPreferences)
  }

  private fun setupAccountRepositoryWithCurrentUserEmail() {
    `when`(sharedPreferences.getString(EMAIL_KEY, null)).thenReturn(DEFAULT_EMAIL)
    accountRepository = AccountRepository(sharedPreferences)
  }

  @Test fun isLoggedInReturnsFalseWhenCurrentUserEmailsIsNull() {
    setupAccountRepositoryWithoutCurrentUserEmail()
    assertEquals(accountRepository.isLoggedIn().blockingFirst(), false)
  }

  @Test fun isLoggedInReturnsTrueWhenCurrentUserEmailsIsNotNull() {
    setupAccountRepositoryWithCurrentUserEmail()
    assertEquals(accountRepository.isLoggedIn().blockingFirst(), true)
  }

  @Test fun loginWithCorrectCredentialsReturnsCurrentUserEmail() {
    setupAccountRepositoryWithoutCurrentUserEmail()
    assertEquals(accountRepository.login(DEFAULT_EMAIL, DEFAULT_PASSWORD), DEFAULT_EMAIL)
  }

  @Test fun loginWithIncorrectCredentialsReturnsNull() {
    setupAccountRepositoryWithoutCurrentUserEmail()
    assertEquals(accountRepository.login(DEFAULT_EMAIL, ""), null)
  }
}

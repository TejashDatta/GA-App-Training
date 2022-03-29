package com.example.ownr.data.source

import android.content.SharedPreferences

class AccountRepository(private val currentUserSharedPreferences: SharedPreferences) {
  companion object {
    private const val EMAIL_KEY = "EMAIL_KEY"
    private const val DEFAULT_EMAIL = "user@example.com"
    private const val DEFAULT_PASSWORD = "password"
  }

  var currentUserEmail: String? = null

  init {
    loadCurrentUserEmail()
  }

  fun isLoggedIn(): Boolean {
    return currentUserEmail != null
  }

  fun login(email: String, password: String): String? {
    if (email == DEFAULT_EMAIL && password == DEFAULT_PASSWORD) {
      currentUserEmail = email
      saveCurrentUserEmail(email)
    }
    return currentUserEmail
  }

  private fun saveCurrentUserEmail(email: String) {
    currentUserSharedPreferences
      .edit()
      .putString(EMAIL_KEY, email)
      .apply()
  }

  private fun loadCurrentUserEmail() {
    currentUserEmail = currentUserSharedPreferences.getString(EMAIL_KEY, null)
  }
}

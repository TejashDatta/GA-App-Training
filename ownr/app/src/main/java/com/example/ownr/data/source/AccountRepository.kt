package com.example.ownr.data.source

import android.app.Application
import android.content.Context
import android.content.SharedPreferences

object AccountRepositoryFactory: Application() {
  private const val CURRENT_USER_PREFERENCES_KEY = "CURRENT_USER_PREFERENCES"

  private var repository : AccountRepository? = null

  fun getInstance(context: Context) = repository ?: initRepository(context)

  private fun initRepository(context: Context): AccountRepository  {
    return AccountRepository(
      context.getSharedPreferences(CURRENT_USER_PREFERENCES_KEY, Context.MODE_PRIVATE)
    ).also {
      repository = it
    }
  }
}

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

  fun login(email: String, password: String) {
    if (email == DEFAULT_EMAIL && password == DEFAULT_PASSWORD) {
      currentUserEmail = email
      saveCurrentUserEmail(email)
    }
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

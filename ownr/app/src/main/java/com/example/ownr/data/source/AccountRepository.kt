package com.example.ownr.data.source

import android.content.SharedPreferences
import io.reactivex.rxjava3.core.Observable

class AccountRepository(private val currentUserSharedPreferences: SharedPreferences) {
  companion object {
    private const val EMAIL_KEY = "EMAIL_KEY"
    private const val DEFAULT_EMAIL = "user@example.com"
    private const val DEFAULT_PASSWORD = "password"
  }

  var currentUserEmail: String? = null

  init {
    loginWithSavedCurrentUserEmail()
  }

  fun isLoggedIn(): Observable<Boolean> {
    return Observable.just(currentUserEmail != null)
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

  private fun loginWithSavedCurrentUserEmail() {
    currentUserEmail = currentUserSharedPreferences.getString(EMAIL_KEY, null)
  }
}

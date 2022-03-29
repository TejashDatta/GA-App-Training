package com.example.ownr

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.ownr.data.source.AccountRepository
import javax.inject.Inject

// TODO: for testing. remove accountRepository and log later.
class SplashActivity : AppCompatActivity() {
  @Inject lateinit var accountRepository: AccountRepository

  override fun onCreate(savedInstanceState: Bundle?) {
    (applicationContext as OwnrApp).appComponent.inject(this)

    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_splash)

    Log.d("SplashActivity", accountRepository.login("user@example.com", "password").toString())
  }
}

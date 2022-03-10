package com.example.ownr.splash

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.ownr.OwnrApp
import com.example.ownr.R
import com.example.ownr.data.source.AccountRepository
import javax.inject.Inject

// TODO: for testing. remove accountRepository and log later.
class SplashActivity : AppCompatActivity(), SplashContract.View {
  override lateinit var presenter: SplashContract.Presenter
  @Inject lateinit var accountRepository: AccountRepository

  override fun onResume() {
    super.onResume()
    presenter.start()
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    (applicationContext as OwnrApp).appComponent.inject(this)

    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_splash)

    Log.d("SplashActivity", accountRepository.login("user@example.com", "password").toString())

    presenter = SplashPresenter(this, accountRepository)
  }
}

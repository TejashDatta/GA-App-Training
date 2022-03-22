package com.example.ownr.splash

import android.os.Bundle
import com.example.ownr.R
import com.example.ownr.data.source.AccountRepository
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class SplashActivity : DaggerAppCompatActivity(), SplashContract.View {
  @Inject lateinit var presenter: SplashPresenter
  @Inject lateinit var accountRepository: AccountRepository

  override fun onResume() {
    super.onResume()
    presenter.takeView(this)
  }

  override fun onDestroy() {
    super.onDestroy()
    presenter.dropView()
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_splash)
  }
}

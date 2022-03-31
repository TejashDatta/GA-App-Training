package com.example.ownr.splash

import android.content.Intent
import android.os.Bundle
import com.example.ownr.R
import com.example.ownr.data.source.AccountRepository
import com.example.ownr.login.LoginActivity
import com.example.ownr.property_list.PropertyListActivity
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class SplashActivity : DaggerAppCompatActivity(), SplashContract.View {
  @Inject lateinit var presenter: SplashPresenter
  @Inject lateinit var accountRepository: AccountRepository

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_splash)
  }

  override fun onResume() {
    super.onResume()
    presenter.takeView(this)
  }

  override fun onDestroy() {
    super.onDestroy()
    presenter.dropView()
  }

  override fun showLogin() {
    startActivity(Intent(this, LoginActivity::class.java))
  }

  override fun showPropertyList() {
    startActivity(Intent(this, PropertyListActivity::class.java))
  }
}

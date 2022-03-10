package com.example.ownr.splash

import com.example.ownr.data.source.AccountRepository

class SplashPresenter(
  private val view: SplashContract.View,
  private val accountRepository: AccountRepository
): SplashContract.Presenter {
  override fun start() {
  }
}

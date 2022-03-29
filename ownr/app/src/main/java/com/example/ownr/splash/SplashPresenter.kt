package com.example.ownr.splash

import com.example.ownr.data.source.AccountRepository
import io.reactivex.rxjava3.core.Observable
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class SplashPresenter @Inject constructor(
  private val accountRepository: AccountRepository
): SplashContract.Presenter {
  companion object {
    const val SPLASH_DISPLAY_SECONDS = 2
  }

  lateinit var view: SplashContract.View

  override fun takeView(view: SplashContract.View) {
    this.view = view
    Observable.timer(SPLASH_DISPLAY_SECONDS.toLong(), TimeUnit.SECONDS).subscribe {
      changeActivity()
    }
  }

  override fun dropView() {
  }

  private fun changeActivity() {
    accountRepository.isLoggedIn().subscribe {
      if (it == true) view.showPropertyList()
      else view.showLogin()
    }
  }
}

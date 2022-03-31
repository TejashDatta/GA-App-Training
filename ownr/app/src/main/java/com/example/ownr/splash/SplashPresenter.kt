package com.example.ownr.splash

import com.example.ownr.BaseSchedulerProvider
import com.example.ownr.data.source.AccountRepository
import io.reactivex.rxjava3.core.Observable
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class SplashPresenter @Inject constructor(
  private val accountRepository: AccountRepository,
  private val schedulerProvider: BaseSchedulerProvider
): SplashContract.Presenter {
  companion object {
    const val SPLASH_DISPLAY_SECONDS = 2
  }

  var view: SplashContract.View? = null

  override fun takeView(view: SplashContract.View) {
    this.view = view
    Observable.timer(
      SPLASH_DISPLAY_SECONDS.toLong(), TimeUnit.SECONDS, schedulerProvider.computation()
    ).subscribe {
      changeActivity()
    }
  }

  override fun dropView() {
    view = null
  }

  private fun changeActivity() {
    val safeView = view ?: return

    accountRepository.isLoggedIn().subscribe {
      if (it == true) safeView.showPropertyList()
      else safeView.showLogin()
    }
  }
}

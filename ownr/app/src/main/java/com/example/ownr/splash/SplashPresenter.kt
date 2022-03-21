package com.example.ownr.splash

import android.util.Log
import com.example.ownr.data.source.AccountRepository
import com.example.ownr.di.ActivityScope
import javax.inject.Inject

@ActivityScope
class SplashPresenter @Inject constructor(
//  private val accountRepository: AccountRepository
): SplashContract.Presenter {
  override fun takeView(view: SplashContract.View) {
//    TODO: remove log
    Log.d("SplashPresenter", "SplashPresenter active")
  }

  override fun dropView() {
//    TODO("Not yet implemented")
  }
}

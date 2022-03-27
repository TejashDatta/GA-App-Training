package com.example.ownr.login

import com.example.own.login.LoginContract
import com.example.ownr.di.ActivityScope
import javax.inject.Inject

@ActivityScope
class LoginPresenter @Inject constructor(): LoginContract.Presenter {
  override fun takeView(view: LoginContract.View) {
    TODO("Not yet implemented")
  }

  override fun dropView() {
    TODO("Not yet implemented")
  }
}

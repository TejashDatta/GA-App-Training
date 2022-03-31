package com.example.ownr.login

import com.example.ownr.BasePresenter
import com.example.ownr.BaseView

class LoginContract {
  interface View: BaseView<Presenter> {
    fun showPropertyList()
    fun showLoginError(loginError: LoginError)
  }

  interface Presenter: BasePresenter<View> {
    fun login(email: String, password: String)
  }
}

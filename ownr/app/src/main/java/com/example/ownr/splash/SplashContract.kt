package com.example.ownr.splash

import com.example.ownr.BasePresenter
import com.example.ownr.BaseView

class SplashContract {
  interface View: BaseView<Presenter> {
    fun showLogin()
    fun showPropertyList()
  }

  interface Presenter: BasePresenter<View> {

  }
}

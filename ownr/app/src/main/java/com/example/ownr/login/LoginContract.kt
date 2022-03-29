package com.example.own.login

import com.example.ownr.BasePresenter
import com.example.ownr.BaseView

class LoginContract {
  interface View: BaseView<Presenter> {

  }

  interface Presenter: BasePresenter<View> {

  }
}

package com.example.ownr.splash

import com.example.newsreader.BasePresenter
import com.example.newsreader.BaseView


class SplashContract {
  interface View: BaseView<Presenter> {

  }

  interface Presenter: BasePresenter<View> {

  }
}

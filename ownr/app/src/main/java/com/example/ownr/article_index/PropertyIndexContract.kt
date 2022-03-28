package com.example.ownr.article_index

import com.example.ownr.BasePresenter
import com.example.ownr.BaseView

class PropertyIndexContract {
  interface View: BaseView<Presenter> {

  }

  interface Presenter: BasePresenter<View> {

  }
}

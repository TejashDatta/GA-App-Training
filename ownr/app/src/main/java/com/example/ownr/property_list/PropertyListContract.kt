package com.example.ownr.property_list

import com.example.ownr.BasePresenter
import com.example.ownr.BaseView

class PropertyListContract {
  interface View: BaseView<Presenter> {

  }

  interface Presenter: BasePresenter<View> {

  }
}

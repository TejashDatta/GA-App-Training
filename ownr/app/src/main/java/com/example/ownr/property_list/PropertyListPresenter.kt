package com.example.ownr.property_list

import com.example.ownr.di.ActivityScope
import javax.inject.Inject

@ActivityScope
class PropertyListPresenter @Inject constructor(): PropertyListContract.Presenter {
  override fun takeView(view: PropertyListContract.View) {
    TODO("Not yet implemented")
  }

  override fun dropView() {
    TODO("Not yet implemented")
  }
}

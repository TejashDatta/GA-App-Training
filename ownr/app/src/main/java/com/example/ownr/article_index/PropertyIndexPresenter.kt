package com.example.ownr.article_index

import com.example.ownr.di.ActivityScope
import javax.inject.Inject

@ActivityScope
class PropertyIndexPresenter @Inject constructor(): PropertyIndexContract.Presenter {
  override fun takeView(view: PropertyIndexContract.View) {
    TODO("Not yet implemented")
  }

  override fun dropView() {
    TODO("Not yet implemented")
  }
}

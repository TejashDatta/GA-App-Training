package com.example.ownr

import com.example.ownr.di.DaggerApplicationComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

open class OwnrApplication: DaggerApplication() {
  override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
    return DaggerApplicationComponent.factory().create(applicationContext)
  }
}

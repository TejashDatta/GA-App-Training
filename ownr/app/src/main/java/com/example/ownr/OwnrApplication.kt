package com.example.ownr

import android.app.Application
import com.example.ownr.di.ApplicationComponent
import com.example.ownr.di.DaggerAppComponent

class OwnrApplication: Application() {
  lateinit var appComponent: ApplicationComponent

  override fun onCreate() {
    super.onCreate()
    appComponent = DaggerAppComponent.factory().create(applicationContext)
  }
}

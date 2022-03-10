package com.example.ownr

import android.app.Application
import com.example.ownr.di.AppComponent
import com.example.ownr.di.DaggerAppComponent

class OwnrApp: Application() {
  lateinit var appComponent: AppComponent

  override fun onCreate() {
    super.onCreate()
    appComponent = DaggerAppComponent.factory().create(applicationContext)
  }
}

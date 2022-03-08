package com.example.ownr

import android.app.Application
import android.content.Context
import com.example.ownr.di.DaggerAppComponent
import dagger.Component
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

class OwnrApp: Application() {
  override fun onCreate() {
    super.onCreate()
    val appComponent = DaggerAppComponent.factory().create(applicationContext)
  }
}

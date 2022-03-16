package com.example.ownr.di

import android.content.Context
import com.example.ownr.OwnrApplication
import com.example.ownr.splash.SplashActivity
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Singleton
@Component(modules = [AccountRepositoryModule::class])
interface ApplicationComponent: AndroidInjector<OwnrApplication> {
  @Component.Factory
  interface Factory {
    fun create(@BindsInstance applicationContext: Context): ApplicationComponent
  }

  fun inject(activity: SplashActivity)
}


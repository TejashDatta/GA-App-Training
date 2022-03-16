package com.example.ownr.di

import android.content.Context
import com.example.ownr.splash.SplashActivity
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AccountRepositoryModule::class])
interface ApplicationComponent {
  @Component.Factory
  interface Factory {
    fun create(@BindsInstance applicationContext: Context): ApplicationComponent
  }

// TODO: for testing. remove later.
  fun inject(activity: SplashActivity)
}


package com.example.ownr.di

import com.example.ownr.login.LoginActivity
import com.example.ownr.login.LoginModule
import com.example.ownr.splash.SplashActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBindingModule {
  @ActivityScope
  @ContributesAndroidInjector
  abstract fun splashActivity(): SplashActivity

  @ActivityScope
  @ContributesAndroidInjector(modules = [LoginModule::class])
  abstract fun loginActivity(): LoginActivity
}

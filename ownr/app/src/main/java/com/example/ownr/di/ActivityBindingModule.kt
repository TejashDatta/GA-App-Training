package com.example.ownr.di

import com.example.ownr.splash.SplashActivity
import com.example.ownr.splash.SplashModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBindingModule {
  @ActivityScope
  @ContributesAndroidInjector(modules = [SplashModule::class])
  abstract fun splashActivity(): SplashActivity
}


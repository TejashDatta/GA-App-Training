package com.example.ownr.splash

import com.example.ownr.di.ActivityScope
import dagger.Binds
import dagger.Module

@Module
abstract class SplashModule {
  @ActivityScope
  @Binds
  abstract fun splashPresenter(presenter: SplashPresenter): SplashContract.Presenter
}

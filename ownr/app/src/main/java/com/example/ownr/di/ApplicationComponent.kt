package com.example.ownr.di

import android.content.Context
import com.example.ownr.OwnrApplication
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
  modules = [
    AndroidSupportInjectionModule::class,
    ActivityBindingModule::class,
    SchedulerProviderModule::class,
    AccountRepositoryModule::class
  ])
interface ApplicationComponent: AndroidInjector<OwnrApplication> {
  @Component.Factory
  interface Factory {
    fun create(@BindsInstance applicationContext: Context): ApplicationComponent
  }
}

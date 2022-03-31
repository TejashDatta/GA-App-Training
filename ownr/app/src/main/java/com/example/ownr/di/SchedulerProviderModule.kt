package com.example.ownr.di

import com.example.ownr.BaseSchedulerProvider
import com.example.ownr.SchedulerProvider
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class SchedulerProviderModule {
  @Singleton
  @Binds
  abstract fun schedulerProvider(schedulerProvider: SchedulerProvider): BaseSchedulerProvider
}

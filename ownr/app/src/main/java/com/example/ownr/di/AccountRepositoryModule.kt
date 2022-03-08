package com.example.ownr.di

import android.content.Context
import com.example.ownr.data.source.AccountRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AccountRepositoryModule {
  private val CURRENT_USER_PREFERENCES_KEY = "CURRENT_USER_PREFERENCES"

  @Singleton
  @Provides
  fun provideAccountRepository(context: Context): AccountRepository {
    return AccountRepository(
      context.getSharedPreferences(CURRENT_USER_PREFERENCES_KEY, Context.MODE_PRIVATE)
    )
  }
}

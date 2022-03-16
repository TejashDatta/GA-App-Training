package com.example.ownr.di

import android.content.Context
import com.example.ownr.data.source.AccountRepository
import com.example.ownr.data.source.SharedPreferencesKeys
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AccountRepositoryModule {
  @Singleton
  @Provides
  fun provideAccountRepository(context: Context): AccountRepository {
    return AccountRepository(
      context.getSharedPreferences(SharedPreferencesKeys.CURRENT_USER_KEY, Context.MODE_PRIVATE)
    )
  }
}

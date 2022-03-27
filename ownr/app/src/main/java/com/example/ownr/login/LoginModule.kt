package com.example.ownr.login

import com.example.own.login.LoginContract
import com.example.ownr.di.ActivityScope
import com.example.ownr.di.FragmentScope
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class LoginModule {
  @FragmentScope
  @ContributesAndroidInjector
  abstract fun loginFragment(): LoginFragment

  @ActivityScope
  @Binds abstract fun loginPresenter(presenter: LoginPresenter): LoginContract.Presenter
}

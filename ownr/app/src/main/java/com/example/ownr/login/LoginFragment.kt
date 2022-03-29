package com.example.ownr.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.own.login.LoginContract
import com.example.ownr.R
import com.example.ownr.di.ActivityScope
import dagger.android.support.DaggerFragment
import javax.inject.Inject

@ActivityScope
class LoginFragment @Inject constructor(): DaggerFragment(), LoginContract.View {
  @Inject lateinit var presenter: LoginContract.Presenter

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    return inflater.inflate(R.layout.fragment_login, container, false)
  }
}

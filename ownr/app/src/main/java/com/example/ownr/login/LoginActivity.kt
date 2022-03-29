package com.example.ownr.login

import android.os.Bundle
import com.example.ownr.R
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class LoginActivity : DaggerAppCompatActivity() {
  @Inject lateinit var loginFragment: LoginFragment

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_content_frame)

    supportFragmentManager.beginTransaction().apply {
      add(R.id.contentFrame, loginFragment)
    }.commit()
  }
}

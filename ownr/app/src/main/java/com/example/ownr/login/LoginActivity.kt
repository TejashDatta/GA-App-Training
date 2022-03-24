package com.example.ownr.login

import android.os.Bundle
import com.example.ownr.R
import dagger.android.support.DaggerAppCompatActivity

class LoginActivity : DaggerAppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_content_frame)
  }
}

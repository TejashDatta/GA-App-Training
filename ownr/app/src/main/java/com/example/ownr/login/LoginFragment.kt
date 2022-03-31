package com.example.ownr.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.ownr.R
import com.example.ownr.di.ActivityScope
import com.example.ownr.property_list.PropertyListActivity
import dagger.android.support.DaggerFragment
import javax.inject.Inject

@ActivityScope
class LoginFragment @Inject constructor(): DaggerFragment(), LoginContract.View {
  @Inject lateinit var presenter: LoginContract.Presenter

  private lateinit var loginErrorTextView: TextView
  private lateinit var emailEditText: EditText
  private lateinit var passwordEditText: EditText
  private lateinit var loginButton: Button

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    val root = inflater.inflate(R.layout.fragment_login, container, false)

    root.apply {
      loginErrorTextView = findViewById(R.id.loginError)
      emailEditText = findViewById(R.id.emailEditText)
      passwordEditText = findViewById(R.id.passwordEditText)
      loginButton = findViewById(R.id.loginButton)
    }

    loginButton.setOnClickListener { login() }

    return root
  }

  override fun onResume() {
    super.onResume()
    presenter.takeView(this)
  }

  override fun onDestroy() {
    super.onDestroy()
    presenter.dropView()
  }

  override fun showPropertyList() {
    startActivity(Intent(activity, PropertyListActivity::class.java))
  }

  override fun showLoginError(loginError: LoginError) {
    when(loginError) {
      LoginError.AUTHENTICATION_FAILURE ->
        loginErrorTextView.text = getString(R.string.loginAuthenticationFailure)
    }
  }

  fun login() {
    presenter.login(emailEditText.text.toString(), passwordEditText.text.toString())
  }
}

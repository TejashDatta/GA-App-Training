package com.example.ownr.login

import com.example.ownr.data.source.AccountRepository
import com.example.ownr.di.ActivityScope
import javax.inject.Inject

@ActivityScope
class LoginPresenter @Inject constructor(
  val accountRepository: AccountRepository
): LoginContract.Presenter {
  var view: LoginContract.View? = null

  override fun takeView(view: LoginContract.View) {
    this.view = view
  }

  override fun dropView() {
    view = null
  }

  override fun login(email: String, password: String) {
    val safeView = view ?: return

    accountRepository.login(email, password).subscribe {
      if (it.currentUserEmail != null) safeView.showPropertyList()
      else safeView.showLoginError(LoginError.AUTHENTICATION_FAILURE)
    }
  }
}

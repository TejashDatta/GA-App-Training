package com.example.ownr.data.source.login

import com.example.ownr.data.models.LoginResult
import com.example.ownr.data.source.AccountRepository
import com.example.ownr.login.LoginContract
import com.example.ownr.login.LoginError
import com.example.ownr.login.LoginPresenter
import io.reactivex.rxjava3.core.Observable
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class LoginPresenterTest {
  private lateinit var presenter: LoginPresenter

  @Mock private lateinit var accountRepository: AccountRepository
  @Mock private lateinit var view: LoginContract.View

  @Before fun setupPresenter() {
    presenter = LoginPresenter(accountRepository)
    presenter.takeView(view)
  }

  @Test fun loginWithValidCredentialsShowsPropertyList() {
    val validEmail = "user@example.com"
    val validPassword = "password"
    `when`(accountRepository.login(validEmail, validPassword))
      .thenReturn((Observable.just(LoginResult(validEmail))))

    presenter.login(validEmail, validPassword)
    verify(view).showPropertyList()
  }

  @Test fun loginWithInvalidCredentialsShowsLoginError() {
    val invalidEmail = ""
    val invalidPassword = ""
    `when`(accountRepository.login(invalidEmail, invalidPassword))
      .thenReturn((Observable.just(LoginResult(null))))

    presenter.login(invalidEmail, invalidPassword)
    verify(view).showLoginError(LoginError.AUTHENTICATION_FAILURE)
  }
}

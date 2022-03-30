package com.example.ownr.data.source.splash

import com.example.ownr.data.source.AccountRepository
import com.example.ownr.data.source.TestSchedulerProvider
import com.example.ownr.splash.SplashContract
import com.example.ownr.splash.SplashPresenter
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.TestScheduler
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner
import java.util.concurrent.TimeUnit

@RunWith(MockitoJUnitRunner::class)
class SplashPresenterTest {
  private lateinit var splashPresenter: SplashPresenter

  private val testScheduler = TestScheduler()
  private val testSchedulerProvider = TestSchedulerProvider(testScheduler)

  @Mock private lateinit var accountRepository: AccountRepository
  @Mock private lateinit var splashView: SplashContract.View

  @Before fun setupPresenter() {
    splashPresenter = SplashPresenter(accountRepository, testSchedulerProvider)
  }

  @Test fun takeViewChangesActivityToLoginWhenNotLoggedIn() {
    `when`(accountRepository.isLoggedIn()).thenReturn(Observable.just(false))
    splashPresenter.takeView(splashView)
    testScheduler.advanceTimeBy(SplashPresenter.SPLASH_DISPLAY_SECONDS.toLong(), TimeUnit.SECONDS)
    verify(splashView).showLogin()
  }

  @Test fun takeViewChangesActivityToPropertyListWhenLoggedIn() {
    `when`(accountRepository.isLoggedIn()).thenReturn(Observable.just(true))
    splashPresenter.takeView(splashView)
    testScheduler.advanceTimeBy(SplashPresenter.SPLASH_DISPLAY_SECONDS.toLong(), TimeUnit.SECONDS)
    verify(splashView).showPropertyList()
  }
}

package com.example.newsreader

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.schedulers.TestScheduler

interface BaseSchedulerProvider {
  fun ui(): Scheduler
  fun io(): Scheduler
}

class SchedulerProvider : BaseSchedulerProvider {
  override fun ui(): Scheduler = AndroidSchedulers.mainThread()
  override fun io(): Scheduler = Schedulers.io()
}

class TestSchedulerProvider(private val scheduler: TestScheduler) : BaseSchedulerProvider {
  override fun ui() = scheduler
  override fun io() = scheduler
}

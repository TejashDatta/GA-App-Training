package com.example.newsreader

import io.reactivex.rxjava3.schedulers.TestScheduler

class TestSchedulerProvider(private val scheduler: TestScheduler) : BaseSchedulerProvider {
  override fun ui() = scheduler
  override fun io() = scheduler
}

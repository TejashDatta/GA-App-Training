package com.example.ownr.data.source

import com.example.ownr.BaseSchedulerProvider
import io.reactivex.rxjava3.core.Scheduler

class TestSchedulerProvider(private val scheduler: Scheduler): BaseSchedulerProvider {
  override fun computation() = scheduler
}

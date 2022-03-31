package com.example.ownr

import io.reactivex.rxjava3.core.Scheduler

interface BaseSchedulerProvider {
  fun computation(): Scheduler
}

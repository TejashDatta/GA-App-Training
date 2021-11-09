package com.example.newsreader

import io.reactivex.rxjava3.core.Scheduler

interface BaseSchedulerProvider {
  fun ui(): Scheduler
  fun io(): Scheduler
}

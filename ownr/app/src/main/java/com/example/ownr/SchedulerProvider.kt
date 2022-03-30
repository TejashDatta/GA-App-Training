package com.example.ownr

import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class SchedulerProvider @Inject constructor(): BaseSchedulerProvider {
  override fun computation() = Schedulers.computation()
}

package com.example.newsreader

import io.reactivex.rxjava3.schedulers.Schedulers

class TestSchedulerProvider : BaseSchedulerProvider {
  override fun ui() = Schedulers.trampoline()
  override fun io() = Schedulers.trampoline()
}

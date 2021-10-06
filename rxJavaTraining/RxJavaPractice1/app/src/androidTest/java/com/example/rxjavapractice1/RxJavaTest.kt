package com.example.rxjavapractice1

import androidx.test.ext.junit.runners.AndroidJUnit4
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import java.lang.Thread.sleep
import java.util.concurrent.CountDownLatch
import java.util.regex.Pattern.matches

@RunWith(AndroidJUnit4::class)
class RxJavaTest {
  @Test fun test1() {
    var output = ""

    Observable.just(1)
      .map { i ->
        output += "1"
        i
      }
      .subscribe(
        { _i -> output += "2" },
        { _e -> output += "4" },
        { output += "3" }
      )
    output += "5"

    assertEquals(output, "1235")
  }

  @Test fun test2() {
    val latch = CountDownLatch(1)
    var output = ""

    Observable.just(1)
      .map { i ->
        sleep(500)
        output += "1"
        i
      }
      .subscribe(
        { _i -> output += "2" },
        { _e -> output += "4" },
        {
          output += "3"
          latch.countDown()
        }
      )
    output += "5"

    latch.await()

    assertEquals(output, "1235")
  }

  @Test fun test3() {
    val latch = CountDownLatch(1)
    var output = ""

    Observable.just(1)
      .subscribeOn(Schedulers.newThread())
      .map { i ->
        sleep(500)
        output += "1"
        i
      }
      .subscribe(
        { _i -> output += "2" },
        { _e -> output += "4" },
        {
          output += "3"
          latch.countDown()
        }
      )
    output += "5"

    latch.await()

    assertEquals(output, "5123")
  }

  @Test fun test4() {
    val latch = CountDownLatch(1)
    val list = mutableListOf<String>()

    Observable.just(1)
      .subscribeOn(Schedulers.newThread())
      .map { i ->
        list.add("1:" + Thread.currentThread().name)
        i
      }
      .subscribe(
        { _i -> list.add("2:" + Thread.currentThread().name) },
        { _e -> list.add("4:" + Thread.currentThread().name) },
        {
          list.add("3:" + Thread.currentThread().name)
          latch.countDown()
        }
      )

    latch.await()

    assertEquals(list.size, 3)
    assert(matches("1:RxNewThreadScheduler-\\d", list[0]))
    assert(matches("2:RxNewThreadScheduler-\\d", list[1]))
    assert(matches("3:RxNewThreadScheduler-\\d", list[2]))
  }

  @Test fun test5() {
    val latch = CountDownLatch(1)
    val list = mutableListOf<String>()

    Observable.just(1)
      .subscribeOn(Schedulers.newThread())
      .map { i ->
        list.add("1:" + Thread.currentThread().name)
        i
      }
      .observeOn(AndroidSchedulers.mainThread())
      .subscribe(
        { _i -> list.add("2:" + Thread.currentThread().name) },
        { _e -> list.add("4:" + Thread.currentThread().name) },
        {
          list.add("3:" + Thread.currentThread().name)
          latch.countDown()
        }
      )

    latch.await()

    assertEquals(list.size, 3)
    assert(matches("1:RxNewThreadScheduler-\\d", list[0]))
    assertEquals(list[1], "2:main")
    assertEquals(list[2], "3:main")
  }

  @Test fun test6() {
    val latch = CountDownLatch(1)
    val list = mutableListOf<String>()

    Observable.just(1)
      .subscribeOn(AndroidSchedulers.mainThread())
      .map { i ->
        list.add("1:" + Thread.currentThread().name)
        i
      }
      .subscribeOn(Schedulers.newThread())
      .map { i ->
        list.add("2:" + Thread.currentThread().name)
        i
      }
      .observeOn(AndroidSchedulers.mainThread())
      .subscribe(
        { _i -> list.add("3:" + Thread.currentThread().name) },
        { _e -> list.add("5:" + Thread.currentThread().name) },
        {
          list.add("4:" + Thread.currentThread().name)
          latch.countDown()
        }
      )

    latch.await()

    assertEquals(list.size, 4)
    assertEquals(list[0], "1:main")
    assertEquals(list[1], "2:main")
    assertEquals(list[2], "3:main")
    assertEquals(list[3], "4:main")
  }

  @Test fun test7() {
    val latch = CountDownLatch(1)
    val list = mutableListOf<String>()

    Observable.just(1)
      .map { i ->
        list.add("1:" + Thread.currentThread().name)
        i
      }
      .subscribeOn(Schedulers.newThread())
      .map { i ->
        list.add("2:" + Thread.currentThread().name)
        i
      }
      .subscribe(
        { _i -> list.add("3:" + Thread.currentThread().name) },
        { _e -> list.add("5:" + Thread.currentThread().name) },
        {
          list.add("4:" + Thread.currentThread().name)
          latch.countDown()
        }
      )

    latch.await()

    assertEquals(list.size, 4)
    assert(matches("1:RxNewThreadScheduler-\\d", list[0]))
    assert(matches("2:RxNewThreadScheduler-\\d", list[1]))
    assert(matches("3:RxNewThreadScheduler-\\d", list[2]))
    assert(matches("4:RxNewThreadScheduler-\\d", list[3]))
  }
}

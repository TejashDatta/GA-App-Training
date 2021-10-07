package com.example.rxjavapractice1

import io.reactivex.rxjava3.core.Flowable
import org.junit.Test

class FizzBuzzTest {
  companion object {
    const val FIZZ = "Fizz"
    const val FIZZ_DIVISOR = 3
    const val BUZZ = "Buzz"
    const val BUZZ_DIVISOR = 5
    const val UPPER_LIMIT = 100
  }

  @Test fun fizzBuzz() {
    Flowable.range(1, UPPER_LIMIT)
      .map { i ->
        when {
          i % (FIZZ_DIVISOR * BUZZ_DIVISOR) == 0 -> FIZZ + BUZZ
          i % FIZZ_DIVISOR == 0 -> FIZZ
          i % BUZZ_DIVISOR == 0 -> BUZZ
          else -> i.toString()
        }
      }
      .reduce("") { result, item -> if (result == "") item else "$result, $item" }
      .subscribe { v -> println(v) }
  }
}

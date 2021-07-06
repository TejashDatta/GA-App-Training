fun main(args: Array<String>) {
  FizzBuzz(args[0].toInt()).run()
}

class FizzBuzz(private val upperLimit: Int) {
  companion object {
    const val FIZZ = "Fizz"
    const val FIZZ_DIVISOR = 3
    const val BUZZ = "Buzz"
    const val BUZZ_DIVISOR = 5
  }

  fun run() {
    for(i in 1..upperLimit) {
      when {
        i % (FIZZ_DIVISOR * BUZZ_DIVISOR) == 0 -> print(FIZZ + BUZZ)
        i % FIZZ_DIVISOR == 0 -> print(FIZZ)
        i % BUZZ_DIVISOR == 0 -> print(BUZZ)
        else -> print(i)
      }
      if (i != upperLimit) print(", ")
    }
  }
}

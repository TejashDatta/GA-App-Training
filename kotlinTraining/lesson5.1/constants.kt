const val ROCKS = 3

object Constants {
  const val CONSTANT2 = "object constant"
}

val foo = Constants.CONSTANT2

// companion object
class MyClass {
  companion object {
    const val CONSTANT3 = "constant in companion"
  }
}

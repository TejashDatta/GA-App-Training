fun main() {
  val dirtyLevel = 10
  val waterFilter1 = { dirty: Int -> dirty / 2}
  val waterFilter2: (Int) -> Int = { dirty -> dirty / 2}
  println(waterFilter1(dirtyLevel))
  println(waterFilter2(dirtyLevel))

  println(updateDirty(30, waterFilter1))

  fun increaseDirty(dirty: Int) = dirty + 1
  println(updateDirty(30, ::increaseDirty))

  println(updateDirty(30) { dirty: Int -> dirty - 1})
}

fun updateDirty(dirty: Int, operation: (Int) -> Int): Int {
  return operation(dirty)
}

fun main() {
  val colors = listOf("red", "blue", "black", "brown", "white")
  val eagerFiltered = colors.filter { it[0] == 'b' }
  println(eagerFiltered)
  val lazyFiltered = colors.asSequence().filter { it[0] == 'b' }
  println(lazyFiltered)
  println(lazyFiltered.toList())

  lazyMap = colors.asSequence.map {
    println("Access: $it")
    it
  }
  println(lazyMap)
  println(lazyMap.first())
  println(lazyMap.toList())
}

// data class and enum

package example.myapp.decor

data class Decoration(val rocks: String) {
}

data class Decoration2(val rocks: String, val wood: String, val diver: String) {
}

enum class Color(val rgb: Int) {
  RED(0xFF0000), GREEN(0x00FF00), BLUE(0x0000FF)
}

enum class Direction(val degrees: Int) {
  NORTH(0), EAST(90), SOUTH(180), WEST(270)
}

fun makeDecorations() {
  val decoration1 = Decoration("granite")
  println(decoration1)

  val decoration2 = Decoration("slate")
  println(decoration2)

  val decoration3 = Decoration("slate")
  println(decoration3)

  println(decoration1.equals(decoration2))
  println(decoration3.equals(decoration2))

  val d5 = Decoration2("crystal", "wood", "diver")
  println(d5)
  val (rocks, _, diver) = d5
  println(rocks)
  println(diver)
}

fun main() {
  makeDecorations()
  println(Direction.EAST.name)
  println(Direction.EAST.ordinal)
  println(Direction.EAST.degrees)
}

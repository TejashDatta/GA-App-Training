data class Fish(var name: String)

fun main() {
  fishExamples()
}

fun fishExamples() {
  val fish = Fish("splashy")

  with(fish.name) {
    println(capitalize())
  }

  myWith(fish.name) {
    println(capitalize())
  }

  fish.run {
    name
  }

  val fish2 = Fish(name = "splashy").apply {
    name = "sharky"
  }
  println(fish2.name)

  println(
    fish
      .let { it.name.capitalize() }
      .let { it + "fish" }
      .let { it.length }
      .let { it + 31 }
  )
  println(fish)
}

inline fun myWith(name: String, block: String.() -> Unit) {
  name.block()
}


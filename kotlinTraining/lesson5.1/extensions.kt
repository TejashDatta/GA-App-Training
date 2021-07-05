fun String.hasSpaces() = find { it == ' ' } != null

open class AquariumPlant(val color: String, private val size: Int)
fun AquariumPlant.isRed() = color == "red"
// error: cannot access 'size': it is private in 'AquariumPlant'
fun AquariumPlant.isBig() = size > 50

class GreenLeafyPlant(size: Int): AquariumPlant("green", size)
fun AquariumPlant.print() = println("AquariumPlant")
fun GreenLeafyPlant.print() = println("GreenLeafyPlant")

val plant = GreenLeafyPlant(size = 10)
plant.print()
println("\n")

val aquariumPlant: AquariumPlant = plant
aquariumPlant.print()

val AquariumPlant.isGreen: Boolean
  get() = color == "green"

aquariumPlant.isGreen

fun AquariumPlant?.pull() {
  this?.apply {
    println("removing $this")
  }
}

val plant2: AquariumPlant? = null
plant2.pull()

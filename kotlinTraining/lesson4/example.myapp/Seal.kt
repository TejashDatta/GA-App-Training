// sealed class

package example.myapp

sealed class Seal
class SeaLion: Seal()
class Walrus: Seal()

fun matchSeal(seal: Seal): String {
  return when(seal) {
    is Walrus -> "walrus"
    is SeaLion -> "sea lion"
  }
}

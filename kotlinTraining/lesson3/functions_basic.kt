import java.util.*

fun main(args: Array<String>) {
  feedFish()
}

fun feedFish() {
  val day = randomDay()
  val food = fishFood(day)
  println("On $day fish eat $food")
}

fun randomDay(): String {
  val week = arrayOf("Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday")
  return week[Random().nextInt(week.size)]
}

fun fishFood(day: String): String {
  return when (day) {
    "Sunday" -> "fish food"
    "Monday" -> "grains"
    "Tuesday" -> "pellets"
    "Wednesday" -> "weeds"
    "Thursday" -> "grass"
    else -> "nothing"
  }
}

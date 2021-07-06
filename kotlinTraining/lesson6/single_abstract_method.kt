package example2
import javacode.JavaRun

fun main() {
  runExample()
}

fun runExample() {
  val runnable = object: Runnable {
    override fun run() {
      println("I'm a Runnable")
    }
  }
  JavaRun.runNow(runnable)

  JavaRun.runNow({ println("lambda parameter") })

  JavaRun.runNow { println("last parameter lambda") }
}

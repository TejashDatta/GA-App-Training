fun main(args: Array<String>) {
  val isUnit = println("Hello ${args[0]}")
  println(isUnit)
}

// REPLに実行
val number = 5
val positive = if (number > 0) true else false
println(positive)

val message = "Number is ${ if (number > 0) "positive" else "negative"}"
println(message)

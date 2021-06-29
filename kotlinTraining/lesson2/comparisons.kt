// REPLに実行

val number = 7
val bigger = 10
val smaller = 5
if (number < smaller) {
  println("small")
} else if (number > bigger) {
  println("big")
} else {
  println("medium")
}

if (number in smaller..bigger) {
 println("in range")
}
  
when (number) {
  0 -> println("zero")
  in smaller..bigger -> println("medium")
  else -> println("unknown")
}

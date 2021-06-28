// REPLに実行

val peopleList = listOf("John", "James", "Adam")
println(people_list)

val mutablePeopleList = mutableListOf("John", "James", "Adam")
mutablePeopleList.remove("James")

val peopleArray = arrayOf("John", "James", "Adam")
println(java.util.Arrays.toString(peopleArray))

val mixedArray = arrayOf("String example", 10)

val integerArray = intArrayOf(1, 2, 3)
val concatenationArray = intArrayOf(4, 5, 6)
val concatedArray = integerArray + concatenationArray
println(concatedArray[4])

val numbers = Array(10) { it * 3 }
println(java.util.Arrays.toString(numbers))

for (number in numbers) {
  print(number.toString() + " ")
}

for ((index, number) in numbers.withIndex()) {
  print("$index : $number ")
}

for (i in 1..5) print(i)

for (i in 5 downTo 1) print(i)

for (i in 1..5 step 2) print(i)

for (i in 'a'..'f') print(i)

var i = 0
while (i < 10) {
  i++
  println(i)
}

do {
  i--
  println(i)
} while (i > 0)

repeat(3) {
  println("Hello")
}

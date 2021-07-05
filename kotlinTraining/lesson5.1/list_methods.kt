val list = mutableListOf(1, 5, 3, 4)

println(list.sum())

list.add(90)
println(list)
list.remove(5)
println(list)

println(list.reversed())

println(list.contains(3))
println(list.subList(1, 2))

val list2 = listOf("a", "bbb", "cc")
println(list2.sumBy { it.length })

for (s in list2.listIterator()) {
  println("$s ")
}

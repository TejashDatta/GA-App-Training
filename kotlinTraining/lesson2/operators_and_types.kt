// REPLに実行

2 * 2
2 * 4.0

2.times(2)
2.2.plus(3)
2.2.div(2)

val integer: Int = 10

val b1 = integer.toByte()
println(b1)

val b2: Byte = 1

// type mismatch error
val i1: Int = b2
val i2: String = b2

// OK
val i4: Int = b2.toInt()
val i5: String = b2.toString()

val ten_thousand = 10_000

var var1 = 2
var1 = 3

// string interpolation

"Var1 = $var1"
"Var1 + 10 = ${var1 + 10}"

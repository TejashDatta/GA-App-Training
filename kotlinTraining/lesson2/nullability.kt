// REPLに実行

// error:
var notNullable: Int = null

var nullable: Int? = null

nullable?.dec()
nullable = 5
nullable?.dec()
nullable = null
nullable?.dec() ?: 0

nullable!!.dec()

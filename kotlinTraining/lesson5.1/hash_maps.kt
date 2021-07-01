val cures = hashMapOf("white spots" to "Ich", "red sores" to "hole disease")

println(cures.get("white spots"))
println(cures["white spots"])
println(cures["undefined"])

println(cures.getOrDefault("bloating", "undefined"))
println(cures.getOrElse("bloating", { "undefined" }))

val inventory = mutableMapOf("fish net" to 1)
inventory.put("tank scrubber", 3)
println(inventory.toString())
inventory.remove("fish net")
println(inventory.toString())

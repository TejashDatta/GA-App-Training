package generics

fun main() {
  genericsExample()
}

fun genericsExample() {
  val aquariumOld = Aquarium<TapWater>(TapWater())
  val aquarium = Aquarium(TapWater())
  println("water needs processing: ${aquarium.waterSupply.needsProcessing}")
  aquarium.waterSupply.addChemicalCleaners()
  println("water needs processing: ${aquarium.waterSupply.needsProcessing}")

//  val aquarium2 = Aquarium("string")
//  println(aquarium2.waterSupply)

//  val aquarium3 = Aquarium(null)
//  if (aquarium3.waterSupply == null) {
//    println("waterSupply is null")
//  }

  val aquarium4 = Aquarium(TapWater())
  val cleaner = TapWaterCleaner()
  addItemTo(aquarium4)
  aquarium4.addWater(cleaner)
  isWaterClean<TapWater>(aquarium4)
  isWaterClean(aquarium4)
  println(aquarium.hasWaterSupplyOfType<TapWater>())
  println(aquarium.waterSupply.isOfType<TapWater>())
}

open class WaterSupply(var needsProcessing: Boolean)

class TapWater: WaterSupply(true) {
  fun addChemicalCleaners() {
    needsProcessing = false
  }
}

class FishStoreWater: WaterSupply(false)

class LakeWater: WaterSupply(true) {
  fun filter() {
    needsProcessing = false
  }
}

class Aquarium<out T: WaterSupply>(val waterSupply: T) {
  fun addWater(cleaner: Cleaner<T>) {
//    check(!waterSupply.needsProcessing) { "water supply needs processing first" }
    if (waterSupply.needsProcessing) {
      cleaner.clean(waterSupply)
    }
    println("adding water from $waterSupply")
  }
}

interface Cleaner<in T: WaterSupply> {
  fun clean(waterSupply: T)
}

class TapWaterCleaner: Cleaner<TapWater> {
  override fun clean(waterSupply: TapWater) = waterSupply.addChemicalCleaners()
}

fun addItemTo(aquarium: Aquarium<WaterSupply>) = println("item added")

fun <T: WaterSupply> isWaterClean(aquarium: Aquarium<T>) {
  println("aquarium water is clean: ${!aquarium.waterSupply.needsProcessing}")
}

inline fun <reified T: WaterSupply> WaterSupply.isOfType() = this is T

inline fun <reified T: WaterSupply> Aquarium<*>.hasWaterSupplyOfType() = waterSupply is T

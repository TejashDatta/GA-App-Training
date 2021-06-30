// abstract class, interface

package example.myapp

//abstract class AquariumFish: FishAction {
//  abstract val color: String
//  override fun eat() = println("yum")
//}

interface FishAction {
  fun eat()
}

class PrintingFishAction(val food: String): FishAction {
  override fun eat() {
    println(food)
  }
}

interface FishColor {
  val color: String
}

object GoldColor : FishColor {
  override val color = "gold"
}

interface AquariumAction {
  fun eat()
  fun jump()
  fun clean()
  fun catchFish()
  fun swim() {
    println("swim")
  }
}

class Shark: FishAction, FishColor {
  override val color = "gray"
  override fun eat() {
    println("Hunt and eat fish")
  }
}

class Plecostomus(fishColor: FishColor = GoldColor):
  FishAction by PrintingFishAction("Eat algae"),
  FishColor by fishColor

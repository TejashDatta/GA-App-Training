package example.myapp

fun main() {
//  buildAquarium()
  makeFish()
}

fun buildAquarium() {
  val aquarium1 = Aquarium()
  aquarium1.printSize()
  val aquarium2 = Aquarium(width = 25)
  aquarium2.printSize()
  val aquarium3 = Aquarium(length = 10, height = 50)
  aquarium3.printSize()
  val aquarium4 = Aquarium(length = 10, width = 25, height = 50)
  aquarium4.printSize()
  val aquarium6 = Aquarium(numberOfFish = 20)
  aquarium6.printSize()
  aquarium6.volume = 70
  aquarium6.printSize()

  val myTowerTank = TowerTank(25, 45)
  myTowerTank.printSize()
}

fun makeFish() {
  val shark = Shark()
  val plecostomus = Plecostomus()
  println("Shark: ${shark.color}")
  shark.eat()
  println("Plecostomus: ${plecostomus.color}")
  plecostomus.eat()
}

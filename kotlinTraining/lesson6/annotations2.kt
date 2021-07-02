package example2
import kotlin.reflect.full.*

fun main() {
  testAnnotations()
}

annotation class ImAPlant

@Target(AnnotationTarget.PROPERTY_GETTER)
annotation class OnGet

@Target(AnnotationTarget.PROPERTY_SETTER)
annotation class OnSet

@ImAPlant class Plant {
  fun trim(){}
  fun fertilize(){}

  @get:OnGet
  val isGrowing: Boolean = true

  @set:OnSet
  var needsFood: Boolean = false
}

fun testAnnotations() {
  val plantObject = Plant::class
  for (m in plantObject.declaredMemberFunctions) {
    println(m.name)
  }
  for (a in plantObject.annotations) {
    println(a.annotationClass.simpleName)
  }
  
  val myAnnotationObject = plantObject.findAnnotation<ImAPlant>()
  println(myAnnotationObject)
}

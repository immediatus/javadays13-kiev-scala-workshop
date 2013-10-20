package com.immediatus

object implicitPatterns_1 {
  import scala.annotation.implicitNotFound

  @implicitNotFound(msg = "type ${T} is not supported.")
  sealed trait TypeChecker[T]

  private object TypeChecker {
    implicit object CharOk extends TypeChecker[Char]
    implicit object StringOk extends TypeChecker[String]
  }

  class ConstrainedGeneric[T : TypeChecker] {
    def test(x: T) = x match {
      case value: Char => "Char value: %s" format value
      case value: String => "String value: %s" format value
      case _ => "Unposible situation"
    }
  }

  object ConstrainedGeneric {
    import TypeChecker._
    def apply[T: TypeChecker]() = new ConstrainedGeneric[T]()
  }
}


object implicitPatterns_2 {

  object NoArg

  trait DurationRes[A,B,C] {
    def resolve(start: A, end: B, duration: C): (Long, Long, Long)
  }

  object DurationRes {
    implicit object startEndRes extends DurationRes[NoArg.type, Long, Long] {
      def resolve(s: NoArg.type, e: Long, d: Long) = (e - d, e, d)
    }
  }

  def test[A,B,C](start: A = NoArg, end: B = NoArg, duration: C = NoArg)(implicit res: DurationRes[A,B,C]) {
    val (s, e, d) = res.resolve(start, end, duration)
  }
}

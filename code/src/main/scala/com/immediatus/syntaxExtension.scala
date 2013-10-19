package com.immediatus

object syntaxExtension {
  trait ApplyFunction[T1, R] {
    def $(a: T1): R
  }

  trait ApplyFunction2[T1, T2, R] {
    def $(v1: T1): Function[T2, R]
  }

  implicit def toFuncSuntax[T1, R](f: T1 => R) =
    new ApplyFunction[T1, R] {
      def $(a: T1) = f apply a
    }

  implicit def toFunctionSyntax[T1, T2, R](f: (T1, T2) => R) =
    new ApplyFunction2[T1, T2, R] {
      def $(a: T1):Function[T2, R] = f.curried(a)
    }

  val f = (x: Int) => x + 1
  val g = (x: Int, y: Int) => x + y
  val g1 = g $ 1

  println(f $ 10)
  println(g1 $ 10)
}

package com.immediatus

object typeClasses {

  trait Functor[F[_]] { self =>
    def map[A, B](fa: F[A])(f: A => B): F[B]
    def apply[A, B](fa: F[A])(f: A => B): F[B] = map(fa)(f)
  }

  object Functor {
    @inline def apply[F[_]](implicit F: Functor[F]): Functor[F] = F
  }

  trait FunctorSyntax[F[_], A] {
    def self: F[A]
    implicit def F: Functor[F]

    final def map[B](f: A => B): F[B] = F.map(self)(f)
    final def ∘[B](f: A => B): F[B] = F.map(self)(f)

    final def >|[B](b: => B): F[B] = F.map(self)(_ => b)
  }

  trait ToFunctorTypeClass {
    implicit def toFunctorFunctions[F[_],A](v: F[A])(implicit F0: Functor[F]) =
      new FunctorSyntax[F,A] {
        def self = v
        implicit def F: Functor[F] = F0
      }
  }

  trait Maybe[+A] {
    def get: A
  }

  object Maybe {
    def apply[A](a: A): Maybe[A] = if(a == null) Empty else Just(a)
  }

  case class Just[A](a: A) extends Maybe[A] {
    def get = a
  }

  case object Empty extends Maybe[Nothing] {
    def get = throw new Exception
  }

  implicit val maybeFunctorInstance = new Functor[Maybe] {
    override def map[A, B](fa: Maybe[A])(f: A => B) = fa match {
      case Just(x)  => Just(f(x))
      case Empty    => Empty
    }
  }
}

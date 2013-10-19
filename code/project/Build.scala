import sbt._
import Keys._

object Build extends sbt.Build {
  import Dependencies._

  lazy val myProject = Project("experiments", file("."))
    .settings(
      organization  := "com.immediatus",
      version       := "0.1",
      scalaVersion  := "2.10.1",
      scalacOptions := Seq("-deprecation", "-unchecked", "-encoding", "utf8"),
      resolvers     ++= Dependencies.resolutionRepos,
      libraryDependencies ++=
        compile(casbah, scalaz) ++
        test(specs2)
    )
}

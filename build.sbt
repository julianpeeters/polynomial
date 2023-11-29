inThisBuild(List(
  crossScalaVersions := Seq(scalaVersion.value),
  description := "The category of Poly, simply typed.",
  organization := "com.julianpeeters",
  homepage := Some(url("https://github.com/julianpeeters/polynomial")),
  licenses := List("Apache-2.0" -> url("http://www.apache.org/licenses/LICENSE-2.0")),
  developers := List(
    Developer(
      "julianpeeters",
      "Julian Peeters",
      "julianpeeters@gmail.com",
      url("http://github.com/julianpeeters")
    )
  ),
  scalacOptions ++= Seq(
    "-deprecation",
    "-feature",
    "-source:future",
    "-Werror",
    "-Wunused:all",
    "-Wvalue-discard"
  ),
  scalaVersion := "3.3.1",
  versionScheme := Some("semver-spec"),
))

lazy val poly = crossProject(JSPlatform, JVMPlatform, NativePlatform)
  .in(file("modules/poly"))
  .settings(
    name := "polynomial",
    libraryDependencies ++= Seq(
      "org.typelevel" %%% "cats-core" % "2.10.0"
    )
  )
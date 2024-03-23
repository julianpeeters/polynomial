val CatsV = "2.10.0"
val TypenameV = "0.2.0"
val TypesizeV = "0.1.0"

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
    "-feature",
    "-source:future",
    "-Werror",
    "-Wunused:all",
    "-Wvalue-discard",
    "-Ykind-projector:underscores"
  ),
  scalaVersion := "3.4.0",
  versionScheme := Some("semver-spec"),
))

lazy val poly = crossProject(JSPlatform, JVMPlatform, NativePlatform)
  .in(file("modules/poly"))
  .settings(
    name := "polynomial",
    libraryDependencies ++= Seq(
      "org.typelevel" %%% "cats-core" % CatsV,
    )
  )

lazy val mermaid = crossProject(JSPlatform, JVMPlatform, NativePlatform)
  .in(file("modules/mermaid"))
  .settings(
    name := "polynomial-mermaid",
    libraryDependencies ++= Seq(
      "com.julianpeeters" %%% "typename" % TypenameV,
      "com.julianpeeters" %%% "typesize" % TypesizeV
    )
  )
  .dependsOn(poly)

lazy val docs = project.in(file("docs/gitignored"))
  .settings(
    mdocOut := file("."),
    mdocVariables := Map(
      "SCALA" -> crossScalaVersions.value.map(e => e.reverse.dropWhile(_ != '.').drop(1).reverse + "+").mkString(", "),
      "VERSION" -> version.value.takeWhile(_ != '+'),
    )
  )
  .dependsOn(poly.jvm, mermaid.jvm)
  .enablePlugins(MdocPlugin)
  .enablePlugins(NoPublishPlugin)
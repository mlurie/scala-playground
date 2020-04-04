ThisBuild / version      := "0.1.0"
ThisBuild / scalaVersion := "2.12.10"
ThisBuild / organization := "com.example"

val scalaTest = "org.scalatest" %% "scalatest" % "3.0.5"
val gigahorse = "com.eed3si9n" %% "gigahorse-okhttp" % "0.3.1"
val playJson  = "com.typesafe.play" %% "play-json" % "2.6.9"

lazy val commonSettings = Seq(
  libraryDependencies += scalaTest % Test,
)

lazy val hello = (project in file("."))
  .aggregate(helloCore)
  .dependsOn(helloCore)
  .enablePlugins(JavaAppPackaging)
  .settings(
    commonSettings,
    name := "Hello",
  )

lazy val helloCore = (project in file("core"))
  .settings(
    commonSettings,
    name := "Hello Core",
    libraryDependencies ++= Seq(gigahorse, playJson),
  )
version := "0.1"

val commonSettings = Seq(
  scalaVersion := "2.12.7",
  scalacOptions ++= Seq(
    "-deprecation",
    "-feature",
    "-unchecked",
    "-Xfatal-warnings",
    "-Xlint"
  )
)

lazy val root = (project in file("."))
  .settings(commonSettings :_*)
  .settings(
    name := "play-evolutions-test-example",
    description := "Play Framework Evolutions test example",
    libraryDependencies ++= Seq(
      guice,
      jdbc,
      evolutions,
      "mysql"                  % "mysql-connector-java" % "5.1.47",
      "org.scalatestplus.play" %% "scalatestplus-play"  % "3.1.2" % Test,
      "org.scalatest"          %% "scalatest"           % "3.0.5" % Test
    )
  )
  .enablePlugins(PlayScala)

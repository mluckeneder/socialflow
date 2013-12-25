name := "socialflow"

version := "1.0-SNAPSHOT"

libraryDependencies ++= Seq(
  jdbc,
  anorm,
  cache,
  "com.restfb" % "restfb" % "1.6.12"
)

play.Project.playScalaSettings

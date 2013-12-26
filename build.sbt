name := "socialflow"

version := "1.0-SNAPSHOT"

libraryDependencies ++= Seq(
  jdbc,
  anorm,
  cache,
  "com.restfb" % "restfb" % "1.6.12",
  "org.pac4j"  % "play-pac4j_scala" % "1.2.0-SNAPSHOT",
  "org.pac4j"  % "pac4j-oauth"          % "1.5.0-SNAPSHOT",
  "org.twitter4j" % "twitter4j-core" % "3.0.5"
)

resolvers ++= Seq("Local Maven Repository" at "file://"+Path.userHome.absolutePath+"/.m2/repository",
                "Sonatype snapshots repository" at "https://oss.sonatype.org/content/repositories/snapshots/")


play.Project.playScalaSettings

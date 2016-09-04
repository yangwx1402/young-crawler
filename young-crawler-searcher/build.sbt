name := "young-crawler-searcher"

version := "1.0"


libraryDependencies ++= {
  val jackson_version = "1.9.13"
  val elastic4s_version = "2.3.0"
  Seq(
    jdbc,
    anorm,
    cache,
    "org.codehaus.jackson" % "jackson-mapper-asl" % jackson_version,
    "org.codehaus.jackson" % "jackson-core-asl" % jackson_version,
    "org.elasticsearch" % "elasticsearch" % "2.4.0"
   // "com.sksamuel.elastic4s" %% "elastic4s-core" % elastic4s_version
  )
}

play.Project.playScalaSettings

import sbt.Keys._
val project_version = "1.0"
val akka_version = "2.4.8"
val httpClient_version = "4.4.1"
val commons_io_version = "2.4"
val commons_lang_version="3.4"
val scala_version = "2.11.7"
val elastic4s_version = "2.3.0"
val jackson_version="1.9.13"
val codePro = Seq("-unchecked", "-deprecation", "-encoding", "utf8")
val resources = Seq("Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/",
  "Maven Repository" at "http://repo1.maven.org/maven2/",
  "maven-restlet" at "http://maven.restlet.org")
lazy val root = Project("young-crawler",file(".")).settings(publish := {}).settings(publishArtifact := false)
  .settings(
    name := "young-crawler",
    version := project_version,
    scalaVersion := scala_version,
    scalacOptions := codePro
  ).aggregate(core)

lazy val core = Project("young-crawler-core",file("young-crawler-core")).settings(
  name :="young-crawler-core",
  version := project_version,
  scalaVersion := scala_version,
  scalacOptions := codePro,
  resolvers ++= resources,
  libraryDependencies += "com.typesafe.akka" %% "akka-actor" % akka_version,
  libraryDependencies +="org.apache.httpcomponents" % "httpclient" % httpClient_version,
  libraryDependencies +="org.apache.httpcomponents" % "httpmime" % httpClient_version,
  libraryDependencies +="org.apache.httpcomponents" % "httpcore" % httpClient_version,
  libraryDependencies += "commons-io" % "commons-io" % commons_io_version,
  libraryDependencies +="org.apache.commons" % "commons-lang3" % commons_lang_version,
  libraryDependencies += "com.sksamuel.elastic4s" %% "elastic4s-core" % elastic4s_version,
  libraryDependencies += "org.codehaus.jackson" % "jackson-core-asl" % jackson_version,
  libraryDependencies += "org.codehaus.jackson" % "jackson-mapper-asl" % jackson_version
)
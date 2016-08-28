name := "young-crawler"

version := "1.0"

scalaVersion := "2.11.7"

scalacOptions := Seq("-unchecked", "-deprecation", "-encoding", "utf8")

resolvers ++= Seq("Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/",
  "Maven Repository" at "http://repo1.maven.org/maven2/",
  "maven-restlet" at "http://maven.restlet.org")

libraryDependencies ++= {
  val akka_version = "2.4.8"
  val httpClient_version = "4.4.1"
  val commons_io_version = "2.4"
  val commons_lang_version="3.4"
  Seq(
    "com.typesafe.akka" %% "akka-actor" % akka_version,
    "org.apache.httpcomponents" % "httpclient" % httpClient_version,
    "org.apache.httpcomponents" % "httpmime" % httpClient_version,
    "org.apache.httpcomponents" % "httpcore" % httpClient_version,
    "commons-io" % "commons-io" % commons_io_version,
    "org.apache.commons" % "commons-lang3" % commons_lang_version
  )
}
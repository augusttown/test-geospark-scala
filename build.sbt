import sbt.Keys.{libraryDependencies, version}

lazy val root = (project in file(".")).settings(
  name := "test-geospark-scala",
  version := "0.1.0",
  scalaVersion := "2.11.11",
  organization := "local.spark",
  publishMavenStyle := true
)

libraryDependencies ++= Seq(

  "org.scalamock" %% "scalamock" % "4.0.0" % "test",
  "org.scalactic" %% "scalactic" % "3.0.4",
  "org.scalatest" %% "scalatest" % "3.0.4" % "test",

  "org.seleniumhq.selenium" % "selenium-java" % "2.35.0" % "test",

  "org.apache.spark" %% "spark-core" % "2.2.0" % "compile",
  "org.apache.spark" %% "spark-sql" % "2.2.0" % "compile",
  "org.apache.spark" %% "spark-mllib" % "2.2.0",
  "org.apache.spark" %% "spark-streaming" % "2.2.0",

  "org.datasyslab" % "geospark" % "0.9.1",
  //"org.datasyslab" % "geospark-sql" % "1.0.0",
  //"org.datasyslab" % "geospark-viz" % "1.0.0",
  //"org.datasyslab" % "sernetcdf" % "0.1.0"

  //"ch.qos.logback" % "logback-classic" % "1.2.3",
  "net.databinder.dispatch" %% "dispatch-core" % "0.13.3"
)

resolvers += "Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots"
resolvers += "Open Source Geospatial Foundation Repository" at "http://download.osgeo.org/webdav/geotools"
resolvers += "apache-snapshots" at "http://repository.apache.org/snapshots/"
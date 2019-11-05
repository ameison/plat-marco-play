name := """sigeslub-backend"""

version := "1.0-RC"

lazy val GatlingTest = config("gatling") extend Test

lazy val root = (project in file(".")).enablePlugins(PlayJava, PlayEbean, GatlingPlugin).configs(GatlingTest)
  .settings(inConfig(GatlingTest)(Defaults.testSettings): _*)
  .settings(
    scalaSource in GatlingTest := baseDirectory.value / "/gatling/simulation"
  )

scalaVersion := "2.11.11"

libraryDependencies += javaJdbc
libraryDependencies += cache
libraryDependencies += javaWs
libraryDependencies += filters
libraryDependencies += javaJpa


libraryDependencies += "org.postgresql" % "postgresql" % "9.3-1100-jdbc4"
libraryDependencies += "org.mindrot" % "jbcrypt" % "0.3m"
libraryDependencies += "commons-io" % "commons-io" % "2.3"
libraryDependencies += "org.webjars" % "satellizer" % "0.12.5"
libraryDependencies += "com.nimbusds" % "nimbus-jose-jwt" % "3.1.1"
libraryDependencies += "org.mockito" % "mockito-core" % "1.8.5"

libraryDependencies += "com.itextpdf" % "itextpdf" % "5.4.2"
libraryDependencies += "com.itextpdf.tool" % "xmlworker" % "5.4.1"
libraryDependencies += "org.apache.poi" % "poi" % "3.9"

libraryDependencies += "io.gatling.highcharts" % "gatling-charts-highcharts" % "2.2.5" % Test
libraryDependencies += "io.gatling" % "gatling-test-framework" % "2.2.5" % Test



PlayKeys.externalizeResources := false

testOptions in Test := Seq(Tests.Argument(TestFrameworks.JUnit, "-a", "-v"))

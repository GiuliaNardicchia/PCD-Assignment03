
ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.4.2"

val junitJupiterVersion = "5.9.2"
lazy val akkaVersion = "2.8.1"
lazy val akkaGroup = "com.typesafe.akka"
lazy val root = (project in file("."))
  .settings(
    name := "template",
    libraryDependencies ++= Seq(
      // Junit api for cucumber
      "org.junit.jupiter" % "junit-jupiter-api" % junitJupiterVersion % Test,
      "org.junit.jupiter" % "junit-jupiter-engine" % junitJupiterVersion % Test,
      "org.scalatest" %% "scalatest" % "3.2.18" % Test,
      "org.scalatestplus" %% "selenium-4-1" % "3.2.12.1" % Test,
      "io.cucumber" %% "cucumber-scala" % "8.14.1" % Test,
      s"org.scalatestplus" %% "scalacheck-1-15" % "3.2.10.0" % Test,
      //"org.slf4j" % "slf4j-log4j12" % "2.0.13" % Test,

      "org.scala-lang.modules" %% "scala-swing" % "3.0.0",
      akkaGroup %% "akka-actor-typed" % akkaVersion,
      "ch.qos.logback" % "logback-classic" % "1.2.3",
      akkaGroup %% "akka-actor-testkit-typed" % akkaVersion % Test,

      "org.scala-lang" %% "toolkit" % "0.7.0"
    ),
    Test / parallelExecution := false
  )

// Cucumber configuration
// Run by:  sbt> cucumber
enablePlugins(CucumberPlugin)
CucumberPlugin.glues := List("testLecture/code/e3bdd/steps")

//coverageFailOnMinimum := true
//coverageMinimumStmtTotal := 90
//coverageMinimumBranchTotal := 90
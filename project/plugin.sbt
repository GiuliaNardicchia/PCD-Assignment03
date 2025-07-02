
libraryDependencySchemes += "org.scala-lang.modules" %% "scala-xml" % VersionScheme.Always // for compatibility issue with cucumber and sbt-coverage

addSbtPlugin("com.waioeka.sbt" % "cucumber-plugin" % "0.3.1")
addDependencyTreePlugin

addSbtPlugin("net.aichler" % "sbt-jupiter-interface" % "0.9.0")

addSbtPlugin("org.scoverage" % "sbt-scoverage" % "2.0.7")
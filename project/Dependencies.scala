import sbt._

object Dependencies {
    val dependencies = Seq(
        "org.scalatra" % "scalatra" % "2.2.0-RC3" cross CrossVersion.binary,
        "org.scalatra" % "scalatra-json" % "2.2.0-RC3",
        "org.json4s" %% "json4s-jackson" % "3.1.0" exclude("org.scala-lang", "scalap"),
        "ch.qos.logback" % "logback-classic" % "1.0.6" % "runtime",
        "org.eclipse.jetty" % "jetty-webapp" % "8.1.7.v20120910" % "compile;container",
        "org.eclipse.jetty.orbit" % "javax.servlet" % "3.0.0.v201112011016" % "compile;container;provided;test" artifacts (Artifact("javax.servlet", "jar", "jar")))
}

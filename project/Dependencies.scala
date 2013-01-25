import sbt._

object Dependencies {
    val ScalatraVersion = "2.2.0-RC3"

    val dependencies = Seq(
        "org.scalatra" %% "scalatra" % ScalatraVersion withSources(), 
        "org.scalatra" %% "scalatra-json" % ScalatraVersion withSources(), 
        "org.json4s" %% "json4s-jackson" % "3.1.0" withSources(),
        "org.json4s" %% "json4s-mongo" % "3.1.0" withSources(),
        "ch.qos.logback" % "logback-classic" % "1.0.6" % "runtime",
        "org.eclipse.jetty" % "jetty-webapp" % "8.1.7.v20120910" % "compile;container" withSources(),
        "org.eclipse.jetty.orbit" % "javax.servlet" % "3.0.0.v201112011016" % "compile;container;test" artifacts (Artifact("javax.servlet","jar", "jar")) withSources(),
        "org.mongodb" %% "casbah" % "2.5.0",
        "org.scalatest" %% "scalatest" % "1.9.1" withSources(),
        "org.scalatra" %% "scalatra-scalatest" % ScalatraVersion % "test" withSources(),
        "org.mockito" % "mockito-core" % "1.9.5" % "test"
    )
}

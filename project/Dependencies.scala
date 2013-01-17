import sbt._

object Dependencies {
    val ScalatraVersion = "2.2.0-RC3"

    val dependencies = Seq(
        "org.scalatra" %% "scalatra" % ScalatraVersion, 
        "org.scalatra" %% "scalatra-json" % ScalatraVersion, 
        "org.json4s" %% "json4s-jackson" % "3.1.0",
        "ch.qos.logback" % "logback-classic" % "1.0.6" % "runtime",
        "org.eclipse.jetty" % "jetty-webapp" % "8.1.7.v20120910" % "compile;container",
        "org.eclipse.jetty.orbit" % "javax.servlet" % "3.0.0.v201112011016" % "compile;container;test" artifacts (Artifact("javax.servlet", "jar", "jar")),
        "org.scalatest" %% "scalatest" % "1.9.1" ,
        "org.scalatra" %% "scalatra-scalatest" % ScalatraVersion % "test"
    )
}

import sbt._
import Keys._

object ApiBuild extends Build {

  import com.github.siasia.WebPlugin.webSettings
  import Tasks._
  import Dependencies._

  val buildVersion = "LOCAL"

  lazy val project = Project (
    "ambient-api",
    file("."),
    settings = Defaults.defaultSettings ++ webSettings ++ Seq(
      organization := "ambient",
      name := "ambient API",
      version := buildVersion,
      scalaVersion := "2.10.0",
      classpathTypes += "orbit",
      publishMavenStyle := false,
      retrieveManaged := true,
      managedDirectory <<= baseDirectory(_ / "ide" / "lib_managed"),
      retrievePattern := "([classifier]/)[artifact](-[revision])(-[classifier]).[ext]",
      logLevel := Level.Info,
      showTiming := true,
      fork := true,
      testOptions in Test += Tests.Argument("-oDF"),
      resolvers += "Sonatype OSS Snapshots" at "http://oss.sonatype.org/content/repositories/snapshots/",
      libraryDependencies ++= dependencies,
      stageTask,
      bundleTask,
      browseTask
    )
  )
}

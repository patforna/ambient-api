import sbt._
import Keys._

object Tasks {
    
    import com.github.siasia.PluginKeys.port
    import com.github.siasia.WebPlugin.container

    private val stage = TaskKey[File]("stage", "Creates an exploded, runnable app.")    
    private val bundle = TaskKey[File]("bundle", "Creates a deployable artifact.")
    private val browse = TaskKey[Unit]("browse", "open web browser to localhost on container:port") 

    // --- stage task
    def stageTask = stage <<= (crossTarget, fullClasspath in Runtime, sourceDirectory in Compile, packageBin in (Compile, packageBin), streams) map { (target, classpath, sourceDirectory, jarPath, streams) =>
        
      val stageDir = target / "stage"
      IO.delete((stageDir * "*").get)
      IO.createDirectory(stageDir)
      
      val libs = (classpath.files +++ jarPath).get x flat(stageDir / "lib")
      val webapp = (sourceDirectory / "webapp" ** ("*" -- "web.xml")).get x rebase(sourceDirectory, stageDir)
      val scripts = (file("ops") / "scripts" * "*").get x flat(stageDir)

      IO.copy(libs ++ webapp ++ scripts)
      scripts foreach { case (origin, destination) => destination.setExecutable(origin.canExecute) }
      
      streams.log.info("Created runnable app in:" + stageDir.getAbsolutePath)
      stageDir
    }
    
    // --- bundle task
    def bundleTask = bundle <<= (stage, packageBin in Compile, streams) map { (stageDir, jarFile, streams) =>   
      val zipFile = file(jarFile.getAbsolutePath.replace(".jar", ".zip"))
      val files = (stageDir ** "*").get x rebase(stageDir, "")
      IO.zip(files, zipFile)
      streams.log.info("Created deployable artifact in:" + zipFile.getAbsolutePath)
      zipFile
    }
    
    // --- browse tasks
    val browseTask = browse <<= (streams, port in container.Configuration) map { (streams, port) =>
      import streams.log
      val url = new URL("http://localhost:%s" format port)
      try {
        log info "Launching browser."
        java.awt.Desktop.getDesktop.browse(url.toURI)
      }
      catch {
        case _ => {
          log info { "Could not open browser, sorry. Open manually to %s." format url.toExternalForm }
        }
      }
    }
}

import sbt._
import com.typesafe.sbt.osgi.SbtOsgi.OsgiKeys
import org.apache.commons.io.FileUtils
import java.io.File

object AutodeployPlugin extends Plugin {

  lazy val karafDeploy = TaskKey[Unit]("karaf-deploy", "Deploy to Karaf")
  
  override def settings: Seq[Setting[_]] = Seq(
      karafDeploy <<= (OsgiKeys.bundle) map {
        (bundle) =>
          FileUtils.copyFile(bundle, new File("/Users/yuko/bin/apache-karaf-2.3.0/deploy/bundle.jar"))
      }
  )
}
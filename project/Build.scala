import sbt._
import com.typesafe.sbt.osgi.SbtOsgi.{ OsgiKeys, osgiSettings }
import sbtrelease.ReleasePlugin._
import Keys._
import java.io.File

object AskMeBuild extends Build {
  lazy val root = Project(id = "askme",
    base = file("."),
    settings = Project.defaultSettings ++ osgiSettings ++ releaseSettings ++ AutodeployPlugin.settings ++ Seq(
      name := "web",

      organization := "eu.semantiq.asmke",

      scalaVersion := "2.9.1",

      // osgi
      OsgiKeys.privatePackage ++= Seq(
        "eu.semantiq.askme.web",
        "eu.semantiq.askme.web.pages"),

      OsgiKeys.importPackage ++= Seq(
        "org.apache.commons.dbcp",
        "org.springframework.jdbc.datasource",
        "org.springframework.aop",
        "org.springframework.aop.framework",
        "org.aopalliance.aop",
        "org.apache.wicket",
        "org.apache.wicket.event",
        "org.apache.wicket.util",
        "org.apache.wicket.page",
        "org.apache.wicket.request",
        "org.apache.wicket.request.cycle",
        "org.apache.wicket.settings",
        "org.apache.wicket.request.resource",
        "org.apache.wicket.core.request.mapper",
        "org.apache.wicket.request.http",
        "org.apache.wicket.ajax",
        "org.ops4j.pax.wicket.api",
        "org.ops4j.pax.wicket.util.proxy",
        "org.ops4j.pax.wicket.util",
        "net.sf.cglib.proxy;version=\"[2,3)\"",
        "net.sf.cglib.core;version=\"[2,3)\"",
        "net.sf.cglib.reflect;version=\"[2,3)\"",
        "javax.servlet;version=\"2.5.0\"",
        "javax.servlet.http;version=\"2.5.0\"",
        "javax.inject;resolution:=optional",
        "org.scalatest.matchers;resolution:=optional",
        "classycle;resolution:=optional",
        "classycle.graph;resolution:=optional",
        "org.scalatools.testing;resolution:=optional",
        "org.specs2;resolution:=optional",
        "org.specs2.data;resolution:=optional",
        "org.specs2.matcher;resolution:=optional",
        "org.specs2.mutable;resolution:=optional",
        "org.specs2.time;resolution:=optional",
        "org.apache.tools.ant;resolution:=optional",
        "org.apache.tools.ant.taskdefs;resolution:=optional",
        "org.apache.tools.ant.types;resolution:=optional",
        "org.apache.tools.ant.util;resolution:=optional",
        "scala.tools.jline;resolution:=optional",
        "scala.tools.jline.console;resolution:=optional",
        "scala.tools.jline.console.history;resolution:=optional",
        "scala.tools.jline.console.completer;resolution:=optional"),

      OsgiKeys.embeddedJars <<= Keys.externalDependencyClasspath in Compile map {
        deps => 
          val jarNames = Seq("jerkson", "paranamer", "jackson", "lift-json", "salat", "casbah", "mongo", "scalaj", "scalap", "scala-compiler")
          deps filter (d => jarNames exists (jar => d.data.getName startsWith jar)) map (d => d.data)
      },

      // resources

      resourceDirectories in Compile ++= Seq(new File("src/main/scala")),

      // generated resources

      resources in Compile <+= (resourceManaged in Compile, version) map {
        (resources, version) =>
          {
            val file = resources / "application.properties"
            val contents = "application.version=%s\napplication.timestamp=%s".format(
              version, new java.util.Date().toString)
            IO.write(file, contents)
            file
          }
      },

      // publishing

      credentials += Credentials(Path.userHome / ".ivy2" / ".credentials"),

      resolvers += "Nexus" at "http://maven.semantiq.eu/content/groups/public",

      publishTo <<= version(v => {
        println("publishing version: " + v)
        if (v.contains("SNAPSHOT"))
          Some("Sonatype Nexus Repository Manager" at "http://maven.semantiq.eu/content/repositories/snapshots")
        else
          Some("Sonatype Nexus Repository Manager" at "http://maven.semantiq.eu/content/repositories/releases")
      }),

      publishMavenStyle := true,

      libraryDependencies ++= Seq(
        "javax.mail" % "mail" % "1.4.5",
        "javax.servlet" % "servlet-api" % "2.5",
        "org.springframework" % "spring-jdbc" % "3.0.5.RELEASE",
        "joda-time" % "joda-time" % "2.0",
        "org.joda" % "joda-convert" % "1.1",
        "org.slf4j" % "slf4j-log4j12" % "1.6.4",
        "org.springframework" % "spring-test" % "3.0.5.RELEASE" % "test",
        "postgresql" % "postgresql" % "9.0-801.jdbc4",
        "commons-dbcp" % "commons-dbcp" % "1.4",
        "commons-io" % "commons-io" % "2.4",
        "commons-codec" % "commons-codec" % "1.6",
        "org.ops4j.pax.wicket" % "org.ops4j.pax.wicket.service" % "2.0.0" excludeAll (
          ExclusionRule(organization = "org.springframework")),

        "com.novus" %% "salat" % "1.9.1",
        "org.scala-lang" % "scala-compiler" % "2.9.1",

        // test libs
        "org.seleniumhq.selenium" % "selenium-java" % "2.29.1" % "test",
        "org.seleniumhq.selenium" % "selenium-server" % "2.29.1" % "test",
        "junit" % "junit" % "4.8.1" % "test",
        "org.mockito" % "mockito-all" % "1.8.5" % "test",
        "org.scalatest" % "scalatest_2.9.0-1" % "1.6.1" % "test")))
}
name := "beanpiece"

organization := "com.dongjinlee"

version := "0.3-SNAPSHOT"

description := "Java bindings for Google SentencePiece."

autoScalaLibrary := false

crossPaths := false

logBuffered in Test := false

parallelExecution in Test := false

libraryDependencies ++= Seq(
  "junit" % "junit" % "4.12" % "test",
  "org.hamcrest" % "hamcrest-all" % "1.3" % "test"
)

javacOptions ++= Seq("-source", "1.7")

// enable plugins: sbt-jni

enablePlugins(JniPlugin)

// sbt-jni configuration

jniLibraryName := "beanpiece"

jniNativeCompiler := "g++"

import java.util.Locale

val os = System.getProperty("os.name").toLowerCase(Locale.ENGLISH) match {
  case os if os contains "win" => "windows"
  case os if os contains "mac" => "osx"
  case os if (os contains "nix") || (os contains "nux") => "linux"
  case os => os
}

val arch = System.getProperty("os.arch")

lazy val soFileSrc = settingKey[File]("Where the shared object will be copied from")

soFileSrc := {
  baseDirectory.value / "library" / os / arch / ("libsentencepiece." + jniLibSuffix.value)
}

lazy val soFileDst = settingKey[File]("Where the shared object will be copied to")

soFileDst := {
  jniBinPath.value / ("libsentencepiece." + jniLibSuffix.value)
}

jniBinPath := {
  (target in Compile).value / "classes" / os / arch
}

jniIncludes ++= Seq(
  "-I" + jniNativeSources.value.toString
)

jniGccFlags ++= Seq(
  // "-L" + , "-lsentencepiece"
)

jniUseCpp11 := true

jniLibSuffix := (System.getProperty("os.name").toLowerCase(Locale.ENGLISH) match {
  case os if os startsWith "mac" => "dylib"
  case os if os startsWith "darwin" => "dylib"
  case os if os startsWith "win" => "dll"
  case _ => "so"
})

// Temp: skip jniJavah task.
jniJavah := Def.task {
  val log = streams.value.log
  log.info("skipping header generation.")
}

// Temp: as of June 2018, sbt-jni 2.0 does not support compile flags ordering. So, override the implementation.

import scala.sys.process.Process

def checkExitCode(name: String, exitCode: Int): Unit = {
  if (exitCode != 0) {
    throw new MessageOnlyException(
      s"$name exited with non-zero status ($exitCode)"
    )
  }
}

jniCompile := Def.task {
  val log = streams.value.log
  jniBinPath.value.getAbsoluteFile.mkdirs()
  val sources = jniSourceFiles.value.mkString(" ")
  val flags = jniGccFlags.value.mkString(" ")
  val command = s"${jniNativeCompiler.value} $flags -o ${jniBinPath.value}/lib${jniLibraryName.value}.${jniLibSuffix.value} $sources -L${(baseDirectory.value / "library" / os / arch).toString} -lsentencepiece"
  log.info(command)
  checkExitCode(jniNativeCompiler.value, Process(command, jniBinPath.value) ! log)
}.dependsOn(jniJavah)
  .tag(Tags.Compile, Tags.CPU)
  .value

// copy sentencepiece shared object into target directory.
lazy val copySharedObject = taskKey[Unit](s"Copy sentencepiece shared object into target directory")

copySharedObject := {
  println(s"Copying ${soFileSrc.value} into ${soFileDst.value}")
  IO.copyFile(soFileSrc.value, soFileDst.value)
  println("Copying completed.")
}

// dependency: jniCompile -> copySharedObject
copySharedObject := (copySharedObject dependsOn jniCompile).value

// dependency: copySharedObject -> compile
(compile in Compile) := ((compile in Compile) dependsOn copySharedObject).value

// sbt-sonatype configuration

homepage := Some(url("https://github.com/dongjinleekr/beanpiece"))
scmInfo := Some(ScmInfo(url("https://github.com/dongjinleekr/beanpiece"),
  "git@github.com:dongjinleekr/beanpiece.git"))
developers := List(Developer("username",
  "Lee Dongjin",
  "dongjin@apache.org",
  url("https://github.com/dongjinleekr")))
licenses += ("Apache-2.0", url("http://www.apache.org/licenses/LICENSE-2.0"))

publishTo := {
  val nexus = "https://oss.sonatype.org/"
  if (isSnapshot.value)
    Some("snapshots" at nexus + "content/repositories/snapshots")
  else
    Some("releases" at nexus + "service/local/staging/deploy/maven2")
}

publishMavenStyle := true

publishArtifact in Test := false

pomIncludeRepository := { _ => false }

packageOptions in(Compile, packageBin) +=
  Package.ManifestAttributes(new java.util.jar.Attributes.Name("Automatic-Module-Name") -> "com.dongjinlee.beanpiece")

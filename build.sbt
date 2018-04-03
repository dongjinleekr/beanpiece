
name := "beanpiece"

version := "0.2-SNAPSHOT"

scalaVersion := "2.12.4"

enablePlugins(JniPlugin)

autoScalaLibrary := false

crossPaths := false

logBuffered in Test := false

parallelExecution in Test := false

libraryDependencies ++= Seq(
  "junit" % "junit" % "4.12" % "test",
  "org.hamcrest" % "hamcrest-all" % "1.3" % "test"
)

javacOptions ++= Seq("-source", "1.7", "-target", "1.7")

javacOptions in doc := Seq("-source", "1.7")

// sbt-jni configuration
jniLibraryName := "beanpiece"

jniNativeClasses := Seq(
  "com.dongjinlee.beanpiece.Processor"
)

jniLibSuffix := (System.getProperty("os.name").toLowerCase match {
  case os if os startsWith "mac" => "dylib"
  case os if os startsWith "darwin" => "dylib"
  case os if os startsWith "win" => "dll"
  case _ => "so"
})

jniNativeCompiler := "g++"

jniUseCpp11 := true

jniCppExtensions := Seq("cc")

jniGccFlags ++= Seq(
  "-std=c++11", "-fPIC"
)

// compilation on Windows with MSYS/gcc needs extra flags in order
// to produce correct DLLs, also it alway produces position independent
// code so let's remove the flag and silence a warning
jniGccFlags := (
  if (System.getProperty("os.name").toLowerCase startsWith "win")
    jniGccFlags.value.filterNot(_ == "-fPIC") ++
      Seq("-D_JNI_IMPLEMENTATION_", "-Wl,--kill-at", "-static-libgcc", "-static-libstdc++")
  else
    jniGccFlags.value
  )

// Special case the jni platform header on windows (use the one from the repo)
// because the JDK provided one is not compatible with the standard compliant
// compilers but only with VisualStudio - our build uses MSYS/gcc
jniJreIncludes := {
  jniJdkHome.value.fold(Seq.empty[String]) { home =>
    val absHome = home.getAbsolutePath
    if (System.getProperty("os.name").toLowerCase startsWith "win") {
      Seq(s"include").map(file => s"-I$absHome/../$file") ++
        Seq(s"""-I${sourceDirectory.value / "windows" / "include"}""")
    } else {
      val jniPlatformFolder = System.getProperty("os.name").toLowerCase match {
        case os if os.startsWith("mac") => "darwin"
        case os => os
      }
      // in a typical installation, JDK files are one directory above the
      // location of the JRE set in 'java.home'
      Seq(s"include", s"include/$jniPlatformFolder").map(file => s"-I$absHome/../$file")
    }
  }
}

jniIncludes ++= Seq(
  "-I" + jniNativeSources.value.toString + "/include"
)

// Where to put the compiled binaries
jniBinPath := {
  val os = System.getProperty("os.name").toLowerCase.replace(' ', '_') match {
    case os if os startsWith "win" => "win"
    case os if os startsWith "mac" => "darwin"
    case os => os
  }
  val arch = System.getProperty("os.arch")
  (target in Compile).value / "classes" / os / arch
}

// Where to put the generated headers for the JNI lib
jniHeadersPath := (target in Compile).value / "classes" / "include"

// Sonatype

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

organization := "com.dongjinlee"

description := "Java bindings for Google SentencePiece."

packageOptions in(Compile, packageBin) +=
  Package.ManifestAttributes(new java.util.jar.Attributes.Name("Automatic-Module-Name") -> "com.dongjinlee.beanpiece")

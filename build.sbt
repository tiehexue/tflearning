name := "Tensor"

version := "0.1"

scalaVersion := "2.12.4"

javaCppPresetLibs ++= Seq("opencv" -> "3.4.0")

libraryDependencies += "org.tensorflow" % "libtensorflow" % "1.5.0"
// libraryDependencies += "org.bytedeco.javacpp-presets" % "opencv" % "3.4.0-1.4"
// libraryDependencies += "org.bytedeco" % "javacv-platform" % "1.4-macosx-x86_64"
// libraryDependencies += "nu.pattern" % "opencv" % "2.4.9-7"

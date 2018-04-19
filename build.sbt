name := "Tensor"

version := "0.1"

scalaVersion := "2.12.5"

javaCppPresetLibs ++= Seq("opencv" -> "3.4.0")

libraryDependencies += "org.tensorflow"   %  "libtensorflow"   % "1.7.0"
libraryDependencies += "org.bytedeco"     %  "javacv"          % "1.4.1"
// libraryDependencies += "org.bytedeco.javacpp-presets" % "opencv" % "3.4.0-1.4"
// libraryDependencies += "org.bytedeco" % "javacv-platform" % "1.4"
// libraryDependencies += "nu.pattern" % "opencv" % "2.4.9-7"

package zio.craftsmanship.jvm.domain

object JavaDevelopmentKit {
  val jre = JavaRuntimeEnvironment
  val javac = JavaCompiler
  val jdb = JavaDebugger
  val javaApiLibraries = JavaApiLibraries
}

object JavaRuntimeEnvironment {
  val jvm = JavaVirtualMachine
  val javaClassLibrary = JavaClassLibraries
}

object JavaCompiler

object JavaDebugger

object JavaApiLibraries

object JavaClassLibraries

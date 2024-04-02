package zio.craftsmanship.compile.domain

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

object JavaVirtualMachine {
  private val jitCompiler = JustInTimeCompiler

  fun executeJarWithKotlinRuntime(
    javaArchiveFile: JavaArchiveFile,
    kotlinRuntime: KotlinRuntime,
    kotlinStandardLibrary: KotlinStandardLibrary,
  ) {
    // compile the byte code into machine code for use at the moment of need.
    val byteCode = getByteCodeOnDemand(javaArchiveFile)
    val machineCode = jitCompiler.compileIntoMachineCode(byteCode)

    executeMachineCode(machineCode)
  }

  private fun getByteCodeOnDemand(javaArchiveFile: JavaArchiveFile): ByteCode {
    return javaArchiveFile.byteCodeList.first()
  }

  private fun executeMachineCode(machineCode: MachineCode) {
    return
  }
}

object JustInTimeCompiler {
  fun compileIntoMachineCode(byteCode: ByteCode): MachineCode {
    return MachineCode(byteCode.code)
  }
}

object JavaCompiler
object JavaDebugger
object JavaApiLibraries
object JavaClassLibraries

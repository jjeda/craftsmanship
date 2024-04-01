package zio.craftsmanship.compile.domain

object JavaVirtualMachine {
  private val justInTimeCompiler = JustInTimeCompiler

  fun execute(javaArchiveFile: JavaArchiveFile) {
    // compile the byte code into machine code for use at the moment of need.
    val byteCode = getByteCodeOnDemand(javaArchiveFile)
    val machineCode = justInTimeCompiler.compileIntoMachineCode(byteCode)

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
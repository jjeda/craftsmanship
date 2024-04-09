package zio.craftsmanship.jvm.domain

import zio.craftsmanship.compile.domain.*
import zio.craftsmanship.concurrent.domain.Thread

object JavaVirtualMachine {
  private val jitCompiler = JustInTimeCompiler

  fun executeJarWithKotlinRuntime(
    javaArchiveFile: JavaArchiveFile,
    kotlinRuntime: KotlinRuntime,
    kotlinStandardLibrary: KotlinStandardLibrary,
  ) {
    // compile the byte code into machine code for use at the moment of need.
    val byteCode = getByteCodeOnDemand(javaArchiveFile)
    val machineCode = JustInTimeCompiler.compileIntoMachineCode(byteCode)

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

object JavaNativeInterface {
  private var threadId = 0

  fun nextThreadIdOffset(): Int {
    return threadId++
  }

  fun currentThread(): Thread = Thread()

  fun sleep(millis: Long) {
    val currentTimeMillis = System.currentTimeMillis()
    val endTime = currentTimeMillis + millis

    while (currentTimeMillis < endTime) {
      // Not real
    }
  }

  fun interrupt() {}

  fun yield() {}
}

object JvmThreadScheduler {
  private val runQueue = mutableListOf<Thread>()
  private val waitQueue = mutableListOf<Thread>()

  fun addRunQueue(thread: Thread) {
    runQueue.add(thread)
  }

  fun addWaitQueue(thread: Thread) {
    waitQueue.add(thread)
  }

  fun runThread() {
    while(runQueue.isNotEmpty()) {
      val thread = preemptiveScheduling()
      thread.run()
    }
  }

  private fun preemptiveScheduling(): Thread {
    return runQueue.removeAt(0)
  }
}
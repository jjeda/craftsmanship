package zio.craftsmanship.concurrent.usecase

import org.springframework.stereotype.Service
import zio.craftsmanship.concurrent.domain.thread
import zio.craftsmanship.jvm.domain.JvmThreadScheduler

@Service
class ConcurrentExecute() {
  fun execute(command: Command) {
    // val runnable = Runnable { command.concurrentTargetBlock }
    // val thread = Thread(runnable)
    val thread = thread {
      command.concurrentTargetBlock
    }

    thread.start()
    JvmThreadScheduler.addRunQueue(thread)

    // Thread.run()
    JvmThreadScheduler.runThread()

    thread.join()
  }

  data class Command(
    val concurrentTargetBlock: () -> Unit,
  )
}

package zio.craftsmanship.concurrent.domain

import zio.craftsmanship.jvm.domain.JavaNativeInterface


fun <R> synchronized(lock: SharedObject, block: () -> R): R {
  lock.monitor.enter()

  return block().also {
    lock.monitor.exit()
    lock.notifyAll()
  }
}


class SharedObject {
  val monitor = Monitor()

  fun wait() {
    val currentThread = JavaNativeInterface.currentThread()
    check(monitor.ownerThread == currentThread)
    currentThread.toWaitingState()

    monitor.waitingThreads.add(currentThread)
  }
  fun notify() {
    monitor.waitingThreads.getOrNull(0)?.wakeUp()
  }

  fun notifyAll() {
    monitor.waitingThreads.forEach { it.wakeUp() }
  }
}

class Monitor {
  private var locked = false
  var ownerThread: Thread? = null
  val waitingThreads = mutableListOf<Thread>()

  fun enter() {
    val currentThread = JavaNativeInterface.currentThread()

    while (locked || currentThread != waitingThreads.firstOrNull()) {
      if (waitingThreads.contains(currentThread).not()) {
        currentThread.toBlockedState()
        waitingThreads.add(currentThread)
      }
    }
    locked = true
    ownerThread = currentThread
  }

  fun exit() {
    locked = false
    ownerThread = null
  }
}

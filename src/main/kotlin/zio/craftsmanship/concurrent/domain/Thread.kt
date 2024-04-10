package zio.craftsmanship.concurrent.domain

import zio.craftsmanship.jvm.domain.JavaNativeInterface
import zio.craftsmanship.jvm.domain.JvmThreadScheduler

fun interface Runnable {
  fun run()
}

fun thread(block: () -> Unit): Thread {
  return object : Thread() {
    override fun run() {
      block()
    }
  }
}

open class Thread(): Runnable {
  private lateinit var task: Runnable
  private var state = State.NEW
  private val threadId = JavaNativeInterface.nextThreadIdOffset()

  constructor(target: Runnable) : this() {
    task = target
  }

  fun start() {
    check(state == State.NEW)
    state = State.RUNNABLE

    // added JVM run queue
  }

  // executed by JVM scheduler
  override fun run() {
    check(::task.isInitialized)
    task.run()
    state = State.TERMINATED
  }

  fun join() {
    val currentThread = JavaNativeInterface.currentThread()
    currentThread.toWaitingState()
    JvmThreadScheduler.addWaitQueue(currentThread)

    // Wait for the target thread to terminate
    while (isAlive()) {
      // TODO: wait()
    }

    currentThread.wakeUp()
  }

  fun sleep(millis: Long) {
    check(state == State.RUNNABLE)
    state = State.BLOCKED

    JvmThreadScheduler.addWaitQueue(this)
    JavaNativeInterface.sleep(millis)
  }

  fun interrupt() {
    check(state == State.BLOCKED)
    state = State.RUNNABLE

    JavaNativeInterface.interrupt()
  }

  fun yield() {
    check(state == State.RUNNABLE)
    // do not change state

    JavaNativeInterface.yield()
  }

  fun wakeUp() {
    check(state == State.WAITING)
    state = State.RUNNABLE
  }

  fun toWaitingState() {
    check(state == State.RUNNABLE)
    state = State.WAITING
  }

  fun toBlockedState() {
    check(state == State.RUNNABLE)
    state = State.BLOCKED
  }

  private fun isAlive(): Boolean {
    return state != State.TERMINATED
  }

  enum class State {
    NEW, RUNNABLE, BLOCKED, WAITING, TIMED_WAITING, TERMINATED
  }
}

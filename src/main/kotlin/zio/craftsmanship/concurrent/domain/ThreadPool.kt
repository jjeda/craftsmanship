package zio.craftsmanship.concurrent.domain

interface Callable<T>

interface Executor {
  // fun execute(command: Runnable)
}

interface ExecutorService : Executor {
  // fun <T> submit(task: Callable<T>): Future<T>

  // fun <T> invoke(tasks: List<Callable<T>>): List<Future<T>>
}

interface ThreadFactory {
  fun newThread(runnable: Runnable): Thread
}

class ThreadPoolExecutor() : ExecutorService

class ForkJoinPool() : ExecutorService

class AutoShutdownDelegatedExecutorService() : ExecutorService

object Executors {
  fun newFixedThreadPool(threadsCount: Int): ExecutorService {
    return ThreadPoolExecutor()
  }

  fun newCachedThreadPool(): ExecutorService {
    return ThreadPoolExecutor()
  }

  fun newWorkStealingPool(): ExecutorService {
    return ForkJoinPool()
  }

  fun newSingleThreadExecutor(threadFactory: ThreadFactory): ExecutorService {
    return AutoShutdownDelegatedExecutorService()
  }
}
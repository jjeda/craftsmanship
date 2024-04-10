package zio.craftsmanship.concurrent.usecase

import org.springframework.stereotype.Service
import zio.craftsmanship.concurrent.domain.SharedObject
import zio.craftsmanship.concurrent.domain.synchronized

@Service
class SynchronizeAccess {

  fun synchronize(command: Command) {
    synchronized(command.lock) {
      command.block()
    }
  }

  data class Command(
    val lock: SharedObject,
    val block: () -> Any
  )
}

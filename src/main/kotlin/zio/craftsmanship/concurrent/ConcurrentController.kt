package zio.craftsmanship.concurrent

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import zio.craftsmanship.concurrent.domain.SharedObject
import zio.craftsmanship.concurrent.usecase.ConcurrentExecute
import zio.craftsmanship.concurrent.usecase.SynchronizeAccess

@Controller
@RequestMapping("/concurrent")
class ConcurrentController(
  private val concurrentExecuteUseCase: ConcurrentExecute,
  private val synchronizeAccessUseCase: SynchronizeAccess,
) {
  @PostMapping
  fun executeConcurrently(
    target: () -> Unit,
  ) {
    val command = ConcurrentExecute.Command(target)

    concurrentExecuteUseCase.execute(command)
  }

  @PostMapping("/synchronize")
  fun synchronize(
    lockObject: SharedObject,
    target: () -> Any
  ) {
    val command = SynchronizeAccess.Command(lockObject, target)

    synchronizeAccessUseCase.synchronize(command)
  }
}

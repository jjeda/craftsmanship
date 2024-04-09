package zio.craftsmanship.concurrent

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import zio.craftsmanship.concurrent.usecase.ConcurrentExecute

@Controller
@RequestMapping("/concurrent")
class ConcurrentController(
  private val concurrentExecuteUseCase: ConcurrentExecute,
) {
  @PostMapping
  fun executeConcurrently(
    concurrentTarget: () -> Unit,
  ) {
    val command = ConcurrentExecute.Command(concurrentTarget)

    concurrentExecuteUseCase.execute(command)
  }
}

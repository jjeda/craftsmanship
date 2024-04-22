package zio.craftsmanship.spring

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import zio.craftsmanship.spring.domain.HttpRequest
import zio.craftsmanship.spring.domain.HttpResponse
import zio.craftsmanship.spring.usecase.DispatchHttpRequest


@Controller
@RequestMapping("/spring")
class SpringMvcController(
  private val dispatchHttpRequestUseCase: DispatchHttpRequest
) {
  @PostMapping("/mvc")
  fun dispatchMessage(request: HttpRequest): HttpResponse {
    return dispatchHttpRequestUseCase.dispatch(DispatchHttpRequest.Command(request))
  }
}

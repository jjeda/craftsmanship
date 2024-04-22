package zio.craftsmanship.spring.usecase

import org.springframework.stereotype.Service
import zio.craftsmanship.spring.domain.HttpRequest
import zio.craftsmanship.spring.domain.HttpResponse
import zio.craftsmanship.spring.domain.ServletResponse
import zio.craftsmanship.spring.domain.Tomcat

@Service
class DispatchHttpRequest {

  fun dispatch(command: Command): HttpResponse {
    val servletContainer = Tomcat()
    val servletRequest = command.request.toServletRequest()
    val servletResponse = ServletResponse()

    servletContainer.invoke(servletRequest, servletResponse)

    return HttpResponse.of(servletResponse)
  }

  data class Command(
    val request: HttpRequest,
  )
}

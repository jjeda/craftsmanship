package zio.craftsmanship.spring.domain

interface ServletContainer {
  fun invoke(request: ServletRequest, response: ServletResponse)
}

class Tomcat : ServletContainer {
  private val filterChain = ApplicationFilterChain()

  override fun invoke(request: ServletRequest, response: ServletResponse) {
    filterChain.doFilter(request, response)
  }
}

class ApplicationFilterChain {
  private val servlet = DispatcherServlet()
  fun doFilter(request: ServletRequest, response: ServletResponse) {
    val filteredRequest = filterRequest(request)

    servlet.doService(filteredRequest, response)
  }

  private fun filterRequest(request: ServletRequest): ServletRequest {
    return request
  }
}

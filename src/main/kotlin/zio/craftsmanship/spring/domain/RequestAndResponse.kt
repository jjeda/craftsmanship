package zio.craftsmanship.spring.domain

data class HttpRequest(
  val message: String
) {
  fun toServletRequest() = ServletRequest("message")
}

data class HttpResponse(
  val message: String?
) {
  companion object {
    fun of(servletResponse: ServletResponse) = HttpResponse(servletResponse.jsonMessage)
  }
}

data class ServletRequest(
  val message: String
)

data class ServletResponse(
  var jsonMessage: String? = null,
)

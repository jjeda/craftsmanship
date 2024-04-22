package zio.craftsmanship.spring.domain

class DispatcherServlet {
  private val handlerMappings: List<HandlerMapping> = listOf()
  private val handlerAdapters: List<HandlerAdapter> = listOf()

  fun doService(request: ServletRequest, response: ServletResponse) {
    doDispatch(request, response)
  }

  private fun doDispatch(request: ServletRequest, response: ServletResponse) {
    val handlerExecutionChain = handlerMappings.firstNotNullOf { it.getHandlerExecutionChain(request) }
    val controller = handlerExecutionChain.getHandler()
    val handlerAdapter = handlerAdapters.first { it.supports(controller) }

    handlerExecutionChain.applyPreHandler(request, response)
    val modelAndView = handlerAdapter.handle(request, response, controller)
    handlerExecutionChain.applyPostHandler(request, response)

    render(modelAndView, request, response)
  }

  private fun render(modelAndView: ModelAndView, request: ServletRequest, response: ServletResponse) {
    response.jsonMessage = modelAndView.modelToJson()
  }
}

interface HandlerMapping {
  fun getHandlerExecutionChain(request: ServletRequest): HandlerExecutionChain?
}

interface HandlerAdapter {
  fun supports(handler: Any?): Boolean

  fun handle(request: ServletRequest, response: ServletResponse, handler: Any?): ModelAndView
}

class HandlerExecutionChain {
  private val interceptorList: List<HandlerInterceptor> = listOf()

  fun getHandler(): Any? {
    return null
  }

  fun applyPreHandler(request: ServletRequest, response: ServletResponse): Boolean {
    interceptorList.forEach {
      it.preHandler(request, response)
    }
    return true
  }

  fun applyPostHandler(request: ServletRequest, response: ServletResponse) {
    interceptorList.forEach {
      it.postHandler(request, response)
    }
  }
}

interface HandlerInterceptor {
  fun preHandler(request: ServletRequest, response: ServletResponse): Boolean

  fun postHandler(request: ServletRequest, response: ServletResponse)
}

data class ModelAndView(
  val modelMap: Map<String, Any> = mapOf(),
  val view: String = "default_view_name",
) {
  fun modelToJson(): String = modelMap.toString()
}

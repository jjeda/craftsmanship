package zio.craftsmanship.http

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import zio.craftsmanship.http.domain.ClientHost
import zio.craftsmanship.http.domain.ServerHost
import zio.craftsmanship.http.usecase.*

@Controller
@RequestMapping("/http")
class HttpController(
  private val connectHttpOverSecureUseCase: ConnectHttpOverSecure,
  private val exchangeMessageUseCase: ExchangeMessage,
) {

  @PostMapping("/secure-message")
  fun exchangeOverSecure(
    client: ClientHost,
    server: ServerHost,
    planMessage: String,
  ) {
    val connectCommand = ConnectHttpOverSecure.Command(client, server)
    connectHttpOverSecureUseCase.shakeHands(connectCommand)

    val exchangeCommand = ExchangeMessage.Command(client, server, planMessage)
    exchangeMessageUseCase.exchange(exchangeCommand)
  }
}

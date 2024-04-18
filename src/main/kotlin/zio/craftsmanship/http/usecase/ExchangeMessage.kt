package zio.craftsmanship.http.usecase

import org.springframework.stereotype.Service
import zio.craftsmanship.http.domain.ClientHost
import zio.craftsmanship.http.domain.ServerHost

@Service
class ExchangeMessage {
  fun exchange(command: Command) = with(command) {
    val encryptedMessage = client.encryptMessage(planMessage)

    server.decryptMessage(client, encryptedMessage)
  }

  data class Command(
    val client: ClientHost,
    val server: ServerHost,
    val planMessage: String
  )
}

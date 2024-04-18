package zio.craftsmanship.http.usecase

import org.springframework.stereotype.Service
import zio.craftsmanship.http.domain.*

@Service
class ConnectHttpOverSecure {
  fun shakeHands(command: Command) = with(command) {
    connectTcp(client, server)

    val clientHelloMessage = client.clientHello()
    val serverHelloMessage = server.serverHello(clientHelloMessage)
    val certificate = server.certificate()
      .also { client.checkCertificate(it) }

    client.clientKeyExchange(
      certificate.publicKey,
      clientHelloMessage.randomValue,
      serverHelloMessage.randomValue,
    ).apply {
      server.addClientKey(client, this)
    }

    client.changeCipherSpec()
    server.changeCipherSpec()
  }

  private fun connectTcp(client: ClientHost, server: ServerHost) {
    // 3-way handshake
  }

  data class Command(
    val client: ClientHost,
    val server: ServerHost,
  )
}

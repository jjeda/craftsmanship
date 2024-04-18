package zio.craftsmanship.http.domain

import kotlin.random.Random

class ClientHost {
  private val tlsVersion = "TLS 1.2"
  private val cipherSuites = CipherSuites(setOf(CipherSuites.Item(setOf(""))))
  private var symmetricKey: String? = null
  private var useCipherSpec: Boolean = false

  fun clientHello(): HelloMessage {
    return HelloMessage(tlsVersion, cipherSuites, Random.nextInt().toString())
  }

  fun checkCertificate(certificate: Certificate) = certificate

  fun clientKeyExchange(
    publicKey: String,
    clientRandomValue: String,
    serverRandomValue: String
  ): String {
    symmetricKey = "symmetric-key:$clientRandomValue:$serverRandomValue"

    return encryptSymmetricKey(publicKey, symmetricKey!!)
  }

  fun changeCipherSpec() {
    useCipherSpec = true
    finished()
  }

  fun encryptMessage(plainMessage: String): String {
    return symmetricKey + plainMessage
  }

  private fun encryptSymmetricKey(publicKey: String, symmetricKey: String): String {
    return "RSA:$publicKey$symmetricKey"
  }

  private fun finished() {}
}

class ServerHost {
  private val tlsVersion = "TLS 1.2"
  private val cipherSuites = CipherSuites(setOf(CipherSuites.Item(setOf(""))))
  private val privateKey = "private-key"
  private val clientKeyMap = mutableMapOf<ClientHost, String>()
  private var useCipherSpec: Boolean = false

  fun serverHello(helloMessage: HelloMessage): HelloMessage {
    val clientSessionIDLength = helloMessage.sessionIDLength
    val sessionId = if (clientSessionIDLength == 0) {
      generateSessionId()
    } else {
      validateSessionId(clientSessionIDLength)
    }

    return HelloMessage(tlsVersion, cipherSuites, Random.nextInt().toString(), sessionId)
  }

  fun certificate(): Certificate {
    val publicKey = serverKeyExchange()

    return Certificate("server name", publicKey, "CA")
      .apply { serverHelloDone() }
  }

  fun addClientKey(client: ClientHost, encryptedClientKey: String) {
    clientKeyMap[client] = encryptedClientKey + privateKey
  }

  fun changeCipherSpec() {
    useCipherSpec = true
    finished()
  }

  fun decryptMessage(client: ClientHost, encryptedMessage: String): String {
    val symmetricKey = clientKeyMap[client]

    return encryptedMessage + symmetricKey
  }

  private fun generateSessionId(): Int = (1..32).random()

  private fun validateSessionId(sessionIDLength: Int) = sessionIDLength

  private fun serverKeyExchange() = "public-key"

  private fun serverHelloDone() {}

  private fun finished() {}
}
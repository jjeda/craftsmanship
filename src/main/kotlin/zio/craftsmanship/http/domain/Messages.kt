package zio.craftsmanship.http.domain

data class HelloMessage(
  val tlsVersion: String,
  val cipherSuites: CipherSuites,
  val randomValue: String,
  val sessionIDLength: Int = 0,
)

data class Certificate(
  val serverName: String,
  val publicKey: String,
  val certificateAuthority: String,
)

data class CipherSuites(
  val items: Set<Item>
) {
  data class Item(
    val algorithmSet: Set<String>
  ) {
    override fun toString(): String {
      return "TLS_" + algorithmSet.joinToString { "_" }
    }
  }
}
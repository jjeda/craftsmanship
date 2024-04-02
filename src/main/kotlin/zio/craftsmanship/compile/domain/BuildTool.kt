package zio.craftsmanship.compile.domain

interface BuildTool {
  fun packaging(): KotlinPackage

  fun test()

  fun deploy()
}

object Gradle : BuildTool {
  private val repository = MavenCentralRepository
  private const val buildScript: String = "kotlin DSL or Groovy"

  override fun packaging(): KotlinPackage {
    return repository.getKotlinDependency()
  }

  override fun test() {
    return
  }

  override fun deploy() {
    return
  }
}

object MavenCentralRepository {
  fun getKotlinDependency() = KotlinPackage
}

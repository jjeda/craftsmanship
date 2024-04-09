package zio.craftsmanship.compile.usecase

import org.springframework.stereotype.Service
import zio.craftsmanship.compile.domain.*
import zio.craftsmanship.jvm.domain.JavaDevelopmentKit

@Service
class ExecuteJavaArchive {
  private val jvm = JavaDevelopmentKit.jre.jvm
  private val buildTool: BuildTool = Gradle

  fun execute(command: Command) = with(buildTool.packaging()) {
    jvm.executeJarWithKotlinRuntime(command.javaArchiveFile, kotlinRuntime, kotlinStandardLibrary)
  }

  data class Command(
    val javaArchiveFile: JavaArchiveFile
  )
}
package zio.craftsmanship.compile.usecase

import org.springframework.stereotype.Service
import zio.craftsmanship.compile.domain.JavaArchiveFile
import zio.craftsmanship.compile.domain.JavaVirtualMachine

@Service
class ExecuteJavaArchive {

  fun execute(command: Command) {
    JavaVirtualMachine.execute(command.javaArchiveFile)
  }
  data class Command(
    val javaArchiveFile: JavaArchiveFile
  )
}
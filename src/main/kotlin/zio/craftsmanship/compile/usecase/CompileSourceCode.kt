package zio.craftsmanship.compile.usecase

import org.springframework.stereotype.Service
import zio.craftsmanship.compile.domain.*

@Service
class CompileSourceCode {
  private val buildTool: BuildTool = Gradle

  fun compile(command: Command): JavaArchiveFile {
    val kotlinCompiler = buildTool.packaging().kotlinCompiler
    val byteCodeList = command.sourceCodeList.map { kotlinCompiler.compileIntoByteCode(it) }

    return JavaArchiveFile.of(byteCodeList)
  }

  data class Command(
    val sourceCodeList: List<SourceCode>
  )
}

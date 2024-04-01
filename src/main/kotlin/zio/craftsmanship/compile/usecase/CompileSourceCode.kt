package zio.craftsmanship.compile.usecase

import org.springframework.stereotype.Service
import zio.craftsmanship.compile.domain.JavaArchiveFile
import zio.craftsmanship.compile.domain.KotlinCompiler
import zio.craftsmanship.compile.domain.SourceCode

@Service
class CompileSourceCode {
  fun compile(command: Command): JavaArchiveFile {
    val byteCodeList = command.sourceCodeList.map { KotlinCompiler.compileIntoByteCode(it) }

    return JavaArchiveFile.of(byteCodeList)
  }

  data class Command(
    val sourceCodeList: List<SourceCode>
  )
}



package zio.craftsmanship.compile

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import zio.craftsmanship.compile.domain.SourceCode
import zio.craftsmanship.compile.usecase.CompileSourceCode
import zio.craftsmanship.compile.usecase.ExecuteJavaArchive

@Controller
@RequestMapping("/compile")
class CompileController(
  private val compileSourceCodeUseCase: CompileSourceCode,
  private val executeJavaArchiveUseCase: ExecuteJavaArchive
) {
  @PostMapping
  fun compileAndExecute(sourceCodeList: List<SourceCode>) {
    val compileSourceCodeCommand = CompileSourceCode.Command(sourceCodeList)
    val javaArchiveFile = compileSourceCodeUseCase.compile(compileSourceCodeCommand)

    val executeJavaArchiveCommand = ExecuteJavaArchive.Command(javaArchiveFile)
    executeJavaArchiveUseCase.execute(executeJavaArchiveCommand)
  }
}
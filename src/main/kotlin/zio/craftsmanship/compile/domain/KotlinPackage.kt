package zio.craftsmanship.compile.domain

object KotlinPackage {
  val kotlinCompiler = KotlinCompiler()
  val kotlinRuntime = KotlinRuntime()
  val kotlinStandardLibrary = KotlinStandardLibrary()
}

class KotlinCompiler {
  fun compileIntoByteCode(sourceCode: SourceCode): ByteCode {
    analysisSyntax(sourceCode)
    checkType(sourceCode)
    val intermediateRepresentation = generateIntermediateRepresentation(sourceCode)

    return generateByteCodeBy(intermediateRepresentation)
  }

  private fun analysisSyntax(sourceCode: SourceCode): SourceCode {
    return sourceCode
  }

  private fun checkType(sourceCode: SourceCode): SourceCode {
    return sourceCode
  }

  private fun generateIntermediateRepresentation(sourceCode: SourceCode): IntermediateRepresentationCode {
    return IntermediateRepresentationCode.of(sourceCode)
  }

  private fun generateByteCodeBy(intermediateRepresentationCode: IntermediateRepresentationCode): ByteCode {
    return ByteCode(intermediateRepresentationCode.codeByAbstractSyntaxTree)
  }
}

class KotlinRuntime

class KotlinStandardLibrary

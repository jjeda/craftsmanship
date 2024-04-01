package zio.craftsmanship.compile.domain

data class SourceCode(
  val code: String
) {
  companion object {
    private const val PREFIX = ".kt"
  }
}

/**
 * fun main() {
 *   val x = 10
 *   val y = 20
 *   println(x + y)
 * }
 *
 * IntermediateRepresentation(IR) - Abstract Syntax Tree(AST)
 *
 * FunctionDeclaration
 *   - Name: "main"
 *   - Parameters: None
 *   - Body:
 *     - VariableDeclaration
 *       - Name: "x"
 *       - Type: Int
 *       - Value: Literal(10)
 *     - VariableDeclaration
 *       - Name: "y"
 *       - Type: Int
 *       - Value: Literal(20)
 *     - FunctionCall
 *       - Name: "println"
 *       - Arguments:
 *         - BinaryOperation
 *           - Operator: "+"
 *           - Operand1: Identifier("x")
 *           - Operand2: Identifier("y")
 */
data class IntermediateRepresentationCode(
  val codeByAbstractSyntaxTree: String
) {
  companion object {
    fun of(sourceCode: SourceCode): IntermediateRepresentationCode {
      return IntermediateRepresentationCode(sourceCode.code)
    }
  }
}

data class ByteCode(
  val code: String
) {
  companion object {
    private const val PREFIX = ".class"
  }
}

data class JavaArchiveFile(
  val byteCodeList: List<ByteCode>
) {
  companion object {
    private const val PREFIX = ".jar"

    fun of(byteCodeList: List<ByteCode>): JavaArchiveFile {
      return JavaArchiveFile(byteCodeList)
    }
  }
}

data class MachineCode(val code: String)

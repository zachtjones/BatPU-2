package tree

sealed interface Literal
data class CharacterLiteral(val character: Char): Literal
data class NumberLiteral(val number: Int): Literal

data class FunctionDeclaration(val name: String, val body: List<Statement>)

data class Program(
    val functions: List<FunctionDeclaration>
)
package tree

import com.github.h0tk3y.betterParse.grammar.parseToEnd
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class MinecraftParserTest {

    @Test
    fun `parse hello world`() {
        MinecraftParser.parseToEnd(helloWorldProgram) shouldBe Program(
            functions = listOf(
                FunctionDeclaration("main", body = listOf(
                    CommentStatement,
                    FunctionCallStatement("_clearCharacterBuffer", args = emptyList()),
                    FunctionCallStatement("_writeCharacterBuffer", args = emptyList()),
                    CommentStatement,
                    FunctionCallStatement("_appendCharacter", args = listOf(CharacterLiteral('H'))),
                    FunctionCallStatement("_appendCharacter", args = listOf(CharacterLiteral('I'))),
                    FunctionCallStatement("_appendCharacter", args = listOf(CharacterLiteral(' '))),
                    FunctionCallStatement("_appendCharacter", args = listOf(CharacterLiteral('W'))),
                    FunctionCallStatement("_appendCharacter", args = listOf(CharacterLiteral('O'))),
                    FunctionCallStatement("_appendCharacter", args = listOf(CharacterLiteral('R'))),
                    FunctionCallStatement("_appendCharacter", args = listOf(CharacterLiteral('L'))),
                    FunctionCallStatement("_appendCharacter", args = listOf(CharacterLiteral('D'))),
                    FunctionCallStatement("_appendCharacter", args = listOf(CharacterLiteral('!'))),
                    FunctionCallStatement("_writeCharacterBuffer", args = emptyList()),
                ))
            )
        )
    }


    private val helloWorldProgram = """
        fun main() {
            // clear between runs
            _clearCharacterBuffer()
            _writeCharacterBuffer()

            // show the display
            _appendCharacter('H')
            _appendCharacter('I')
            _appendCharacter(' ')
            _appendCharacter('W')
            _appendCharacter('O')
            _appendCharacter('R')
            _appendCharacter('L')
            _appendCharacter('D')
            _appendCharacter('!')
            _writeCharacterBuffer()
        }
    """.trimIndent()
}
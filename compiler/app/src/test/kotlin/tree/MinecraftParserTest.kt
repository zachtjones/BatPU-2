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
                    FunctionCallStatement("_clearCharsBuffer", args = emptyList()),
                    FunctionCallStatement("_bufferChars", args = emptyList()),
                    CommentStatement,
                    FunctionCallStatement("_writeChar", args = listOf(CharacterLiteral('H'))),
                    FunctionCallStatement("_writeChar", args = listOf(CharacterLiteral('I'))),
                    FunctionCallStatement("_writeChar", args = listOf(CharacterLiteral(' '))),
                    FunctionCallStatement("_writeChar", args = listOf(CharacterLiteral('W'))),
                    FunctionCallStatement("_writeChar", args = listOf(CharacterLiteral('O'))),
                    FunctionCallStatement("_writeChar", args = listOf(CharacterLiteral('R'))),
                    FunctionCallStatement("_writeChar", args = listOf(CharacterLiteral('L'))),
                    FunctionCallStatement("_writeChar", args = listOf(CharacterLiteral('D'))),
                    FunctionCallStatement("_writeChar", args = listOf(CharacterLiteral('!'))),
                    FunctionCallStatement("_bufferChars", args = emptyList()),
                ))
            )
        )
    }


    private val helloWorldProgram = """
        fun main() {
            // clear between runs
            _clearCharsBuffer()
            _bufferChars()
        
            // show the display
            _writeChar('H')
            _writeChar('I')
            _writeChar(' ')
            _writeChar('W')
            _writeChar('O')
            _writeChar('R')
            _writeChar('L')
            _writeChar('D')
            _writeChar('!')
            _bufferChars()
        }
    """.trimIndent()
}
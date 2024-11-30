package compiler

import assembly.*
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import tree.*

class MinecraftCompilerTest {

    @Test
    fun `simple hello world program should compile`() {
        val program = Program(
            functions = listOf(
                FunctionDeclaration("main", body = listOf(
                    CommentStatement,
                    FunctionCallStatement("_clearCharsBuffer", args = emptyList()),
                    FunctionCallStatement("_bufferChars", args = emptyList()),
                    CommentStatement,
                    FunctionCallStatement("_writeChar", args = listOf(CharacterLiteral('H'))),
                    FunctionCallStatement("_writeChar", args = listOf(CharacterLiteral('I'))),
                    FunctionCallStatement("_bufferChars", args = emptyList()),
                ))
            )
        )

        MinecraftCompiler.compile(program) shouldBe listOf(
            CallInstruction(name="main"),
            HaltInstruction,
            LabelInstruction(name="main"),
            LoadImmediateInstruction(register = 1, value = 249),
            StoreInstruction(valueR = 0, baseR = 1),
            LoadImmediateInstruction(register = 1, value = 248),
            StoreInstruction(valueR = 0, baseR = 1),
            LoadImmediateInstruction(register = 1, value = 'H'),
            LoadImmediateInstruction(register = 2, value = 247),
            StoreInstruction(valueR = 1, baseR = 2),
            LoadImmediateInstruction(register = 1, value = 'I'),
            LoadImmediateInstruction(register = 2, value = 247),
            StoreInstruction(valueR = 1, baseR = 2),
            LoadImmediateInstruction(register = 1, value = 248),
            StoreInstruction(valueR = 0, baseR = 1),
            ReturnInstruction
        )
    }
}
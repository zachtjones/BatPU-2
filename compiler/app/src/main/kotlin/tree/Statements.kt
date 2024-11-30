package tree

import assembly.Instruction
import assembly.LoadImmediateInstruction
import assembly.StoreInstruction

interface Statement {
    fun addAssembly(instructions: ArrayList<Instruction>)
}

data class FunctionCallStatement(
    val functionName: String,
    val args: List<Literal>
): Statement {
    override fun addAssembly(instructions: ArrayList<Instruction>) {
        if (args.size > 3) {
            throw IllegalArgumentException("Too many args (${args.size}) to $functionName")
        }
        // move in the args
        args.forEachIndexed { index, literal ->
            instructions += when (literal) {
                is CharacterLiteral -> LoadImmediateInstruction(
                    register = index + 1,
                    value = literal.character
                )
                is NumberLiteral -> LoadImmediateInstruction(
                    register = index + 1,
                    value = literal.number
                )
            }
        }
        // 'call' the function
        when (functionName) {
            "_appendCharacter" -> {
                require(args.size == 1) { "_appendCharacter takes 1 argument" }
                instructions += LoadImmediateInstruction(2, 247)
                instructions += StoreInstruction(valueR = 1, baseR = 2)
            }
            "_writeCharacterBuffer" -> {
                require(args.isEmpty()) { "_writeCharacterBuffer takes no arguments" }
                instructions += LoadImmediateInstruction(1, 248)
                instructions += StoreInstruction(valueR = 0, baseR = 1)
            }
            "_clearCharacterBuffer" -> {
                require(args.isEmpty()) { "_writeCharacterBuffer takes no arguments" }
                instructions += LoadImmediateInstruction(1, 249)
                instructions += StoreInstruction(valueR = 0, baseR = 1)
            }
            // TODO support remaining special names.
            else -> {
                // TODO support custom function names
                throw IllegalArgumentException("Function $functionName not supported")
            }
        }
    }
}

object CommentStatement: Statement {
    override fun toString() = "Comment"

    override fun addAssembly(instructions: ArrayList<Instruction>) {}
}
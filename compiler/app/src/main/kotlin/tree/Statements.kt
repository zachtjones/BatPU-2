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
        val builtInFunction = BuiltInFunction.getOrNull(functionName)
        if (builtInFunction != null) {
            require(args.size == builtInFunction.expectedArgs) { "$functionName takes ${builtInFunction.expectedArgs} argument(s)" }
            builtInFunction.addAssembly(instructions)
        } else {
            TODO("Support non-built in functions, like $functionName")
        }
    }
}

object CommentStatement: Statement {
    override fun toString() = "Comment"

    override fun addAssembly(instructions: ArrayList<Instruction>) {}
}
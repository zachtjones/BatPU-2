package tree

import assembly.AddInstruction
import assembly.Instruction
import assembly.LoadImmediateInstruction

data class FunctionCallStatement(
    val functionName: String,
    val args: List<Expression>
): Statement {
    override fun addAssembly(instructions: ArrayList<Instruction>, compileContext: CompileContext) {
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

                is ReadVariable -> AddInstruction()
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
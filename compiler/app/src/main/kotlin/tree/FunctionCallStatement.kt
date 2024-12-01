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
        args.forEachIndexed { index, expression ->
            // r1 for arg 1, ...
            val targetRegister = index + 1
            instructions += when (expression) {
                is CharacterLiteral -> LoadImmediateInstruction(
                    register = targetRegister,
                    value = expression.character
                )
                is NumberLiteral -> LoadImmediateInstruction(
                    register = targetRegister,
                    value = expression.number
                )

                is ReadVariable -> {
                    val sourceRegister = compileContext.getSymbolEntry(expression.variableName)
                    AddInstruction(
                        regA = sourceRegister,
                        regB = 0,
                        regResult = targetRegister
                    )
                }
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
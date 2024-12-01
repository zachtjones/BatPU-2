package tree

import assembly.Instruction

sealed interface BooleanExpression {
    fun addAssembly(instructions: ArrayList<Instruction>, compileContext: CompileContext)
}

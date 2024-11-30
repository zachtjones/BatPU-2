package tree

import assembly.Instruction

data class VariableDeclarationAndAssignment(
    private val variableName: String,
    private val value: Literal
): Statement {
    override fun addAssembly(instructions: ArrayList<Instruction>, compileContext: CompileContext) {
        TODO("Not yet implemented")
    }
}
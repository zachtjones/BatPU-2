package tree

import assembly.Instruction

data class DecrementStatement(private val variableName: String): Statement {
    override fun addAssembly(instructions: ArrayList<Instruction>, compileContext: CompileContext) {
        TODO("Not yet implemented")
    }
}
package tree

import assembly.Instruction

class IncrementStatement(private val variableName: String): Statement {
    override fun addAssembly(instructions: ArrayList<Instruction>, compileContext: CompileContext) {
        TODO("Not yet implemented")
    }
}
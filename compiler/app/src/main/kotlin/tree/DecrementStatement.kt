package tree

import assembly.AddImmediateInstruction
import assembly.Instruction

data class DecrementStatement(private val variableName: String): Statement {

    override fun addAssembly(instructions: ArrayList<Instruction>, compileContext: CompileContext) {
        val register = compileContext.getSymbolEntry(variableName)
        instructions += AddImmediateInstruction(register, immediate = -1)
    }
}
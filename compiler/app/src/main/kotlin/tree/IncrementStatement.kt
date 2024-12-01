package tree

import assembly.AddImmediateInstruction
import assembly.Instruction

class IncrementStatement(private val variableName: String): Statement {
    override fun addAssembly(instructions: ArrayList<Instruction>, compileContext: CompileContext) {
        val register = compileContext.getSymbolEntry(variableName)
        instructions += AddImmediateInstruction(register, immediate = 1)
    }
}
package tree

import assembly.Instruction
import assembly.LoadImmediateInstruction

data class VariableDeclarationAndAssignment(
    private val variableName: String,
    private val value: Literal
): Statement {
    override fun addAssembly(instructions: ArrayList<Instruction>, compileContext: CompileContext) {
        // right now we don't know if the variable is used across function calls, so going with persisted ones
        try {
            val allocatedRegister = compileContext.freePersistedRegisters.removeFirst()
            compileContext.addSymbolEntry(variableName, allocatedRegister)

            // TODO - save the register to memory so it can be restored when this function returns

            instructions += when (value) {
                is NumberLiteral -> LoadImmediateInstruction(register = allocatedRegister, value = value.number)
                is CharacterLiteral -> LoadImmediateInstruction(register = allocatedRegister, value = value.character)
            }
        } catch (ex: NoSuchElementException) {
            throw IllegalArgumentException("Not enough registers to compile -- TODO implement smarter ways to have more variables")
        }
    }
}
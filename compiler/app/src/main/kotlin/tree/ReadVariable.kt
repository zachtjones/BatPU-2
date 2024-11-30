package tree

import assembly.Instruction

/**
 * Reading a variable's value
 */
class ReadVariable(private val variableName: String): Expression {
    override fun addAssembly(instructions: ArrayList<Instruction>) {
        TODO("Not yet implemented")
    }
}
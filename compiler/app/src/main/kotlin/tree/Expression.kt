package tree

import assembly.Instruction

sealed interface Expression {
    fun addAssembly(instructions: ArrayList<Instruction>)
}

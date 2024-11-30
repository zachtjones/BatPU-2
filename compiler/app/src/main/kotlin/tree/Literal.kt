package tree

import assembly.Instruction

sealed interface Literal: Expression {
    override fun addAssembly(instructions: ArrayList<Instruction>) {
        TODO("To be implemented if needed")
    }
}
data class CharacterLiteral(val character: Char): Literal
data class NumberLiteral(val number: Int): Literal
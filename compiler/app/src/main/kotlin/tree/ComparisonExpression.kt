package tree

import assembly.Instruction

data class ComparisonExpression(
    private val identifier: String,
    private val operation: String,
    private val literal: Literal): BooleanExpression {
    // TODO assuming >= for now

    override fun addAssembly(instructions: ArrayList<Instruction>) {
        TODO("Not yet implemented")
    }
}

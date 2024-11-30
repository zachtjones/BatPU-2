package tree

import assembly.Instruction

data class WhileStatement(val expression: BooleanExpression, val body: List<Statement>): Statement {
    override fun addAssembly(instructions: ArrayList<Instruction>, compileContext: CompileContext) {
        TODO("Not yet implemented")
    }
}
package tree

import assembly.Instruction
import assembly.JumpInstruction
import assembly.LabelInstruction

data class WhileStatement(val expression: BooleanExpression, val body: List<Statement>): Statement {
    override fun addAssembly(instructions: ArrayList<Instruction>, compileContext: CompileContext) {
        // pre-compute label names
        val startLabel = compileContext.getNextLabel("whileStart")
        val endLabel = compileContext.getNextLabel("whileEnd")

        // start label
        instructions += LabelInstruction(startLabel)

        // evaluate condition & branch (if false to end)
        compileContext.booleanExpressionLogic = BranchOnFalse(endLabel)
        expression.addAssembly(instructions, compileContext)
        compileContext.booleanExpressionLogic = null

        // body
        for (statement in body) {
            statement.addAssembly(instructions, compileContext)
        }

        // jump to start
        instructions += JumpInstruction(startLabel)

        // end label
        instructions += LabelInstruction(endLabel)
    }
}
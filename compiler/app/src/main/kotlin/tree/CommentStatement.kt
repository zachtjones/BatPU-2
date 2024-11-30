package tree

import assembly.Instruction

object CommentStatement: Statement {
    override fun toString() = "Comment"

    override fun addAssembly(instructions: ArrayList<Instruction>, compileContext: CompileContext) {}
}
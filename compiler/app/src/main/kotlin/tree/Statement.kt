package tree

import assembly.Instruction

interface Statement {
    fun addAssembly(instructions: ArrayList<Instruction>, compileContext: CompileContext)
}

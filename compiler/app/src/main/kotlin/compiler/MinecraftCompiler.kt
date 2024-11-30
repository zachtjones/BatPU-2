package compiler

import assembly.*
import tree.Program

object MinecraftCompiler {

    fun compile(program: Program): List<Instruction> {
        val instructions = arrayListOf<Instruction>()
        // returns from main will end the program execution
        instructions += CallInstruction("main")
        instructions += HaltInstruction

        // TODO other top level concepts

        for (function in program.functions) {
            instructions += LabelInstruction(function.name)

            function.body.forEach {
                it.addAssembly(instructions)
            }

            instructions += ReturnInstruction
        }

        return instructions
    }
}
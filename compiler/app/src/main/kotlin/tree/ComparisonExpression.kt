package tree

import assembly.*

data class ComparisonExpression(
    private val identifier: String,
    private val operation: String,
    private val literal: Literal
): BooleanExpression {
    // TODO assuming >= for now

    override fun addAssembly(instructions: ArrayList<Instruction>, compileContext: CompileContext) {
        val logic = requireNotNull(compileContext.booleanExpressionLogic) {
            "Internal error: statement enclosing comparison did not set the booleanExpressionLogic"
        }

        // load immediate to a temp register
        val tempRegister = compileContext.freeScratchRegisters.removeFirst()
        instructions += when (literal) {
            is NumberLiteral -> {
                LoadImmediateInstruction(register = tempRegister, value = literal.number)
            }
            is CharacterLiteral -> {
                LoadImmediateInstruction(register = tempRegister, value = literal.character)
            }
        }

        // get the register to be compared against
        val targetRegister: Int = compileContext.getSymbolEntry(identifier)

        instructions += SubtractInstruction(regA = targetRegister, regB = tempRegister, regResult = 0)

        when (operation) {
            ">=" -> {
                when (logic) {
                    is BranchOnFalse -> {
                        // when less than, branch to end
                        instructions += BranchInstruction(logic.targetLabel, BranchCondition.LESS_THAN)
                    }
                }
            }
            "<" -> {
                when (logic) {
                    is BranchOnFalse -> {
                        // when greater than or equal, branch to end
                        instructions += BranchInstruction(logic.targetLabel, BranchCondition.GREATER_THAN_OR_EQUAL)
                    }
                }
            }
            "==" -> {
                when (logic) {
                    is BranchOnFalse -> {
                        // if not equal, branch to end
                        instructions += BranchInstruction(logic.targetLabel, BranchCondition.NOT_EQUAL)
                    }
                }
            }
            "!=" -> {
                when (logic) {
                    is BranchOnFalse -> {
                        // if equal branch to end
                        instructions += BranchInstruction(logic.targetLabel, BranchCondition.EQUAL)
                    }
                }
            }
            else -> {
                throw IllegalArgumentException("Unknown comparison operator '$operation'")
            }
        }

        // tempRegister is free to be used by something else
        compileContext.freeScratchRegisters += tempRegister
    }
}

package tree

import java.util.*

class CompileContext(private val functionName: String) {

    /**
     * Set this value when processing a logic statement (like if / while) before compiling in the expression.
     * Guides the expression's compile behavior.
     */
    var booleanExpressionLogic: BooleanExpressionBranchResult? = null

    val freeScratchRegisters = TreeSet(listOf(4, 5, 6, 7, 8, 9))
    val freePersistedRegisters = TreeSet(listOf(10, 11, 12, 13, 14))

    val zeroRegister = 0
    val returnRegister = 15

    // String to register
    private val symbolTable = mutableMapOf<String, Int>()

    private var nextLabel: Int = 1

    fun getSymbolEntry(name: String): Int = requireNotNull(symbolTable[name]) {
        "Error: symbol '$name' not defined, please check your program"
    }
    fun addSymbolEntry(name: String, value: Int) {
        symbolTable[name] = value
    }

    fun getNextLabel(suffix: String = ""): String {
        nextLabel += 1
        return "${functionName}_$suffix${nextLabel}"
    }
}

sealed interface BooleanExpressionBranchResult

/** When the boolean expression is compiled, branch when the expression is false and then discard the result */
class BranchOnFalse(val targetLabel: String): BooleanExpressionBranchResult
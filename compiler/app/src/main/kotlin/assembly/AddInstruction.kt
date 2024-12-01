package assembly

/**
 * result (C in ISA) = A + B
 *
 * A, B, result are register numbers.
 */
class AddInstruction(
    private val regA: Int,
    private val regB: Int,
    private val regResult: Int,
): Instruction() {
    override fun string(): String = "ADD r$regA r$regB r$regResult"
}
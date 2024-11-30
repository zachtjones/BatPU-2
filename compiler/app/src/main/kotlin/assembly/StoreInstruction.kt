package assembly

data class StoreInstruction(
    /** register that holds the value to write */
    private val valueR: Int,
    /** register that holds the base address to write to */
    private val baseR: Int,
    /** immediate offset for the address */
    private val offset: Int = 0
) : Instruction() {

    override fun string() = "STR r$baseR r$valueR $offset"
}
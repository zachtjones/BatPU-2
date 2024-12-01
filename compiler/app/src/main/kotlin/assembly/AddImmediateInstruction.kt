package assembly

data class AddImmediateInstruction(
    private val register: Int,
    private val immediate: Int
): Instruction() {

    override fun string(): String = "ADI r$register $immediate"
}
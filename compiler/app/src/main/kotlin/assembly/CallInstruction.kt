package assembly

data class CallInstruction(val name: String): Instruction() {
    override fun string() = "CAL .$name"
}
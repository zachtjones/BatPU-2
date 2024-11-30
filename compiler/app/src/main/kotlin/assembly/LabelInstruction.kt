package assembly

data class LabelInstruction(val name: String): Instruction() {
    override fun string() = ".$name"
}
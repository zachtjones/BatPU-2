package assembly

data class JumpInstruction(private val label: String) : Instruction() {
    override fun string(): String = "JMP .$label"
}
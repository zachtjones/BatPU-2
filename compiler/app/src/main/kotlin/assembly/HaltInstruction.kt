package assembly

object HaltInstruction: Instruction() {
    override fun string(): String = "HLT"
}
package assembly

data class LoadImmediateInstruction private constructor(
    private val register: Int,
    private val value: String
): Instruction() {

    constructor(register: Int, value: Char) : this(register, "'$value'")
    constructor(register: Int, value: Int) : this(register, value.toString())

    override fun string() = "LDI r$register $value"
}
package assembly

data class BranchInstruction(
    private val label: String,
    private val condition: BranchCondition
): Instruction() {
    override fun string(): String = "BRH ${condition.assemblyName} .$label"
}

enum class BranchCondition(val assemblyName: String) {
    EQUAL("EQ"),
    NOT_EQUAL("NE"),
    GREATER_THAN_OR_EQUAL("GE"),
    LESS_THAN("LE"),
    ;
}
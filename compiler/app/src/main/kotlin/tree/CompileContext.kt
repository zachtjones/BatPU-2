package tree

class CompileContext {

    val freeScratchRegisters = mutableSetOf(
        4, 5, 6, 7, 8, 9
    )
    val freePersistedRegisters = mutableSetOf(
        10, 11, 12, 13, 14
    )

    val zeroRegister = 0
    val returnRegister = 15


    // String to register
    val symbolTable = mutableMapOf<String, Int>()
}
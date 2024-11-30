package assembly

/**
 * An instruction in assembly
 */
sealed class Instruction {

    abstract fun string(): String

    // can't make toString abstract, so forcing classes to implement via string()
    override fun toString(): String = string()
}
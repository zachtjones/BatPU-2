package tree

import assembly.Instruction
import assembly.LoadImmediateInstruction
import assembly.StoreInstruction

enum class BuiltInFunction(
    /**
     * The name to use in the high-level language
     */
    val languageName: String,
    /**
     * The number of expected arguments to the function.
     */
    val expectedArgs: Int,
    /**
     * If this function returns a value
     */
    val returns: Boolean
) {
    // draw, clear, and get pixel all take in (x, y) for convenience.
    //

    /*
    * 'pixel_x', 'pixel_y', 'draw_pixel', 'clear_pixel',
    * 'load_pixel', 'buffer_screen', 'clear_screen_buffer',
    * */

    DRAW_PIXEL(languageName = "_drawPixel", expectedArgs = 2, returns = false),
    CLEAR_PIXEL(languageName = "_clearPixel", expectedArgs = 2, returns = false),
    GET_PIXEL(languageName = "_getPixel", expectedArgs = 2, returns = true),

    BUFFER_SCREEN(languageName = "_bufferScreen", expectedArgs = 0, returns = false),
    CLEAR_SCREEN_BUFFER(languageName = "_clearScreenBuffer", expectedArgs = 0, returns = false),

    WRITE_CHAR(languageName = "_writeChar", expectedArgs = 1, returns = false),
    BUFFER_CHARS(languageName = "_bufferChars", expectedArgs = 0, returns = false),
    CLEAR_CHARS_BUFFER(languageName = "_clearCharsBuffer", expectedArgs = 0, returns = false),

    SHOW_NUMBER(languageName = "_showNumber", expectedArgs = 0, returns = false),
    CLEAR_NUMBER(languageName = "_clearNumber", expectedArgs = 0, returns = false),
    SIGNED_MODE(languageName = "_signedMode", expectedArgs = 0, returns = false),
    UNSIGNED_MODE(languageName = "_unsignedMode", expectedArgs = 0, returns = false),

    RANDOM(languageName = "_random", expectedArgs = 0, returns = true),
    CONTROLLER(languageName = "_controllerInput", expectedArgs = 0, returns = true),
    ;

    fun addAssembly(instructions: ArrayList<Instruction>) {
        when (this) {
            DRAW_PIXEL -> TODO()
            CLEAR_PIXEL -> TODO()
            GET_PIXEL -> TODO()
            BUFFER_SCREEN -> TODO()
            CLEAR_SCREEN_BUFFER -> TODO()
            WRITE_CHAR -> {
                instructions += LoadImmediateInstruction(2, 247)
                instructions += StoreInstruction(valueR = 1, baseR = 2)
            }
            BUFFER_CHARS -> {
                instructions += LoadImmediateInstruction(1, 248)
                instructions += StoreInstruction(valueR = 0, baseR = 1)
            }
            CLEAR_CHARS_BUFFER -> {
                instructions += LoadImmediateInstruction(1, 249)
                instructions += StoreInstruction(valueR = 0, baseR = 1)
            }
            SHOW_NUMBER -> TODO()
            CLEAR_NUMBER -> TODO()
            SIGNED_MODE -> TODO()
            UNSIGNED_MODE -> TODO()
            RANDOM -> TODO()
            CONTROLLER -> TODO()
        }
    }

    /*
    *
    * 'write_char', 'buffer_chars', 'clear_chars_buffer',
    * 'show_number', 'clear_number', 'signed_mode', 'unsigned_mode',
    * 'rng', 'controller_input'
    * */

    companion object {
        private val map = entries.associateBy { it.languageName }

        fun getOrNull(name: String): BuiltInFunction? = map[name]
    }
}
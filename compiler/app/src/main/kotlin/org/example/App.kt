package org.example

import com.github.h0tk3y.betterParse.grammar.parseToEnd
import java.nio.file.Files
import kotlin.io.path.Path

fun main(vararg args: String) {
    val filename = if (args.size >= 1) {
        println("Loading file ${args[0]}")
        args[0]
    } else {
        "programs/hellowWorld.kt"
    }
    val lines = Files.readString(Path(filename))

    println(lines)

    val tokens = MinecraftParser.tokenizer.tokenize(lines)
    val tokensByLine = tokens.groupBy { it.row }.toSortedMap()
    for ((line,contents) in tokensByLine) {
        println("Line $line: ${contents.map { it.type.name }}}")
    }

    val result = MinecraftParser.parseToEnd(lines)
    println(result)
}
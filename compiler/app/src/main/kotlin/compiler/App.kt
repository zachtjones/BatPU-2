package compiler

import com.github.h0tk3y.betterParse.grammar.parseToEnd
import tree.MinecraftParser
import java.io.PrintWriter
import java.nio.file.Files
import kotlin.io.path.Path

const val printTokens: Boolean = true

fun main(vararg args: String) {
    val filename = if (args.size >= 1) {
        println("Loading file ${args[0]}")
        args[0]
    } else {
        "programs/hiWorld.kt"
    }
    val lines = Files.readString(Path(filename))

    if (printTokens) {
        val tokens = MinecraftParser.tokenizer.tokenize(lines)
        val tokensByLine = tokens.groupBy { it.row }.toSortedMap()
        for ((line, contents) in tokensByLine) {
            println("Line $line: ${contents.map { it.type.name }}}")
        }
    }

    val parsed = MinecraftParser.parseToEnd(lines)
    println("SyntaxTree: $parsed")

    val assembly = MinecraftCompiler.compile(parsed)
    val output = PrintWriter(filename.replaceAfterLast('.', "as"))
    for (line in assembly) {
        output.println(line.string())
    }
    output.flush()
    output.close()
}